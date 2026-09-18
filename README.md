# Vityarthi-Project_java
# Food Ordering System - Canteen

## About the Project

Food Ordering System - Canteen is a simple Java based console application which has been developed to simplify the food ordering process in a canteen.

The primary objective of the project is to ease the process of the customers as well as the canteen administrator. The canteen customers can create an account and access all the available food items, add items to cart, place orders and access order history.

The admin has separate set of options to manage the food menu, update order details, manage the food items and access basic information of registered customers and revenue.

The project is purely developed using Java and stores the required information in text files without using any external database.
---

## Features

### Customer

- Register a new account
- Login with username and password
- View available food menu
- Search food item
- Filter food items by category
- Add item to cart
- Update item quantity
- Remove item from cart
- View cart details
- Calculates subtotal, discount and tax amount automatically
- Place order
- Cash on delivery option
- View order history
- Logout

### Admin

- Admin login
- View available food items
- Add new food items
- Update food items
- Delete food items
- Toggle availability of food items
- View all orders
- Update order status
- View all registered customers
- View revenue report
- Logout

### Order Status

Following are the order statuses in this system.

- `ORDER_PLACED`
- `PREPARING`
- `OUT_FOR_DELIVERY`
- `DELIVERED`
- `CANCELLED`

### Billing

The system will automatically calculate the amount of the order based on the selected food items and their quantities.
A ₹50 discount will be given on an order with a subtotal of ₹300 or more.
5% tax will be applied to the subtotal amount after the discount has been deducted. The amount will be shown when the customer proceeds to checkout.
---

## Technologies Used

- Java
- Java OOPs concepts
- Java Collections
- Java Exception Handling
- Java File Handling
- Java Date/Time

### Java Libraries
The following are the java libraries that has been used.

- `java.io`
- `java.util`
- `java.text`

The application does not use any external database to store the information and instead uses text files.
---

## Project Structure

```text
Food Ordering System - Canteen
│
├── main.java
│
└── data
├── customers.txt
├── food.txt
├── orders.txt
└── payments.txt
User
├── Customer
└── Admin

Food
├── VegFood
└── NonVegFood

Cart
CartItem
Order

Payment
└── CashPayment

UserService
FoodService
OrderService
PaymentService

FileHandler
InvalidInputException
OrderStatus '''

## Steps to Install & Run

1. Ensure you have the **Java Development Kit (JDK)** installed on your system.
2. Clone or download this repository.
3. Open a terminal or command prompt and navigate to the folder containing `main.java`.
4. Compile the source code:

```bash
javac main.java
