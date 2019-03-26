# FoodOrderingApp-Backend
# REST API Endpoints

[![N|Solid](https://i0.wp.com/flixtel.in/wp-content/uploads/2018/02/cropped-mpls-new-1.png?resize=150%2C150)](https://www.upgrad.com/)

[![Build Status](https://travis-ci.org/joemccann/dillinger.svg?branch=master)](https://github.com/BradPrit09/Quora-Demo.git)

In this project, we will be developing from scratch REST API endpoints of various functionalities required for the web app FoodOrderingApp. In order to observe the functionalities of the endpoints, we will create the FoodOrderingApp UI using React to interact with and store the data in the PostgreSQL database. In order to observe the functionality of the endpoints, we will use the Swagger user interface and store the data in the PostgreSQL database. Also, the project has to be implemented using Java Persistence API (JPA).


 
# New Features!
The following API endpoints are  implemented in respective classes:
  - signup - "/customer/signup" This endpoint is used to register a new user in the web Application.
  - login- "/customer/login" This endpoint is used for user authentication.Customer authenticates in the application and after successful authentication, JWT token is given to a customer.
  - logout - "/customer/logout" This endpoint is used to sign out from the web Application.
  - Update - “/customer” This endpoint requests for all the attributes in “UpdateCustomerRequest” about the customer.
  - Change Password - “/customer/password” This endpoint changes the user password.
  - Save Address - “/address”  Returns uuid of the address saved and message “ADDRESS SUCCESSFULLY REGISTERED” in the JSON response with the corresponding HTTP status.
  - Get All Saved Addresses - “/address/customer” Returns the list of saved address in descending order of their saved time in the Json response with the corresponding HTTP status.
  - Delete Saved Address - “/address/{address_id}” Returns uuid of the address deleted and message “ADDRESS DELETED SUCCESSFULLY” in the JSON response with the corresponding HTTP status.
  - Get All States - “/states” When any customer tries to access this endpoint, it should retrieve all the states present in the database and display the response in a JSON format with the corresponding HTTP status.
  - Get All Restaurants - "/restaurant" When any customer tries to access this endpoint, it should retrieve all the restaurants in order of their ratings and display the response in a JSON format with the corresponding HTTP status.
  - Get Restaurant/s by Name - “/restaurant/name/{reastaurant_name}” This endpoint must request the following value from the customer as a path variable: Restaurant name - String
  - Get Restaurants by Category Id “/restaurant/category/{category_id}”  Returns Category UUID - String Within each restaurant, the list of categories should be displayed in a categories string, in alphabetical order and the items shouldn’t be displayed.
  - Get Restaurant by Restaurant ID - “/api/restaurant/{restaurant_id}” The restaurant detail should have all the items it contains grouped by their categories in alphabetical order.
  - Update Restaurant Details- “/api/restaurant/{restaurant_id}” If the restaurant id entered by the customer matches any restaurant in the database, it should update that restaurant’s rating in the database along with the number of customers who have rated it. Then return the uuid of the restaurant updated and message “RESTAURANT RATING UPDATED SUCCESSFULLY” in the JSON response with the corresponding HTTP status. 
  - Get Top 5 Items by Popularity - “/item/restaurant/{restaurant_id} If the restaurant id entered by the customer matches any restaurant in the database, it should retrieve the top five items of that restaurant based on the number of times that item was ordered and then display the response in a JSON format with the corresponding HTTP status.
  - Get All Categories - “/category” it should retrieve all the categories present in the database, ordered by their name and display the response in a JSON format with the corresponding HTTP status.
  - Get Category by Id - “/category/{category_id}” If the category id entered by the customer matches any category in the database, it should retrieve that category with all items within that category and then display the response in a JSON format with the corresponding HTTP status. Also, the name searched should not be case sensitive.
  - Get Coupon by Coupon Name - “/order/coupon/{coupon_name}” If the coupon name entered by the customer matches any coupon in the database, retrieve the coupon details and display the response in a JSON format with the corresponding HTTP status.
  - Get Past Orders of User - “/order” retrieve all the past orders from the customer sorted by their order date, with the newest order first, and return them in a JSON format with the corresponding HTTP status.
  - Save Order - “/order” This endpoint rests for all the attributes in “SaveOrderRequest” from the customer.
  - Get Payment Methods - “/payment” Retrieve all the payment methods and return them in the JSON format with the corresponding HTTP status.
  




> The overall goal of this project is to provide a backend for Food Ordering Web Application


### Tech

Quora API uses a number of open source projects to work properly:

* [IntelliJ IDEA](https://www.jetbrains.com/idea/)
* [Java](https://www.java.com/en/download/)
* [Git](https://git-scm.com/downloads)


And of course Quora API itself is open source with a [public repository](https://github.com/BradPrit09/Quora-Demo/) on GitHub.

### Installation

This project  requires [Maven](https://maven.apache.org/)  v3.4+ to run and we need [postgresql.jar](https://jdbc.postgresql.org/download.html) driver which needs to be added to the dependency of the project.



```sh
 1.Import the project in Intellij idea using option "Checkout from Version Control"

 2.Select git as the option

 3.Use the URL https://github.com/BradPrit09/FoodOrderingApp-Backend to clone the repository on your local machine

 4.Once the project gets imported in you local workspace, you need to add the jar file postgresql in your classpath.

 5.right click on the postgresql jar file and select "Add to library"

 6.Then goto Application.yaml and for the Postgresql driver select suggesstion in red and select add to classpath

    This will add the file to classpath.

7.Goto file FoodOrderingApp-Backend, right click and run it as a Java application. 
```




### Development

Want to contribute? Great!
Make a change in your file and instantanously see your updates!

Open your favorite Terminal and run these commands after changes are made from local repository.

First Tab:
```sh
$ git init
```

Second Tab:
```sh
$ git add .
```

 Third:
```sh
$ git commit -m "Message For Commit Done"
```
Fouth:
```sh
$ git push origin patch
```

### Todos

 - Write MORE Tests
 

License
----
Apache 2.0




**Free Software, Hell Yeah!**

