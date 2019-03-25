package com.upgrad.FoodOrderingApp.api.controller;

import com.upgrad.FoodOrderingApp.api.model.ErrorResponse;
import com.upgrad.FoodOrderingApp.api.model.ItemList;
import com.upgrad.FoodOrderingApp.api.model.ItemListResponse;
import com.upgrad.FoodOrderingApp.api.model.RestaurantListResponse;
import com.upgrad.FoodOrderingApp.service.businness.ItemService;
import com.upgrad.FoodOrderingApp.service.businness.RestaurantService;
import com.upgrad.FoodOrderingApp.service.entity.CategoryEntity;
import com.upgrad.FoodOrderingApp.service.entity.ItemEntity;
import com.upgrad.FoodOrderingApp.service.entity.Orders;
import com.upgrad.FoodOrderingApp.service.entity.RestaurantEntity;
import com.upgrad.FoodOrderingApp.service.exception.RestaurantNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
@RequestMapping("/")
public class ItemController {

    ///
    @Autowired
    private ItemService itemService;

    @Autowired
    private RestaurantService restaurantService;

    /**
     * Method used to get top orders for a restaurant
     *
     * @param uuid
     * @return
     * @throws RestaurantNotFoundException
     */
    @RequestMapping(method = RequestMethod.GET, path = "/item/restaurant/{restaurant_id}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity getTopFiveOrders(@PathVariable("restaurant_id") final String uuid) {
        RestaurantEntity restaurantByRestaurantId = null;

        try {
            restaurantByRestaurantId = restaurantService.getRestaurantByRestaurantId(uuid);
            Set<CategoryEntity> categories = restaurantByRestaurantId.getCategories();
            List<ItemEntity> itemList = new ArrayList<>();
            //get all the available items from each category
            for (CategoryEntity entity : categories) {
                itemList.addAll(entity.getItemList());
            }

            int count = 0;
            ItemListResponse response = new ItemListResponse();
            for (ItemEntity entity : itemList) {
                ItemList list = new ItemList();
                list.setId(UUID.fromString(entity.getUuid()));
                list.setItemType(entity.getType()
                        .equalsIgnoreCase("0") ? ItemList.ItemTypeEnum.VEG : ItemList.ItemTypeEnum.NON_VEG);
                list.setPrice(entity.getPrice());
                list.setItemName(entity.getItemName());
                response.add(list);
                count++;
                if (count == 5) {
                    break;
                }
            }
            return new ResponseEntity(response, HttpStatus.OK);

        } catch (RestaurantNotFoundException e) {
            ErrorResponse response = new ErrorResponse();
            response.setCode(e.getCode());
            response.setMessage(e.getErrorMessage());
            return new ResponseEntity(response, HttpStatus.FORBIDDEN);
        }
    }


}
