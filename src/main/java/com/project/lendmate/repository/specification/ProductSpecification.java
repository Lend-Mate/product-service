package com.project.lendmate.repository.specification;

import com.project.lendmate.dto.requestDto.ProductFilterRequest;
import com.project.lendmate.model.Product;
import com.project.lendmate.model.ProductAvailability;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ProductSpecification {

    private static Specification<Product> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Product> isAvailable() {
        return (root, query, cb) -> {
            LocalDateTime now = LocalDateTime.now();

            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProductAvailability> pa = subquery.from(ProductAvailability.class);

            subquery.select(pa.get("productId"))
                    .where(
                            cb.equal(pa.get("productId"), root.get("id")),
                            cb.lessThanOrEqualTo(pa.get("startDate"), now),
                            cb.greaterThanOrEqualTo(pa.get("endDate"), now)
                    );

            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<Product> withFilters(ProductFilterRequest filter) {
        Specification<Product> spec = Specification.unrestricted();

        spec = spec.and(isNotDeleted());
        spec = spec.and(isAvailable());

        if (filter.getCategoryId() != null) {
            spec = spec.and(hasCategoryId(filter.getCategoryId()));
        }
        if (filter.getBrand() != null) {
            spec = spec.and(hasBrand(filter.getBrand()));
        }
        if (filter.getMinPrice() != null) {
            spec = spec.and(hasPriceGreaterThan(filter.getMinPrice()));
        }
        if (filter.getMaxPrice() != null) {
            spec = spec.and(hasPriceLessThan(filter.getMaxPrice()));
        }
        if (filter.getMinRentalDays() != null) {
            spec = spec.and(hasRentalDaysGreaterThan(filter.getMinRentalDays()));
        }
        if (filter.getMaxRentalDays() != null) {
            spec = spec.and(hasRentalDaysLessThan(filter.getMaxRentalDays()));
        }

        return spec;
    }

    private static Specification<Product> hasCategoryId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("categoryId"), id);
    }

    private static Specification<Product> hasBrand(String brand) {
        return (root, query, cb) ->
                brand == null ? null : cb.like(cb.lower(root.get("brand")), "%" + brand.toLowerCase() + "%");
    }

    private static Specification<Product> hasPriceGreaterThan(BigDecimal minPrice) {
        return (root, query, cb) ->
                minPrice == null ? null : cb.greaterThanOrEqualTo(root.get("price"), minPrice);
    }

    private static Specification<Product> hasPriceLessThan(BigDecimal maxPrice) {
        return (root, query, cb) ->
                maxPrice == null ? null : cb.lessThanOrEqualTo(root.get("price"), maxPrice);
    }

    private static Specification<Product> hasRentalDaysGreaterThan(Integer rentalDays) {
        return (root, query, cb) ->
                rentalDays == null ? null : cb.greaterThanOrEqualTo(root.get("minRentalDays"), rentalDays);
    }

    private static Specification<Product> hasRentalDaysLessThan(Integer rentalDays) {
        return (root, query, cb) ->
                rentalDays == null ? null : cb.lessThanOrEqualTo(root.get("maxRentalDays"), rentalDays);
    }
}
