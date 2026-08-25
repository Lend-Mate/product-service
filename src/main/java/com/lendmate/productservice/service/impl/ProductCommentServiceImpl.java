package com.lendmate.productservice.service.impl;

import com.lendmate.productservice.dto.requestDto.ProductCommentRequest;
import com.lendmate.productservice.dto.responseDto.ProductCommentResponse;
import com.lendmate.productservice.expection.CommentNotFoundException;
import com.lendmate.productservice.expection.ProductNotFoundException;
import com.lendmate.productservice.mapper.ProductCommentMapper;
import com.lendmate.productservice.model.ProductComment;
import com.lendmate.productservice.repository.ProductCommentRepository;
import com.lendmate.productservice.service.ProductCommentService;
import com.lendmate.productservice.service.ProductService;
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
    private final ProductService productService;

    @Override
    public List<ProductCommentResponse> getComments(Long id) {
        return repository.findByProductId(id).stream().map(mapper::toDto).collect(Collectors.toList());
    }

    @Override
    public ProductCommentResponse createProductComment(ProductCommentRequest request) {
        boolean existProductId = productService.existsProduct(request.getProductId());
        if (!existProductId) throw new ProductNotFoundException("Product not found, productId: " + request.getProductId());
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