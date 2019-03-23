package com.upgrad.FoodOrderingApp.service.entity;


import javax.persistence.*;

/**
 * Entity class for Rstaurant and category
 */
@Entity
@Table(name = "restaurant_category", schema = "public")
@NamedQueries({
        @NamedQuery(name = "restaurantCategoryMapping", query = "select c from RestaurantCategoryEntity as c")
})
public class RestaurantCategoryEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    private RestaurantEntity restaurantId;


    @ManyToOne
    @JoinColumn(name = "category_id")
    private CategoryEntity categoryEntityId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public RestaurantEntity getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(RestaurantEntity restaurantId) {
        this.restaurantId = restaurantId;
    }

    public CategoryEntity getCategoryEntityId() {
        return categoryEntityId;
    }

    public void setCategoryEntityId(CategoryEntity categoryEntityId) {
        this.categoryEntityId = categoryEntityId;
    }
}
