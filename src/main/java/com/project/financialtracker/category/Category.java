package com.project.financialtracker.category;

import com.project.financialtracker.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Integer categoryId;

    @Column(name = "name")
    private String name;

    @Column(name = "max_limit")
    private Double maxLimit;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    Category(CategoryRequest categoryRequest, User user){
        this.name = categoryRequest.getName();
        this.maxLimit = categoryRequest.getMaxLimit();
        this.user = user;
    }
}
