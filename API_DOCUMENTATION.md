# API Documentation - eCommerce Application

RESTful API for e-commerce management with users, products, shopping cart, and orders implemented in microservices architecture.

**Base URLs**: 
- User service: `http://localhost:8082/api/`
- Product service: `http://localhost:8081/api/`
- Order service: `http://localhost:8083/api/`

---

## 📑 Table of Contents

1. [Users](#users)
2. [Products](#products)
3. [Shopping Cart](#shopping-cart)
4. [Orders](#orders)

---

<a name="users"></a>
## 👥 Users

### Create User
```http
POST /api/users
```

**Request Body (JSON)**:
```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@gmail.com",
  "phone": "64846848",
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "zipcode": "10001"
  }
}
```

**Successful Response (200 OK)**:
```json
"User created successfully"
```

---

### Update User
```http
PUT /api/users/{id}
```

**Path Parameters**:
- `id` (Long) - User ID

**Request Body (JSON)**:
```json
{
  "firstName": "John updated",
  "lastName": "Doe updated",
  "email": "john@gmail.com",
  "phone": "64846848",
  "address": {
    "street": "123 Main St 5",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "zipcode": "10001"
  }
}
```

**Successful Response (200 OK)**:
```json
"User updated successfully"
```

**Error Response (404 NOT FOUND)**: User not found

---

### Get All Users
```http
GET /api/users
```

**Successful Response (200 OK)**:
```json
[
  {
    "id": "69a8715a0d7513753c2209f4",
    "firstName": "John updated",
    "lastName": "Doe updated",
    "email": "john@gmail.com",
    "phone": "64846848",
    "role": "CUSTOMER",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "zipcode": "10001"
    }
  }
]
```
---

### Get User by ID
```http
GET /api/users/{id}
```

**Path Parameters**:
- `id` (Long) - User ID

**Successful Response (200 OK)**:
```json
{
  "id": "69a8715a0d7513753c2209f4",
  "firstName": "John",
  "lastName": "Doe",
  "email": "john@gmail.com",
  "phone": "64846848",
  "role": "CUSTOMER",
  "address": {
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "zipcode": "10001"
  }
}
```

**Error Response (404 NOT FOUND)**: User not found

---

### Delete User
```http
DELETE /api/users/{id}
```

**Path Parameters**:
- `id` (Long) - User ID

**Successful Response (200 OK)**:
```json
"User deleted successfully"
```

---

<a name="products"></a>
## 🛍️ Products

### Create Product
```http
POST /api/products
```

**Request Body (JSON)**:
```json
{
  "name": "iPhone 16 Pro",
  "description": "Latest iPhone with amazing features",
  "price": 1099.99,
  "stockQuantity": 50,
  "category": "Electronics",
  "imageUrl": "https://placehold.co/600x400"
}
```

**Successful Response (201 CREATED)**:
```json
{
  "id": 1,
  "name": "iPhone 16 Pro",
  "description": "Latest iPhone with amazing features",
  "price": 1099.99,
  "stockQuantity": 50,
  "category": "Electronics",
  "imageUrl": "https://placehold.co/600x400",
  "active": true
}
```

---

### Update Product
```http
PUT /api/products/{id}
```

**Path Parameters**:
- `id` (Long) - Product ID

**Request Body (JSON)**:
```json
{
  "name": "iPhone 16 Pro Updated",
  "description": "Latest iPhone with amazing features",
  "price": 1099.98,
  "stockQuantity": 50,
  "category": "Electronics",
  "imageUrl": "https://placehold.co/600x400"
}
```

**Successful Response (200 OK)**:
```json
{
  "id": 1,
  "name": "iPhone 16 Pro Updated",
  "description": "Latest iPhone with amazing features",
  "price": 1099.98,
  "stockQuantity": 50,
  "category": "Electronics",
  "imageUrl": "https://placehold.co/600x400",
  "active": true
}
```

**Error Response (404 NOT FOUND)**: Product not found

---

### Get All Products
```http
GET /api/products
```

**Successful Response (200 OK)**:
```json
[
  {
    "id": 1,
    "name": "iPhone 16 Pro",
    "description": "Latest iPhone with amazing features",
    "price": 1099.99,
    "stockQuantity": 50,
    "category": "Electronics",
    "imageUrl": "https://placehold.co/600x400",
    "active": true
  }
]
```

---

### Delete Product
```http
DELETE /api/products/{id}
```

**Path Parameters**:
- `id` (Long) - Product ID

**Example**:
```http
DELETE /api/products/1
```

**Successful Response (204 NO CONTENT)**: Product deleted

**Error Response (404 NOT FOUND)**: Product not found

---

### Search Products
```http
GET /api/products/search?keyword={keyword}
```

**Query Parameters**:
- `keyword` (String) - Keyword to search in product name or description

**Example**:
```http
GET /api/products/search?keyword=iPhone
```

**Successful Response (200 OK)**:
```json
[
  {
    "id": 1,
    "name": "iPhone 16 Pro",
    "description": "Latest iPhone with amazing features",
    "price": 1099.99,
    "stockQuantity": 50,
    "category": "Electronics",
    "imageUrl": "https://placehold.co/600x400",
    "active": true
  }
]
```

---

<a name="shopping-cart"></a>
## 🛒 Shopping Cart

**Note**: All cart endpoints require the `X-User-ID` header with the user's ID.

### Add Product to Cart
```http
POST /api/cart
```

**Headers**:
- `X-User-ID: 1`

**Request Body (JSON)**:
```json
{
  "productId": "1",
  "quantity": 1
}
```

**Successful Response (201 CREATED)**: Product added to cart

**Error Response (400 BAD REQUEST)**:
```json
"Product Out of Stock or User Not Found or Product Not Found"
```

---

### Get Cart Items
```http
GET /api/cart
```

**Headers**:
- `X-User-ID: 1`

**Successful Response (200 OK)**:
```json
[
  {
    "id": 1,
    "userId": "1",
    "productId": "1",
    "quantity": 1,
    "price": 1000.00
  }
]
```

**No Content Response (204 NO CONTENT)**: Empty cart

---

### Remove Product from Cart
```http
DELETE /api/cart/items/{productId}
```

**Headers**:
- `X-User-ID: 1`

**Path Parameters**:
- `productId` (Long) - Product ID to remove

**Example**:
```http
DELETE /api/cart/items/1
```

**Successful Response (204 NO CONTENT)**: Product removed from cart

**Error Response (404 NOT FOUND)**: Product not found in cart

---

<a name="orders"></a>
## 📦 Orders

**Note**: Order endpoints require the `X-User-ID` header with the user's ID.

### Create Order
```http
POST /api/orders
```

**Headers**:
- `X-User-ID: 1`

**Description**: Creates an order with all items from the user's cart and empties the cart.

**Successful Response (201 CREATED)**:
```json
{
  "id": 1,
  "totalAmount": 1000.00,
  "status": "CONFIRMED",
  "items": [
    {
      "id": 1,
      "productId": "1",
      "quantity": 1,
      "price": 1000.00
    }
  ],
  "createdAt": "2026-03-04T19:08:50.44672"
}
```

**Error Response (400 BAD REQUEST)**: Empty cart or error creating order

---

## 🔐 HTTP Status Codes

- `200 OK` - Successful request
- `201 CREATED` - Resource created successfully
- `204 NO CONTENT` - Successful request with no content in response
- `400 BAD REQUEST` - Client request error
- `404 NOT FOUND` - Resource not found
- `500 INTERNAL SERVER ERROR` - Internal server error

---

## 📝 Data Models

### UserRequest
```json
{
  "firstName": "String",
  "lastName": "String",
  "email": "String",
  "phone": "String",
  "address": "AddressDTO"
}
```

### UserResponse
```json
{
  "id": "String",
  "firstName": "String",
  "lastName": "String",
  "email": "String",
  "phone": "String",
  "role": "UserRole (CUSTOMER, ADMIN)",
  "address": "AddressDTO"
}
```

### AddressDTO
```json
{
  "street": "String",
  "city": "String",
  "state": "String",
  "country": "String",
  "zipcode": "String"
}
```

### ProductRequest
```json
{
  "name": "String",
  "description": "String",
  "price": "BigDecimal",
  "stockQuantity": "Integer",
  "category": "String",
  "imageUrl": "String"
}
```

### ProductResponse
```json
{
  "id": "Long",
  "name": "String",
  "description": "String",
  "price": "BigDecimal",
  "stockQuantity": "Integer",
  "category": "String",
  "imageUrl": "String",
  "active": "boolean"
}
```

### CartItemRequest
```json
{
  "userId": "String (set by header)",
  "productId": "String",
  "quantity": "Integer",
  "price": "BigDecimal"
}
```

### CartItemResponse
```json
{
  "id": "Long",
  "userId": "String",
  "productId": "String",
  "quantity": "Integer",
  "price": "BigDecimal"
}
```

### OrderResponse
```json
{
  "id": "Long",
  "totalAmount": "BigDecimal",
  "status": "OrderStatus (PENDING, CONFIRMED, SHIPPED, DELIVERED, CANCELLED)",
  "items": "List<OrderItemDTO>",
  "createdAt": "LocalDateTime"
}
```

### OrderItemDTO
```json
{
  "id": "Long",
  "productId": "String",
  "quantity": "Integer",
  "price": "BigDecimal"
}
```

---

## 🧪 Usage Examples with cURL

### Create a User
```bash
curl -X POST http://localhost:8082/api/users \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John new",
    "lastName": "Doe new",
    "email": "john@gmail.com",
    "phone": "64846848",
    "address": {
      "street": "123 Main St",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "zipcode": "10001"
    }
  }'
```

### Update a User
```bash
curl -X PUT http://localhost:8082/api/users/69a8715a0d7513753c2209f4 \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John updated",
    "lastName": "Doe updated",
    "email": "john@gmail.com",
    "phone": "64846848",
    "address": {
      "street": "123 Main St 5",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "zipcode": "10001"
    }
  }'
```

### Delete a User
```bash
curl -X DELETE http://localhost:8082/api/users/69a8715a0d7513753c2209f4
```

### Create a Product
```bash
curl -X POST http://localhost:8081/api/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 16 Pro",
    "description": "Latest iPhone with amazing features",
    "price": 1099.99,
    "stockQuantity": 50,
    "category": "Electronics",
    "imageUrl": "https://placehold.co/600x400"
  }'
```

### Update a Product
```bash
curl -X PUT http://localhost:8081/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "iPhone 16 Pro Updated",
    "description": "Latest iPhone with amazing features",
    "price": 1099.98,
    "stockQuantity": 50,
    "category": "Electronics",
    "imageUrl": "https://placehold.co/600x400"
  }'
```

### Delete a Product
```bash
curl -X DELETE http://localhost:8081/api/products/1
```

### Add Product to Cart
```bash
curl -X POST http://localhost:8083/api/cart \
  -H "Content-Type: application/json" \
  -H "X-User-ID: 1" \
  -d '{
    "productId": 1,
    "quantity": 1
  }'
```

### Get Cart Items
```bash
curl -X GET http://localhost:8083/api/cart \
  -H "X-User-ID: 1"
```

### Remove Product from Cart
```bash
curl -X DELETE http://localhost:8083/api/cart/items/1 \
  -H "X-User-ID: 1"
```

### Create Order
```bash
curl -X POST http://localhost:8083/api/orders \
  -H "X-User-ID: 1"
```

---

## 💡 Additional Notes

1. **Authentication**: Currently the API uses a simple `X-User-ID` header to identify users. In production, JWT or OAuth2 should be implemented.

2. **Validation**: It's recommended to add validations with `@Valid` and Bean Validation constraints on DTOs.

3. **Pagination**: For endpoints returning lists, implementing pagination using Spring Data's `Pageable` is recommended.

4. **CORS**: If consuming the API from a frontend, configure CORS appropriately.

5. **Error Handling**: Implement a global `@ControllerAdvice` for consistent exception handling.

6. **Security**: The current implementation doesn't include authentication/authorization. Consider implementing Spring Security for production use.
