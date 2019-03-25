package com.upgrad.FoodOrderingApp.api.controller;


import com.upgrad.FoodOrderingApp.api.model.*;
import com.upgrad.FoodOrderingApp.service.businness.CategoryService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.exception.CategoryNotFoundException;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Method used to get top orders for a restaurant
     *
     * @return
     * @throws RestaurantNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/category", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getAllCategories() {
        List<CategoryEntity> categoryList = categoryService.getAllCategories();
        List<CategoryListResponse> listResponse = convertToCategoryListResponse(categoryList);
        CategoriesListResponse response = new CategoriesListResponse().categories(listResponse);
        return new ResponseEntity(response, HttpStatus.OK);
    }

    private List<CategoryListResponse> convertToCategoryListResponse(List<CategoryEntity> categoryList) {
        List<CategoryListResponse> finalList = new ArrayList<>();
        for (CategoryEntity entity : categoryList) {
            CategoryListResponse listObject = new CategoryListResponse();
            listObject.setId(UUID.fromString(entity.getUuid()));
            listObject.setCategoryName(entity.getCategoryName());
            finalList.add(listObject);
        }
        return finalList;
    }


    @RequestMapping(method = RequestMethod.GET, path = "/category/{category_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getCategoryById(@PathVariable("category_id") final String categoryId) {
        CategoryEntity categoryEntity = null;
        try {

            if (categoryId.trim().isEmpty()) {
                throw new CategoryNotFoundException("CNF-001", "Category id field should not be empty");
            }
        } catch (CategoryNotFoundException e) {
            ErrorResponse response = new ErrorResponse().message(e.getErrorMessage()).code(e.getCode());
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }

        try {
            categoryEntity = categoryService.getCategoryById(categoryId);
        } catch (CategoryNotFoundException e) {
            ErrorResponse response = new ErrorResponse().message(e.getErrorMessage()).code(e.getCode());
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }

        List<ItemEntity> itemList = categoryEntity.getItemList();
        List<ItemList> responseItemList = new ArrayList<>();

        for (ItemEntity entity : itemList) {
            ItemList listObj = new ItemList();
            listObj.setItemName(entity.getItemName());
            listObj.setPrice(entity.getPrice());
            listObj.setItemType(entity.getType().equalsIgnoreCase("0") ? ItemList.ItemTypeEnum.VEG : ItemList.ItemTypeEnum.NON_VEG);
            listObj.setId(UUID.fromString(entity.getUuid()));
            responseItemList.add(listObj);
        }


        CategoryDetailsResponse response = new CategoryDetailsResponse()
                .categoryName(categoryEntity.getCategoryName())
                .id(UUID.fromString(categoryEntity.getUuid()))
                .itemList(responseItemList);
        return new ResponseEntity(response, HttpStatus.OK);
    }

}
