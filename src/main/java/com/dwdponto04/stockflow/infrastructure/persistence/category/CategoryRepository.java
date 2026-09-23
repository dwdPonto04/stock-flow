package com.dwdponto04.stockflow.infrastructure.persistence.category;

import com.dwdponto04.stockflow.business.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,Long> {
}
