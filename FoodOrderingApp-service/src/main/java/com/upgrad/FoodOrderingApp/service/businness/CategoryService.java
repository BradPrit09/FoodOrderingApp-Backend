package com.upgrad.FoodOrderingApp.service.businness;

import com.upgrad.FoodOrderingApp.service.dao.CategoryDao;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;


    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryEntity checkCategoryExists(String categoryId) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryDao.checkCategoryExists(categoryId);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        } else {
            return categoryEntity;
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public List<CategoryEntity> getAllCategories() {
        List<CategoryEntity> categoryList = categoryDao.getAllCategories();
        return categoryList;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryEntity getCategoryById(String categoryId) throws CategoryNotFoundException {
        CategoryEntity categoryEntity = categoryDao.getCategoryById(categoryId);
        if (categoryEntity == null) {
            throw new CategoryNotFoundException("CNF-002", "No category by this id");
        }
        return categoryEntity;
    }
}
