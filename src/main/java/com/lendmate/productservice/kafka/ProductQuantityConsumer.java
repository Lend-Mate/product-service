package com.lendmate.productservice.kafka;

import com.lendmate.productservice.event.StockDecreaseEvent;
import com.lendmate.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductQuantityConsumer {
    private final ProductService productService;

    @RetryableTopic(attempts = "1", kafkaTemplate = "kafkaTemplate", dltStrategy = DltStrategy.FAIL_ON_ERROR)
    @KafkaListener(topics = "quantity-decrease-topic", groupId = "product-service")
    public void handleStockEvent(StockDecreaseEvent event) {
        productService.decreaseStockForItems(event.getItems(), event.getOrderId());
    }

    @DltHandler
    public void handleDltPayment(StockDecreaseEvent event) {
        log.error("Stok yetersiz veya işlem başarısız olduğu için event DLT'ye düştü - orderId={}", event.getOrderId());
    }
}
