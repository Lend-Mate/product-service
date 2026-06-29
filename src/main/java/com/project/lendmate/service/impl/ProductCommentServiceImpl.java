package com.project.lendmate.service.impl;

import com.project.lendmate.dto.requestDto.ProductCommentRequest;
import com.project.lendmate.dto.responseDto.ProductCommentResponse;
import com.project.lendmate.expection.CommentNotFoundException;
import com.project.lendmate.mapper.ProductCommentMapper;
import com.project.lendmate.model.ProductComment;
import com.project.lendmate.repository.ProductCommentRepository;
import com.project.lendmate.service.ProductCommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class ProductCommentServiceImpl implements ProductCommentService {

    private final ProductCommentRepository repository;
    private final ProductCommentMapper mapper;

    @Override
    public List<ProductCommentResponse> getComments() {
        return repository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductCommentResponse createProductComment(ProductCommentRequest request) {
        ProductComment model = mapper.toEntity(request);
        ProductComment saved = repository.save(model);
        return mapper.toDto(saved);
    }

    @Override
    @Transactional
    public ProductCommentResponse updateProductComment(Long commentId, ProductCommentRequest request) {
        ProductComment comment = repository.findById(commentId).orElseThrow(() -> new CommentNotFoundException("Comment not found: " + commentId));
        mapper.updateEntity(comment,request);
        ProductComment savedComment = repository.save(comment);
        return mapper.toDto(savedComment);
    }

    @Override
    public void deleteProductComments(List<Long> commentIds) {
        if (commentIds == null || commentIds.isEmpty()) {
            return;
        }
       repository.deleteAllByIdInBatch(commentIds);
    }
}