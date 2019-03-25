package com.upgrad.FoodOrderingApp.service.dao;

import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.CustomerEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDao {

    @PersistenceContext
    EntityManager entityManager;


    public CategoryEntity checkCategoryExists(String categoryId) {
        try {
            return entityManager.createNamedQuery("getCategory", CategoryEntity.class).setParameter("uuid", categoryId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }

    public List<CategoryEntity> getAllCategories() {
        Query query = entityManager.createQuery("from CategoryEntity");
        List resultList = query.getResultList();
        List<CategoryEntity> entityList = new ArrayList<>();
        for (Object obj : resultList) {
            entityList.add((CategoryEntity) obj);
        }
        return entityList;
    }

    public CategoryEntity getCategoryById(String categoryId) {
        try {
            return entityManager.createNamedQuery("getCategory", CategoryEntity.class).setParameter("uuid", categoryId)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
