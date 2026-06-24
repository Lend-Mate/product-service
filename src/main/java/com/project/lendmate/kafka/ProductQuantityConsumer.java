package com.project.lendmate.kafka;

import com.project.lendmate.dto.requestDto.ProductRequest;
import com.project.lendmate.dto.responseDto.ProductResponse;
import com.project.lendmate.event.StockDecreaseEvent;
import com.project.lendmate.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQuantityConsumer {
    private final ProductService productService;

    @KafkaListener(topics = "quantity-decrease-topic", groupId = "product-service")
    public void handleStockEvent(StockDecreaseEvent event) {
        event.getItems().forEach(item -> {
            ProductResponse product = productService.getProductById(item.getProductId());
            int updatedStock = product.getStockQuantity() - item.getQuantity();
            if (updatedStock < 0 ){
                throw new IllegalStateException("Yetersiz stock");
            }
            ProductRequest request = new ProductRequest();
            request.setStockQuantity(updatedStock);
            productService.updateProduct(item.getProductId(), request);
        });
        log.info("Tüm stoklar güncellendi - orderId={}", event.getOrderId());
    }

}
