package com.lendmate.productservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lendmate.productservice.dto.requestDto.ProductRequest;
import com.lendmate.productservice.model.Enum.Currency;
import com.lendmate.productservice.model.Enum.RentalPeriod;
import com.lendmate.productservice.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
class ProductControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private ProductRequest validRequest;

    @BeforeEach
    void setUp() {
        productRepository.deleteAll();

        validRequest = new ProductRequest();
        validRequest.setOwnerId(1L);
        validRequest.setCategoryId(2L);
        validRequest.setProductName("Test Kamera");
        validRequest.setDescription("Test açıklama");
        validRequest.setCurrency(Currency.TL);
        validRequest.setPrice(new BigDecimal("350.00"));
        validRequest.setBrand("Sony");
        validRequest.setStockQuantity(1);
        validRequest.setDepositAmount(new BigDecimal("1000.00"));
        validRequest.setAvailablePeriods(Set.of(RentalPeriod.ONE_MONTH));
        validRequest.setAttributes(List.of());
    }

    // ─── POST /products ───────────────────────────────────────

    @Test
    void createProduct_withValidRequest_returns201() throws Exception {
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.productName").value("Test Kamera"))
                .andExpect(jsonPath("$.brand").value("Sony"))
                .andExpect(jsonPath("$.id").exists());
    }

    @Test
    void createProduct_withNullOwnerId_returns400() throws Exception {
        validRequest.setOwnerId(null);

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void createProduct_withBlankProductName_returns400() throws Exception {
        validRequest.setProductName("");

        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createProduct_alreadyExists_returns409() throws Exception {
        // İlk kayıt
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated());

        // Aynı ownerId + productName ile tekrar
        mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isConflict());
    }

    // ─── GET /products/{id} ───────────────────────────────────

    @Test
    void getProductById_exists_returns200() throws Exception {
        // Önce oluştur
        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.productName").value("Test Kamera"));
    }

    @Test
    void getProductById_notFound_returns404() throws Exception {
        mockMvc.perform(get("/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }

    // ─── PUT /products/{id} ───────────────────────────────────

    @Test
    void updateProduct_success_returns200() throws Exception {
        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        validRequest.setProductName("Güncellenmiş Kamera");

        mockMvc.perform(put("/products/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("Güncellenmiş Kamera"));
    }

    @Test
    void updateProduct_notFound_returns404() throws Exception {
        mockMvc.perform(put("/products/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isNotFound());
    }

    // ─── DELETE /products/{id} ────────────────────────────────

    @Test
    void deleteProduct_success_returns204() throws Exception {
        String response = mockMvc.perform(post("/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isCreated())
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(response).get("id").asLong();

        mockMvc.perform(delete("/products/{id}", id))
                .andExpect(status().isNoContent());

        // Soft delete — artık 404 dönmeli
        mockMvc.perform(get("/products/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteProduct_notFound_returns404() throws Exception {
        mockMvc.perform(delete("/products/{id}", 999L))
                .andExpect(status().isNotFound());
    }
}
