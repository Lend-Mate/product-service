//package com.lendmate.productservice.config;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.cache.annotation.EnableCaching;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.cache.RedisCacheConfiguration;
//import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
//import org.springframework.data.redis.serializer.RedisSerializationContext;
//
//@Configuration
//@EnableCaching
//@Slf4j
//@RequiredArgsConstructor
//public class RedisConfig {
//
//    private final ObjectMapper objectMapper;
//
//    @Bean
//    RedisCacheConfiguration cacheConfiguration() {
//        ObjectMapper redisObjectMapper = objectMapper.copy();
//        redisObjectMapper.registerModule(new JavaTimeModule());
//        redisObjectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
//
//        GenericJackson2JsonRedisSerializer genericJackson2JsonRedisSerializer = new GenericJackson2JsonRedisSerializer(redisObjectMapper);
//
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair
//                                .fromSerializer(genericJackson2JsonRedisSerializer)
//                );
//    }
//}