package com.dwdponto04.stockflow.business.product.entity;

import com.dwdponto04.stockflow.business.category.entity.Category;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "code",unique = true,nullable = false,length = 30)
    private UUID code;
    @Column(name = "name",nullable = false,length = 70)
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price",nullable = false,precision = 10, scale = 2)
    private BigDecimal price;
    @Column(name = "quantity",nullable = false)
    private Integer quantity;
    @ManyToOne(optional = false)
    @JoinColumn(name = "category_id",referencedColumnName = "id",nullable = false)
    private Category category;
}
