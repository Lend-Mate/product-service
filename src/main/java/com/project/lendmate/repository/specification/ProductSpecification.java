package com.project.lendmate.repository.specification;

import com.project.lendmate.dto.requestDto.ProductFilterRequest;
import com.project.lendmate.model.Enum.Reason;
import com.project.lendmate.model.Product;
import com.project.lendmate.model.ProductAvailability;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ProductSpecification {

    private static Specification<Product> isNotDeleted() {
        return (root, query, cb) -> cb.equal(root.get("deleted"), false);
    }

    private static Specification<Product> isAvailable() {
        return (root, query, cb) -> {
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<ProductAvailability> pa = subquery.from(ProductAvailability.class);

            subquery.select(pa.get("productId"))
                    .where(
                            cb.equal(pa.get("productId"), root.get("id")),
                            pa.get("reason").in(Reason.BLOCKED, Reason.MAINTENANCE)
                    );

            return cb.not(cb.exists(subquery));
        };
    }

    public static Specification<Product> withFilters(ProductFilterRequest filter) {
        Specification<Product> spec = Specification.unrestricted();

        spec = spec.and(isNotDeleted());
        spec = spec.and(isAvailable());

        if (filter.getQuery() != null) {
            spec = spec.and(matchProductNameOrDescription(filter.getQuery()));
        }

        if (filter.getCategoryId() != null) {
            spec = spec.and(hasCategoryId(filter.getCategoryId()));
        }
        if (filter.getBrands() != null && !filter.getBrands().isEmpty()) {
            spec = spec.and(hasBrands(filter.getBrands()));
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

    private static Specification<Product> matchProductNameOrDescription(String query) {
        return (root, criteriaQuery, cb) -> {
            // Eğer arama metni boşsa, filtreleme yapma (koşulsuz geç)
            if (query == null || query.trim().isEmpty()) {
                return null;
            }

            // Küçük/büyük harf duyarlılığını ortadan kaldırmak için metni küçültüyoruz
            String lowerCaseQuery = "%" + query.toLowerCase() + "%";

            // 1. Koşul: Name alanı sorguyu içeriyor mu?
            var namePredicate = cb.like(cb.lower(root.get("productName")), lowerCaseQuery);

            // 2. Koşul: Description alanı sorguyu içeriyor mu?
            var descriptionPredicate = cb.like(cb.lower(root.get("description")), lowerCaseQuery);

            // İki koşulu OR (veya) ile birleştiriyoruz
            return cb.or(namePredicate, descriptionPredicate);
        };
    }

    private static Specification<Product> hasCategoryId(Long id) {
        return (root, query, cb) ->
                id == null ? null : cb.equal(root.get("categoryId"), id);
    }

    private static Specification<Product> hasBrands(List<String> brands) {
        return (root, query, cb) -> root.get("brand").in(brands);
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
