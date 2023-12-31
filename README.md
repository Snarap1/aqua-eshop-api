### About
The Backend for an Online Store is a robust and feature-rich server-side application that powers the functionality of an e-commerce website. It provides a comprehensive set of features, enabling users to register, log in, create orders, view their order history, and includes an admin panel for managing the store's operations.
-------------

### Technologies
- Java
- Spring boot, Spring Data JPA, Spring Security
- PostgreSQL
- JWT Tokens
- Docker
- Swagger
-------------
### Setup

To build and run the project, follow these steps:
* Clone the repository
* Navigate to the project directory
* Add database "sec_db" to postgres 
* Build the project: mvn clean install
* Run the project: mvn spring-boot:run 

-> The application will be available at http://localhost:8080.

-------------
Look at console to get SuperUser authentication token.
http://localhost:8080/swagger-ui/index.html# 

Create post example: 

User registration example: 
```json
{
  "username": "user",
  "email": "user@gmail.com",
  "password": "userpassword",
  "role": "USER"
}
```
User authentication request example 
```json
{
  "email": "user@gmail.com",
  "password": "userpassword"
}
```
order-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|PUT |/api/v1/order/{orderItemId}/quantity||
|PUT |/api/v1/order/delivery||
|PATCH |/api/v1/order/{productId}||
|PATCH |/api/v1/order/status/{orderId}||
|PATCH |/api/v1/order/address/{orderId}||
|GET |/api/v1/order/status||
|GET |/api/v1/order/orderList||
|GET |/api/v1/order/orderHistory||
|GET |/api/v1/order/downloadOrderDetails/{orderId}||
|GET |/api/v1/order/all||

Management
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|GET |/api/v1/management||
|PUT |/api/v1/management||
|POST |/api/v1/management||
|DELETE |/api/v1/management||

product-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|POST |/api/v1/product/||
|GET |/api/v1/product/{productId}||
|DELETE |/api/v1/product/{productId}||
|PATCH |/api/v1/product/{productId}||
|GET |/api/v1/product/all||

order-item-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|POST |/api/v1/item/||
|GET |/api/v1/item/{itemId}||
|GET |/api/v1/item/all||
|DELETE |/api/v1/item/{itemID}||

cart-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|GET |/api/v1/cart||
|POST |/api/v1/cart||
|POST |/api/v1/cart/buy||
|GET |/api/v1/cart/{cartId}||
|DELETE |/api/v1/cart/{cartId}||
|GET |/api/v1/cart/all||

authentication-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|POST /api/v1/auth/register||
|POST /api/v1/auth/refresh-token||
|POST /api/v1/auth/authenticate||

address-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|POST |/api/v1/address/||
|PATCH |/api/v1/address/{orderId}||
|GET |/api/v1/address/{addressId}||
|DELETE |/api/v1/address/{addressId}||
|GET |/api/v1/address/all||

user-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|PATCH |/api/v1/user||

admin-controller
| Method  | URL  | Description |
| :------------ |:---------------:| -----:|
|GET |/api/v1/admin||
-------------
