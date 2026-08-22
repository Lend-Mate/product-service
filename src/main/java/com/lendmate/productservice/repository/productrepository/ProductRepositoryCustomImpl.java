package com.lendmate.productservice.repository.productrepository;

import com.lendmate.productservice.model.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<String> findDistinctBrands(Specification<Product> spec) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();

        CriteriaQuery<String> query = cb.createQuery(String.class);

        Root<Product> root = query.from(Product.class);

        query.select(root.get("brand"))
                .distinct(true);

        if (spec != null) {
            Predicate predicate = spec.toPredicate(root, query, cb);
            query.where(predicate);
        }

        query.orderBy(cb.asc(root.get("brand")));

        return entityManager.createQuery(query).getResultList();
    }
}