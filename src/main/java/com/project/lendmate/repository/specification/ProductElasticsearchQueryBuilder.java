package com.project.lendmate.repository.specification;

import co.elastic.clients.elasticsearch._types.query_dsl.BoolQuery;
import com.project.lendmate.document.ProductDocument;
import com.project.lendmate.dto.requestDto.ProductSearchFilterRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProductElasticsearchQueryBuilder {

    private final ElasticsearchOperations elasticsearchOperations;

    public Page<ProductDocument> searchWithFilters(ProductSearchFilterRequest filter, Pageable pageable) {

        BoolQuery.Builder boolQuery = new BoolQuery.Builder();

        // text search — productName veya description içinde
        if (filter.getQuery() != null && !filter.getQuery().isBlank()) {
            boolQuery.must(m -> m
                    .multiMatch(mm -> mm
                            .query(filter.getQuery())
                            .fields("product_name", "description")
                    )
            );
        }

        // categoryId filtresi
        if (filter.getCategoryId() != null) {
            boolQuery.filter(f -> f
                    .term(t -> t
                            .field("category_id")
                            .value(filter.getCategoryId())
                    )
            );
        }

        // brand filtresi
        if (filter.getBrand() != null) {
            boolQuery.filter(f -> f
                    .term(t -> t
                            .field("brand")
                            .value(filter.getBrand())
                    )
            );
        }

        // fiyat aralığı
        if (filter.getMinPrice() != null || filter.getMaxPrice() != null) {
            boolQuery.filter(f -> f
                    .range(r -> r
                            .number(n -> {
                                n.field("price");
                                if (filter.getMinPrice() != null) n.gte(filter.getMinPrice().doubleValue());
                                if (filter.getMaxPrice() != null) n.lte(filter.getMaxPrice().doubleValue());
                                return n;
                            })
                    )
            );
        }

        // minRentalDays aralığı
        if (filter.getMinRentalDays() != null) {
            boolQuery.filter(f -> f
                    .range(r -> r
                            .number(n -> {
                                n.field("min_rental_days");
                                if (filter.getMinRentalDays() != null) n.gte(filter.getMinRentalDays().doubleValue());
                                return n;
                            })
                    )
            );
        }

        // maxRentalDays aralığı
        if (filter.getMaxRentalDays() != null) {
            boolQuery.filter(f -> f
                    .range(r -> r
                            .number(n -> {
                                n.field("max_rental_days");
                                if (filter.getMaxRentalDays() != null) n.lte(filter.getMaxRentalDays().doubleValue());
                                return n;
                            })
                    )
            );
        }

        NativeQuery nativeQuery = NativeQuery.builder()
                .withQuery(q -> q.bool(boolQuery.build()))
                .withPageable(pageable)
                .build();

        SearchHits<ProductDocument> hits = elasticsearchOperations.search(nativeQuery, ProductDocument.class);

        List<ProductDocument> documents = hits.getSearchHits()
                .stream()
                .map(SearchHit::getContent)
                .toList();

        return new PageImpl<>(documents, pageable, hits.getTotalHits());
    }
}