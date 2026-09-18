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
OrderStatus
## How to Run
Requirements
Java JDK installed on your system.
A Java IDE such as VS Code, IntelliJ IDEA, or Eclipse (optional).
Steps
Download or clone this repository.
Open the project folder in your IDE or terminal.
Make sure the main file is named main.java.
Open the terminal in the project folder.
Compile the program:
javac main.java
Run the program:
java main
The required data folder and text files will be created automatically when the program starts.
## Instructions for Testing
Customer Testing
Start the program.
Select 2. New Customer? Register.
Enter the name, username, password, 10-digit phone number and address.
Login using the registered username and password.
Select 1. View Menu to see the available food items.
Use 2. Search Food to search for a food item.
Use 3. Filter by Category to filter the menu.
Select 4. Add Item to Cart and enter a Food ID and quantity.
Select 5. View Cart to check the subtotal, discount, tax and final amount.
Select 7. Checkout and confirm the order.
Select 8. Order History to check the placed order.
Admin Testing
Return to the main menu and select 3. Admin Login.
Use the following credentials:
Username: admin
Password: admin123
Test the following options:
View food items
Add a food item
Update a food item
Delete a food item
Change food availability
View all orders
Update order status
View customers
View revenue report
