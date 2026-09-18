# Food Ordering System – Canteen

## Project Report

## 1. Introduction

The **Food Ordering System – Canteen** is a Java-based command-line application designed to manage food ordering operations in a college canteen. The system allows customers to view available food items, search and filter the menu, add items to their cart, place orders, and view their order details.

The application also provides an **Admin module** for managing food items, customer records, orders, and order status. This provides a structured way to manage the different activities involved in a canteen food ordering system.

The system uses file handling to store important information so that data can remain available even after the program is closed and started again.

---

## 2. Objective

The main objective of this project is to develop a simple food ordering application using **Java** and demonstrate important **Object-Oriented Programming (OOP)** concepts.

The system allows users to:

* View available food items
* Search and filter food items
* Add food items to a cart
* Place food orders
* Calculate the total bill
* Select Cash on Delivery as the payment method
* View order details
* Track order status
* Manage food items through the Admin module
* Manage customer information
* Manage customer orders
* Update order status
* View revenue information
* Store data using file handling
* Apply important Java and OOP concepts

---

## 3. Technologies Used

| Technology           | Details                             |
| -------------------- | ----------------------------------- |
| Programming Language | Java                                |
| Interface            | Command-Line Interface (CLI)        |
| Data Storage         | Text Files                          |
| Collections          | ArrayList, List                     |
| Packages             | `java.io`, `java.util`, `java.time` |
| IDE                  | VS Code / Any Java-supported IDE    |

The project does not require a graphical interface or an external database. It can be compiled and executed directly from the command line using the Java Development Kit (JDK).

---

## 4. System Features

### 4.1 Customer Module

The Customer Module provides the functionality required for ordering food from the canteen.

Customers can browse the menu, select food items, add them to their cart, and place an order.

### 4.2 View Food Menu

The system displays the food items available in the canteen.

The menu contains information such as:

* Food Item ID
* Food Item Name
* Category
* Price
* Availability

### 4.3 Search and Filter Food Items

Customers can search for available food items.

The filtering functionality helps customers find suitable food items without manually checking the entire menu.

### 4.4 Add Items to Cart

Customers can select food items from the menu and add them to their cart.

The cart stores the selected items until the customer confirms and places the order.

### 4.5 Place Order

After selecting the required food items, the customer can place an order.

The system records the selected items and calculates the total amount of the order.

### 4.6 Bill Calculation

The system automatically calculates the total cost of the selected food items based on their price and quantity.

This reduces the need for manual calculations.

### 4.7 Cash on Delivery

The system provides **Cash on Delivery (COD)** as a payment option.

The selected payment method is stored along with the order details.

### 4.8 View Order Details

Customers can view their order information, including:

* Order ID
* Customer Information
* Ordered Food Items
* Quantity
* Total Amount
* Payment Method
* Order Status
* Date and Time

### 4.9 Order Status

The system maintains the status of each order.

The administrator can update the order status as the order progresses through the canteen's order process.

---

## 5. Admin Module

The Admin Module provides functionality for managing the canteen system.

The administrator can manage menu items, customer information, orders, order status, and revenue-related information.

### 5.1 Food Item Management

The administrator can manage food items available in the canteen.

The Admin Module supports operations related to adding, viewing, updating, and deleting food items.

### 5.2 Customer Management

The system maintains customer records associated with food orders.

The administrator can access customer information required for managing the ordering process.

### 5.3 Order Management

The administrator can view and manage customer orders.

Order information includes:

* Order ID
* Customer
* Ordered Items
* Total Amount
* Payment Method
* Order Status
* Order Date and Time

### 5.4 Update Order Status

The administrator can update the status of an order.

This allows the progress of an order to be tracked from the time it is placed until it is completed.

### 5.5 Revenue Information

The system provides revenue-related information based on the orders stored in the application.

This provides the administrator with a basic overview of the revenue generated through food orders.

---

## 6. Object-Oriented Programming Concepts Used

The project demonstrates several important **Object-Oriented Programming concepts in Java**.

### Abstraction

Abstraction is used to represent common functionality while hiding unnecessary implementation details.

The project uses classes and methods to separate common operations from their specific implementations.

### Inheritance

Inheritance is used where classes share common properties and functionality.

It helps reduce code duplication and allows child classes to reuse functionality from their parent classes.

### Polymorphism

Polymorphism allows the same method or object reference to work with different implementations.

The project uses polymorphism where common operations can work with different objects according to their actual class.

### Encapsulation

Encapsulation is used to keep data inside classes and control access to it through methods.

Food item, customer, cart, and order information is maintained inside their respective classes.

### Classes and Objects

The project is based on classes and objects.

Different classes represent different components of the canteen system, such as food items, customers, carts, and orders.

---

## 7. Collections

The project uses Java Collection Framework classes to manage multiple records.

`ArrayList` and `List` are used for maintaining collections of:

* Food Items
* Customers
* Orders
* Cart Items

Collections make it easier to add, remove, search, and process multiple records during program execution.

---

## 8. Exception Handling

Exception handling is used to handle errors that may occur during program execution.

The project handles common situations such as:

* Invalid input
* Invalid numeric values
* Incorrect menu choices
* Invalid food item selection
* Invalid order-related operations
* File reading and writing errors
* Incorrect administrator login details

Exception handling helps prevent the program from terminating unexpectedly when invalid input or operations are encountered.

---

## 9. File Handling

The project uses **File Handling** to provide persistent data storage.

Important application records are stored in text files.

When the application starts, previously stored information can be loaded from the files.

Whenever relevant information is added or modified, the updated information can be written back to the files.

This allows data to remain available even after the application is closed.

The file-based approach keeps the project simple while demonstrating Java file handling concepts.

---

## 10. Input Validation

The program performs input validation to reduce incorrect data entry.

Some validations include:

* Required fields should not be empty.
* Numeric values must contain valid numbers.
* Food item information must contain valid values.
* Invalid menu selections are handled.
* Order-related inputs are validated.
* Incorrect administrator login details are rejected.
* Invalid values are handled using exception handling.

These checks help maintain valid information within the application.

---

## 11. Program Structure

The project is divided into different classes, with each class responsible for a specific part of the system.

### Main

Handles the main program execution and command-line interaction.

### FoodItem

Represents the food items available in the canteen and stores their relevant information.

### Customer

Represents customer information used during the ordering process.

### Cart

Maintains the food items selected by the customer before placing an order.

### Order

Represents a customer's food order and contains information such as items, amount, payment method, and status.

### Admin

Provides administrator-related operations for managing the canteen system.

### OrderStatus

An enumeration used to represent the different statuses of an order.

### Custom Exception

Used to handle application-specific errors.

---

## 12. Command-Line Execution

The project is designed to run directly from a terminal without requiring a graphical interface.

The main Java source file is:

```text
main.java
```

### Compile

```bash
javac main.java
```

### Run

```bash
java main
```

After running the program, the **Food Ordering System – Canteen** menu is displayed in the terminal.

The user can select the required option and follow the instructions displayed by the program.

---

## 13. Expected Output

When the program starts, the **Food Ordering System – Canteen** menu is displayed.

The customer can perform operations such as:

* View Food Menu
* Search Food
* Filter Food
* Add Food to Cart
* Place Order
* View Order Details
* Track Order Status

The administrator can access the Admin Module to perform operations related to:

* Food Item Management
* Customer Management
* Order Management
* Order Status
* Revenue Information

The exact menu displayed depends on the selected module and operation.

---

## 14. Advantages

The project provides several useful features:

* Simple command-line interface
* Easy food menu management
* Food search and filtering
* Cart functionality
* Automatic bill calculation
* Order management
* Order status tracking
* Admin management
* Revenue information
* Persistent data storage using file handling
* Java Collections for managing records
* Exception handling for invalid operations
* Demonstration of important Java OOP concepts

---

## 15. Future Enhancements

The project can be further improved by adding:

* Database connectivity
* Graphical User Interface (GUI)
* Online payment methods
* Customer registration and authentication
* Food ratings and reviews
* Order cancellation
* Detailed revenue reports
* Inventory management
* Receipt generation
* Web or mobile application support

---

## 16. Conclusion

The **Food Ordering System – Canteen** is a Java-based command-line application that provides basic functionality for managing food ordering operations in a college canteen.

The system allows customers to view food items, search and filter the menu, add items to a cart, place orders, calculate bills, select Cash on Delivery, and view order details and status. The Admin Module provides functionality for managing food items, customers, orders, order status, and revenue information.

The project demonstrates important Java concepts such as **classes and objects, constructors, inheritance, abstraction, polymorphism, encapsulation, collections, exception handling, enumerations, date and time handling, and file handling**.

Overall, the project provides a practical implementation of Java and Object-Oriented Programming concepts through a simple **Food Ordering System – Canteen** application.
