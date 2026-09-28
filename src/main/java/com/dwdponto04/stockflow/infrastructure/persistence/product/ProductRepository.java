package com.dwdponto04.stockflow.infrastructure.persistence.product;

import com.dwdponto04.stockflow.business.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

    boolean existsByNameIgnoreCase(String name);
}
