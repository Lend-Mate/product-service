package com.lendmate.productservice.kafka;

import com.lendmate.productservice.dto.requestDto.ProductRequest;
import com.lendmate.productservice.dto.responseDto.ProductResponse;
import com.lendmate.productservice.event.StockDecreaseEvent;
import com.lendmate.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQuantityConsumer {
    private final ProductService productService;

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate")
    @KafkaListener(topics = "quantity-decrease-topic", groupId = "product-service")
    public void handleStockEvent(StockDecreaseEvent event) {
        throw new RuntimeException("DLT Testi için bilerek fırlatılan hata!");

//        event.getItems().forEach(item -> {
//            ProductResponse product = productService.getProductById(item.getProductId());
//            int updatedStock = product.getStockQuantity() - item.getQuantity();
//            if (updatedStock < 0){
//                throw new IllegalStateException("Yetersiz stock");
//            }
//            ProductRequest request = new ProductRequest();
//            request.setStockQuantity(updatedStock);
//            productService.updateProduct(item.getProductId(), request);
//        });
//        log.info("Tüm stoklar güncellendi - orderId={}", event.getOrderId());
    }

    @DltHandler
    public void handleDltPayment(StockDecreaseEvent event) {
        log.error("Stok yetersiz veya işlem başarısız olduğu için event DLT'ye düştü - orderId={}", event.getOrderId());
    }
}
