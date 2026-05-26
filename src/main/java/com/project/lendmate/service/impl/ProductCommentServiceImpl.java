package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;
import com.project.lendmate.mapper.ProductCommentMapper;
import com.project.lendmate.model.ProductComment;
import com.project.lendmate.repository.ProductCommentRepository;
import com.project.lendmate.service.ProductCommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class ProductCommentServiceImpl implements ProductCommentService {

    private final ProductCommentRepository repository;
    private final ProductCommentMapper mapper;

    @Override
    public ProductCommentResponse createProductComment(ProductCommentRequest request) {
        ProductComment model = mapper.toEntity(request);
        ProductComment saved = repository.save(model);
        return mapper.toDto(saved);
    }
}