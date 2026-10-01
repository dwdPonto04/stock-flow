package com.dwdponto04.stockflow.infrastructure.persistence.product;

import com.dwdponto04.stockflow.business.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {


    boolean existsByNameIgnoreCase(String name);

    Optional<Product> findByNameIgnoreCase(String name);
    Optional<Product> findByCode(UUID code);
    List<Product> findByCategoryId(Long categoryId);


}
