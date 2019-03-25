package com.upgrad.FoodOrderingApp.service.entity;

import org.hibernate.annotations.Fetch;
import org.springframework.data.repository.cdi.Eager;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

/**
 * Entity class for CategoryEntity table
 */
@Entity
@Table(name = "category", schema = "public")
@NamedQueries({
        @NamedQuery(name = "getCategory", query = "select c from CategoryEntity as c where c.uuid=:uuid")
})
public class CategoryEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    @NotNull
    private String uuid;

    @Column(name = "category_name")
    @Size(max = 255)
    @NotNull
    private String categoryName;

    @ManyToMany(mappedBy = "categories")
    private List<RestaurantEntity> restaurantList;


    @ManyToMany(cascade = {CascadeType.ALL}, fetch = FetchType.EAGER)
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id"))
    private List<ItemEntity> itemList;


    public List<ItemEntity> getItemList() {
        return itemList;
    }

    public void setItemList(List<ItemEntity> itemList) {
        this.itemList = itemList;
    }

    public List<RestaurantEntity> getRestaurantList() {
        return restaurantList;
    }

    public void setRestaurantList(List<RestaurantEntity> restaurantList) {
        this.restaurantList = restaurantList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
