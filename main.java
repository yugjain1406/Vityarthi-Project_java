import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

abstract class User implements Serializable {
    private String userId;
    private String name;
    private String username;
    private String password;

    public User(String userId, String name, String username, String password) {
        this.userId = userId;
        this.name = name;
        this.username = username;
        this.password = password;
    }

    public abstract String getRole();

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setName(String name) { this.name = name; }

    public boolean checkPassword(String attempt) {
        return password != null && password.equals(attempt);
    }

    @Override
    public String toString() {
        return userId + "," + name + "," + username + "," + password;
    }
}

class Customer extends User {
    private String phone;
    private String address;
    private Cart cart;
    private List<Order> orderHistory;

    public Customer(String userId, String name, String username, String password, String phone, String address) {
        super(userId, name, username, password);
        this.phone = phone;
        this.address = address;
        this.cart = new Cart();
        this.orderHistory = new ArrayList<>();
    }

    @Override
    public String getRole() { return "CUSTOMER"; }

    public String getPhone() { return phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public Cart getCart() { return cart; }
    public List<Order> getOrderHistory() { return orderHistory; }
    public void addOrderToHistory(Order order) { orderHistory.add(order); }

    @Override
    public String toString() {
        return getUserId() + "," + getName() + "," + getUsername() + "," + getPassword() + "," + phone + "," + address;
    }
}

class Admin extends User {
    public Admin(String userId, String name, String username, String password) {
        super(userId, name, username, password);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}

abstract class Food implements Serializable {
    private String foodId;
    private String name;
    private String category;
    private double price;
    private boolean available;

    public Food(String foodId, String name, String category, double price, boolean available) {
        this.foodId = foodId;
        this.name = name;
        this.category = category;
        this.price = price;
        this.available = available;
    }

    public abstract String getDescription();
    public abstract String getType();

    public String getFoodId() { return foodId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }

    @Override
    public String toString() {
        return foodId + "," + name + "," + category + "," + price + "," + available + "," + getType();
    }
}

class VegFood extends Food {
    public VegFood(String foodId, String name, String category, double price, boolean available) {
        super(foodId, name, category, price, available);
    }

    @Override
    public String getDescription() { return getName() + " (Veg) - " + getCategory() + " - Rs." + getPrice(); }

    @Override
    public String getType() { return "VEG"; }
}

class NonVegFood extends Food {
    public NonVegFood(String foodId, String name, String category, double price, boolean available) {
        super(foodId, name, category, price, available);
    }

    @Override
    public String getDescription() { return getName() + " (Non-Veg) - " + getCategory() + " - Rs." + getPrice(); }

    @Override
    public String getType() { return "NONVEG"; }
}

class CartItem {
    private Food food;
    private int quantity;

    public CartItem(Food food, int quantity) {
        this.food = food;
        this.quantity = quantity;
    }

    public Food getFood() { return food; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void increaseQuantity(int by) { this.quantity += by; }

    public void decreaseQuantity(int by) {
        this.quantity -= by;
        if (this.quantity < 0) this.quantity = 0;
    }

    public double getLineTotal() { return food.getPrice() * quantity; }

    @Override
    public String toString() { return food.getName() + " x " + quantity + " = Rs." + getLineTotal(); }
}

class Cart {
    private static final double TAX_RATE = 0.05;
    private static final double DISCOUNT_THRESHOLD = 300.0;
    private static final double FLAT_DISCOUNT = 50.0;
    private List<CartItem> items;

    public Cart() { items = new ArrayList<>(); }

    public void addItem(Food food, int quantity) {
        for (CartItem item : items) {
            if (item.getFood().getFoodId().equals(food.getFoodId())) {
                item.increaseQuantity(quantity);
                return;
            }
        }
        items.add(new CartItem(food, quantity));
    }

    public void removeItem(String foodId) {
        items.removeIf(item -> item.getFood().getFoodId().equals(foodId));
    }

    public void decreaseQuantity(String foodId, int by) {
        for (CartItem item : items) {
            if (item.getFood().getFoodId().equals(foodId)) {
                item.decreaseQuantity(by);
                if (item.getQuantity() == 0) removeItem(foodId);
                return;
            }
        }
    }

    public void clearCart() { items.clear(); }
    public List<CartItem> getItems() { return items; }
    public boolean isEmpty() { return items.isEmpty(); }

    public double getSubtotal() {
        double subtotal = 0;
        for (CartItem item : items) subtotal += item.getLineTotal();
        return subtotal;
    }

    public double getDiscount() { return getSubtotal() >= DISCOUNT_THRESHOLD ? FLAT_DISCOUNT : 0.0; }
    public double getTax() { return (getSubtotal() - getDiscount()) * TAX_RATE; }

    public double getFinalTotal() {
        double total = getSubtotal() - getDiscount() + getTax();
        return Math.round(total * 100.0) / 100.0;
    }
}

enum OrderStatus { ORDER_PLACED, PREPARING, OUT_FOR_DELIVERY, DELIVERED, CANCELLED }

class Order {
    private String orderId;
    private String customerId;
    private String customerName;
    private List<CartItem> items;
    private double totalAmount;
    private String paymentMethod;
    private OrderStatus status;
    private String orderDate;

    public Order(String orderId, String customerId, String customerName, List<CartItem> items,
                 double totalAmount, String paymentMethod, String orderDate) {
        this.orderId = orderId;
        this.customerId = customerId;
        this.customerName = customerName;
        this.items = new ArrayList<>(items);
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
        this.status = OrderStatus.ORDER_PLACED;
        this.orderDate = orderDate;
    }

    public String getOrderId() { return orderId; }
    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public List<CartItem> getItems() { return items; }
    public double getTotalAmount() { return totalAmount; }
    public String getPaymentMethod() { return paymentMethod; }
    public OrderStatus getStatus() { return status; }
    public void setStatus(OrderStatus status) { this.status = status; }
    public String getOrderDate() { return orderDate; }

    public String toFileFormat() {
        StringBuilder itemsStr = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            CartItem ci = items.get(i);
            itemsStr.append(ci.getFood().getFoodId()).append(":").append(ci.getQuantity());
            if (i != items.size() - 1) itemsStr.append("|");
        }
        return orderId + ";" + customerId + ";" + customerName + ";" + itemsStr + ";"
                + totalAmount + ";" + paymentMethod + ";" + status + ";" + orderDate;
    }

    @Override
    public String toString() { return "Order #" + orderId + " - " + status + " - Rs." + totalAmount; }
}

abstract class Payment {
    protected String paymentId;
    protected String orderId;
    protected double amount;
    protected boolean successful;

    public Payment(String paymentId, String orderId, double amount) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.amount = amount;
        this.successful = false;
    }

    public abstract boolean pay();
    public abstract String getMethodName();

    public String getPaymentId() { return paymentId; }
    public String getOrderId() { return orderId; }
    public double getAmount() { return amount; }
    public boolean isSuccessful() { return successful; }

    @Override
    public String toString() { return paymentId + "," + orderId + "," + amount + "," + getMethodName() + "," + successful; }
}

class CashPayment extends Payment {
    public CashPayment(String paymentId, String orderId, double amount) { super(paymentId, orderId, amount); }

    @Override
    public boolean pay() { successful = true; return true; }

    @Override
    public String getMethodName() { return "Cash on Delivery"; }
}

class InvalidInputException extends Exception {
    public InvalidInputException(String message) { super(message); }
}

class FileHandler {
    public static final String DATA_DIR = "data";
    public static final String CUSTOMER_FILE = DATA_DIR + File.separator + "customers.txt";
    public static final String FOOD_FILE = DATA_DIR + File.separator + "food.txt";
    public static final String ORDER_FILE = DATA_DIR + File.separator + "orders.txt";
    public static final String PAYMENT_FILE = DATA_DIR + File.separator + "payments.txt";

    public static void initDataFiles() {
        File dir = new File(DATA_DIR);
        if (!dir.exists()) dir.mkdirs();
        createIfMissing(CUSTOMER_FILE);
        createIfMissing(FOOD_FILE);
        createIfMissing(ORDER_FILE);
        createIfMissing(PAYMENT_FILE);
    }

    private static void createIfMissing(String path) {
        File f = new File(path);
        try {
            if (!f.exists()) f.createNewFile();
        } catch (IOException e) {
            System.out.println("Could not create file " + path + ": " + e.getMessage());
        }
    }

    private static void appendLine(String path, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to " + path + ": " + e.getMessage());
        }
    }

    private static List<String> readAllLines(String path) {
        List<String> lines = new ArrayList<>();
        File f = new File(path);
        if (!f.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.trim().isEmpty()) lines.add(line);
            }
        } catch (IOException e) {
            System.out.println("Error reading " + path + ": " + e.getMessage());
        }
        return lines;
    }

    private static void rewriteFile(String path, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error rewriting " + path + ": " + e.getMessage());
        }
    }

    public static void saveCustomer(Customer c) { appendLine(CUSTOMER_FILE, c.toString()); }

    public static List<Customer> loadCustomers() {
        List<Customer> customers = new ArrayList<>();
        for (String line : readAllLines(CUSTOMER_FILE)) {
            String[] p = line.split(",", -1);
            if (p.length >= 6) customers.add(new Customer(p[0], p[1], p[2], p[3], p[4], p[5]));
        }
        return customers;
    }

    public static void saveFood(Food food) { appendLine(FOOD_FILE, food.toString()); }

    public static List<Food> loadFood() {
        List<Food> foods = new ArrayList<>();
        for (String line : readAllLines(FOOD_FILE)) {
            String[] p = line.split(",", -1);
            if (p.length >= 6) {
                String id = p[0], name = p[1], category = p[2], type = p[5];
                double price = Double.parseDouble(p[3]);
                boolean available = Boolean.parseBoolean(p[4]);
                if ("NONVEG".equals(type)) foods.add(new NonVegFood(id, name, category, price, available));
                else foods.add(new VegFood(id, name, category, price, available));
            }
        }
        return foods;
    }

    public static void rewriteAllFood(List<Food> foods) {
        List<String> lines = new ArrayList<>();
        for (Food f : foods) lines.add(f.toString());
        rewriteFile(FOOD_FILE, lines);
    }

    public static void saveOrder(Order order) { appendLine(ORDER_FILE, order.toFileFormat()); }

    public static List<Order> loadOrders(List<Food> allFood) {
        List<Order> orders = new ArrayList<>();
        for (String line : readAllLines(ORDER_FILE)) {
            String[] p = line.split(";", -1);
            if (p.length < 8) continue;
            String orderId = p[0], customerId = p[1], customerName = p[2];
            List<CartItem> items = new ArrayList<>();
            if (!p[3].isEmpty()) {
                for (String piece : p[3].split("\\|")) {
                    String[] fq = piece.split(":");
                    if (fq.length == 2) {
                        Food food = findFoodById(allFood, fq[0]);
                        if (food != null) items.add(new CartItem(food, Integer.parseInt(fq[1])));
                    }
                }
            }
            double total = Double.parseDouble(p[4]);
            String paymentMethod = p[5], date = p[7];
            Order order = new Order(orderId, customerId, customerName, items, total, paymentMethod, date);
            order.setStatus(OrderStatus.valueOf(p[6]));
            orders.add(order);
        }
        return orders;
    }

    public static void rewriteAllOrders(List<Order> orders) {
        List<String> lines = new ArrayList<>();
        for (Order o : orders) lines.add(o.toFileFormat());
        rewriteFile(ORDER_FILE, lines);
    }

    private static Food findFoodById(List<Food> foods, String id) {
        for (Food f : foods) if (f.getFoodId().equals(id)) return f;
        return null;
    }

    public static void savePayment(Payment payment) { appendLine(PAYMENT_FILE, payment.toString()); }
}

class UserService {
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";
    private List<Customer> customers;
    private int nextCustomerNumber;

    public UserService() {
        customers = FileHandler.loadCustomers();
        nextCustomerNumber = customers.size() + 1;
    }

    public Customer register(String name, String username, String password, String phone, String address)
            throws InvalidInputException {
        validateRegistration(name, username, password, phone);
        if (findByUsername(username) != null) throw new InvalidInputException("Username already taken.");
        String id = "C" + String.format("%03d", nextCustomerNumber++);
        Customer customer = new Customer(id, name, username, password, phone, address);
        customers.add(customer);
        FileHandler.saveCustomer(customer);
        return customer;
    }

    private void validateRegistration(String name, String username, String password, String phone)
            throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Name cannot be empty.");
        if (username == null || username.trim().length() < 3)
            throw new InvalidInputException("Username must be at least 3 characters.");
        if (password == null || password.length() < 4)
            throw new InvalidInputException("Password must be at least 4 characters.");
        if (phone == null || !phone.matches("\\d{10}"))
            throw new InvalidInputException("Phone number must be exactly 10 digits.");
    }

    public Customer loginCustomer(String username, String password) throws InvalidInputException {
        Customer c = findByUsername(username);
        if (c == null || !c.checkPassword(password)) throw new InvalidInputException("Invalid username or password.");
        return c;
    }

    public Admin loginAdmin(String username, String password) throws InvalidInputException {
        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password))
            return new Admin("A001", "System Admin", ADMIN_USERNAME, ADMIN_PASSWORD);
        throw new InvalidInputException("Invalid admin credentials.");
    }

    private Customer findByUsername(String username) {
        for (Customer c : customers) if (c.getUsername().equalsIgnoreCase(username)) return c;
        return null;
    }

    public List<Customer> getAllCustomers() { return customers; }
}

class FoodService {
    private List<Food> foodList;
    private int nextFoodNumber;

    public FoodService() {
        foodList = FileHandler.loadFood();
        if (foodList.isEmpty()) seedDefaultMenu();
        nextFoodNumber = foodList.size() + 1;
    }

    private void seedDefaultMenu() {
        addFoodInternal(new VegFood("F001", "Margherita Pizza", "Pizza", 249.0, true));
        addFoodInternal(new VegFood("F002", "Farmhouse Pizza", "Pizza", 299.0, true));
        addFoodInternal(new NonVegFood("F003", "Chicken Pizza", "Pizza", 349.0, true));
        addFoodInternal(new VegFood("F004", "Veg Burger", "Burger", 99.0, true));
        addFoodInternal(new NonVegFood("F005", "Chicken Burger", "Burger", 139.0, true));
        addFoodInternal(new VegFood("F006", "Paneer Butter Masala", "Indian Food", 189.0, true));
        addFoodInternal(new NonVegFood("F007", "Butter Chicken", "Indian Food", 249.0, true));
        addFoodInternal(new VegFood("F008", "Veg Fried Rice", "Chinese Food", 149.0, true));
        addFoodInternal(new NonVegFood("F009", "Chicken Manchurian", "Chinese Food", 199.0, true));
        addFoodInternal(new VegFood("F010", "Cold Coffee", "Beverages", 79.0, true));
        addFoodInternal(new VegFood("F011", "Masala Lemonade", "Beverages", 59.0, true));
        addFoodInternal(new VegFood("F012", "Gulab Jamun", "Desserts", 69.0, true));
        addFoodInternal(new VegFood("F013", "Chocolate Brownie", "Desserts", 89.0, true));
    }

    private void addFoodInternal(Food food) {
        foodList.add(food);
        FileHandler.saveFood(food);
    }

    public List<Food> getAllFood() { return foodList; }

    public List<Food> searchByName(String keyword) {
        List<Food> result = new ArrayList<>();
        if (keyword == null) return result;
        String lower = keyword.toLowerCase();
        for (Food f : foodList) if (f.getName().toLowerCase().contains(lower)) result.add(f);
        return result;
    }

    public List<Food> filterByCategory(String category) {
        List<Food> result = new ArrayList<>();
        if ("All".equalsIgnoreCase(category)) return new ArrayList<>(foodList);
        for (Food f : foodList) if (f.getCategory().equalsIgnoreCase(category)) result.add(f);
        return result;
    }

    public Food addFood(String name, String category, double price, boolean isVeg, boolean available)
            throws InvalidInputException {
        if (name == null || name.trim().isEmpty()) throw new InvalidInputException("Food name cannot be empty.");
        if (price <= 0) throw new InvalidInputException("Price must be greater than zero.");
        String id = "F" + String.format("%03d", nextFoodNumber++);
        Food food = isVeg ? new VegFood(id, name, category, price, available)
                           : new NonVegFood(id, name, category, price, available);
        foodList.add(food);
        FileHandler.saveFood(food);
        return food;
    }

    public void updateFood(String foodId, String name, String category, double price) throws InvalidInputException {
        Food f = findById(foodId);
        if (f == null) throw new InvalidInputException("Food item not found.");
        if (price <= 0) throw new InvalidInputException("Price must be greater than zero.");
        f.setName(name);
        f.setCategory(category);
        f.setPrice(price);
        FileHandler.rewriteAllFood(foodList);
    }

    public void deleteFood(String foodId) throws InvalidInputException {
        Food f = findById(foodId);
        if (f == null) throw new InvalidInputException("Food item not found.");
        foodList.remove(f);
        FileHandler.rewriteAllFood(foodList);
    }

    public void setAvailability(String foodId, boolean available) throws InvalidInputException {
        Food f = findById(foodId);
        if (f == null) throw new InvalidInputException("Food item not found.");
        f.setAvailable(available);
        FileHandler.rewriteAllFood(foodList);
    }

    public Food findById(String foodId) {
        for (Food f : foodList) if (f.getFoodId().equals(foodId)) return f;
        return null;
    }
}

class OrderService {
    private List<Order> orders;
    private int nextOrderNumber;
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");

    public OrderService(FoodService foodService) {
        orders = FileHandler.loadOrders(foodService.getAllFood());
        nextOrderNumber = orders.size() + 1;
    }

    public Order placeOrder(Customer customer, String paymentMethod) throws InvalidInputException {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) throw new InvalidInputException("Your cart is empty.");
        String orderId = "ORD" + String.format("%04d", nextOrderNumber++);
        String date = DATE_FORMAT.format(new Date());
        Order order = new Order(orderId, customer.getUserId(), customer.getName(),
                cart.getItems(), cart.getFinalTotal(), paymentMethod, date);
        orders.add(order);
        FileHandler.saveOrder(order);
        customer.addOrderToHistory(order);
        cart.clearCart();
        return order;
    }

    public List<Order> getOrdersForCustomer(String customerId) {
        List<Order> result = new ArrayList<>();
        for (Order o : orders) if (o.getCustomerId().equals(customerId)) result.add(o);
        return result;
    }

    public List<Order> getAllOrders() { return orders; }

    public void updateStatus(String orderId, OrderStatus newStatus) throws InvalidInputException {
        for (Order o : orders) {
            if (o.getOrderId().equals(orderId)) {
                o.setStatus(newStatus);
                FileHandler.rewriteAllOrders(orders);
                return;
            }
        }
        throw new InvalidInputException("Order not found: " + orderId);
    }

    public double getTotalRevenue() {
        double total = 0;
        for (Order o : orders) if (o.getStatus() != OrderStatus.CANCELLED) total += o.getTotalAmount();
        return total;
    }
}

class PaymentService {
    private int nextPaymentNumber = 1;

    public Payment processPayment(String method, String orderId, double amount) throws InvalidInputException {
        String paymentId = "PAY" + String.format("%04d", nextPaymentNumber++);
        Payment payment = new CashPayment(paymentId, orderId, amount);
        boolean ok = payment.pay();
        FileHandler.savePayment(payment);
        if (!ok) throw new InvalidInputException("Payment failed.");
        return payment;
    }
}

public class main {
    static Scanner sc = new Scanner(System.in);
    static UserService userService;
    static FoodService foodService;
    static OrderService orderService;
    static PaymentService paymentService;

    public static void main(String[] args) {
        FileHandler.initDataFiles();
        userService = new UserService();
        foodService = new FoodService();
        orderService = new OrderService(foodService);
        paymentService = new PaymentService();

        while (true) {
            System.out.println("\n===== Canteen Food Ordering System =====");
            System.out.println("1. Customer Login");
            System.out.println("2. New Customer? Register");
            System.out.println("3. Admin Login");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": customerLogin(); break;
                case "2": registerCustomer(); break;
                case "3": adminLogin(); break;
                case "4": System.out.println("Thank you for using the Canteen Food Ordering System!"); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void registerCustomer() {
        System.out.println("\n--- Customer Registration ---");
        System.out.print("Name: ");
        String name = sc.nextLine();
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        System.out.print("Phone (10 digits): ");
        String phone = sc.nextLine();
        System.out.print("Address: ");
        String address = sc.nextLine();
        try {
            Customer c = userService.register(name, username, password, phone, address);
            System.out.println("Registration successful! Your customer ID is " + c.getUserId());
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void customerLogin() {
        System.out.println("\n--- Customer Login ---");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        try {
            Customer customer = userService.loginCustomer(username, password);
            System.out.println("Welcome, " + customer.getName() + "!");
            customerMenu(customer);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void adminLogin() {
        System.out.println("\n--- Admin Login ---");
        System.out.print("Username: ");
        String username = sc.nextLine();
        System.out.print("Password: ");
        String password = sc.nextLine();
        try {
            Admin admin = userService.loginAdmin(username, password);
            System.out.println("Welcome, " + admin.getName() + "!");
            adminMenu();
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void customerMenu(Customer customer) {
        while (true) {
            System.out.println("\n--- Customer Menu (" + customer.getName() + ") ---");
            System.out.println("1. View Menu");
            System.out.println("2. Search Food");
            System.out.println("3. Filter by Category");
            System.out.println("4. Add Item to Cart");
            System.out.println("5. View Cart");
            System.out.println("6. Remove Item from Cart");
            System.out.println("7. Checkout");
            System.out.println("8. Order History");
            System.out.println("9. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": printMenu(foodService.getAllFood()); break;
                case "2":
                    System.out.print("Enter keyword: ");
                    printMenu(foodService.searchByName(sc.nextLine()));
                    break;
                case "3":
                    System.out.print("Enter category (or All): ");
                    printMenu(foodService.filterByCategory(sc.nextLine()));
                    break;
                case "4": addToCart(customer); break;
                case "5": viewCart(customer); break;
                case "6":
                    System.out.print("Enter Food ID to remove: ");
                    customer.getCart().removeItem(sc.nextLine().trim());
                    System.out.println("Item removed.");
                    break;
                case "7": checkout(customer); break;
                case "8": viewOrderHistory(customer); break;
                case "9": System.out.println("Logged out."); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void printMenu(List<Food> foods) {
        if (foods.isEmpty()) {
            System.out.println("No items found.");
            return;
        }
        System.out.printf("%-6s %-25s %-15s %-8s %-8s %-6s%n", "ID", "Name", "Category", "Price", "Type", "Avail");
        for (Food f : foods) {
            System.out.printf("%-6s %-25s %-15s %-8.2f %-8s %-6s%n",
                    f.getFoodId(), f.getName(), f.getCategory(), f.getPrice(), f.getType(),
                    f.isAvailable() ? "Yes" : "No");
        }
    }

    static void addToCart(Customer customer) {
        System.out.print("Enter Food ID: ");
        String id = sc.nextLine().trim();
        Food food = foodService.findById(id);
        if (food == null || !food.isAvailable()) {
            System.out.println("Food item not available.");
            return;
        }
        System.out.print("Enter quantity: ");
        try {
            int qty = Integer.parseInt(sc.nextLine().trim());
            if (qty <= 0) {
                System.out.println("Quantity must be positive.");
                return;
            }
            customer.getCart().addItem(food, qty);
            System.out.println("Added to cart.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity.");
        }
    }

    static void viewCart(Customer customer) {
        Cart cart = customer.getCart();
        if (cart.isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        System.out.println("\n--- Your Cart ---");
        for (CartItem item : cart.getItems()) System.out.println(item);
        System.out.println("Subtotal: Rs." + cart.getSubtotal());
        System.out.println("Discount: Rs." + cart.getDiscount());
        System.out.println("Tax: Rs." + String.format("%.2f", cart.getTax()));
        System.out.println("Total: Rs." + cart.getFinalTotal());
    }

    static void checkout(Customer customer) {
        if (customer.getCart().isEmpty()) {
            System.out.println("Your cart is empty.");
            return;
        }
        viewCart(customer);
        System.out.print("Confirm order with Cash on Delivery? (y/n): ");
        if (!sc.nextLine().trim().equalsIgnoreCase("y")) {
            System.out.println("Order cancelled.");
            return;
        }
        try {
            double amount = customer.getCart().getFinalTotal();
            Order order = orderService.placeOrder(customer, "Cash on Delivery");
            paymentService.processPayment("Cash on Delivery", order.getOrderId(), amount);
            System.out.println("Order placed successfully! " + order);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewOrderHistory(Customer customer) {
        List<Order> orders = orderService.getOrdersForCustomer(customer.getUserId());
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        for (Order o : orders) System.out.println(o);
    }

    static void adminMenu() {
        while (true) {
            System.out.println("\n--- Admin Menu ---");
            System.out.println("1. View All Food Items");
            System.out.println("2. Add Food Item");
            System.out.println("3. Update Food Item");
            System.out.println("4. Delete Food Item");
            System.out.println("5. Toggle Food Availability");
            System.out.println("6. View All Orders");
            System.out.println("7. Update Order Status");
            System.out.println("8. View All Customers");
            System.out.println("9. Revenue Report");
            System.out.println("10. Logout");
            System.out.print("Choose an option: ");
            String choice = sc.nextLine().trim();

            switch (choice) {
                case "1": printMenu(foodService.getAllFood()); break;
                case "2": addFood(); break;
                case "3": updateFood(); break;
                case "4": deleteFood(); break;
                case "5": toggleAvailability(); break;
                case "6": viewAllOrders(); break;
                case "7": updateOrderStatus(); break;
                case "8": viewAllCustomers(); break;
                case "9": revenueReport(); break;
                case "10": System.out.println("Logged out."); return;
                default: System.out.println("Invalid option.");
            }
        }
    }

    static void addFood() {
        try {
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Category: ");
            String category = sc.nextLine();
            System.out.print("Price: ");
            double price = Double.parseDouble(sc.nextLine().trim());
            System.out.print("Veg? (y/n): ");
            boolean isVeg = sc.nextLine().trim().equalsIgnoreCase("y");
            Food food = foodService.addFood(name, category, price, isVeg, true);
            System.out.println("Food added with ID " + food.getFoodId());
        } catch (NumberFormatException e) {
            System.out.println("Price must be a valid number.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void updateFood() {
        try {
            System.out.print("Food ID to update: ");
            String id = sc.nextLine().trim();
            System.out.print("New Name: ");
            String name = sc.nextLine();
            System.out.print("New Category: ");
            String category = sc.nextLine();
            System.out.print("New Price: ");
            double price = Double.parseDouble(sc.nextLine().trim());
            foodService.updateFood(id, name, category, price);
            System.out.println("Food updated.");
        } catch (NumberFormatException e) {
            System.out.println("Price must be a valid number.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void deleteFood() {
        System.out.print("Food ID to delete: ");
        String id = sc.nextLine().trim();
        try {
            foodService.deleteFood(id);
            System.out.println("Food deleted.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void toggleAvailability() {
        System.out.print("Food ID: ");
        String id = sc.nextLine().trim();
        Food food = foodService.findById(id);
        if (food == null) {
            System.out.println("Food item not found.");
            return;
        }
        try {
            foodService.setAvailability(id, !food.isAvailable());
            System.out.println("Availability toggled.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAllOrders() {
        List<Order> orders = orderService.getAllOrders();
        if (orders.isEmpty()) {
            System.out.println("No orders yet.");
            return;
        }
        System.out.printf("%-8s %-15s %-15s %-10s %-18s %-15s%n",
                "Order ID", "Customer", "Date", "Total", "Payment", "Status");
        for (Order o : orders) {
            System.out.printf("%-8s %-15s %-15s %-10.2f %-18s %-15s%n",
                    o.getOrderId(), o.getCustomerName(), o.getOrderDate(),
                    o.getTotalAmount(), o.getPaymentMethod(), o.getStatus());
        }
    }

    static void updateOrderStatus() {
        System.out.print("Order ID: ");
        String orderId = sc.nextLine().trim();
        System.out.println("Statuses: " + Arrays.toString(OrderStatus.values()));
        System.out.print("New Status: ");
        String statusStr = sc.nextLine().trim().toUpperCase();
        try {
            OrderStatus status = OrderStatus.valueOf(statusStr);
            orderService.updateStatus(orderId, status);
            System.out.println("Order status updated.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    static void viewAllCustomers() {
        List<Customer> customers = userService.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
            return;
        }
        System.out.printf("%-6s %-15s %-12s %-12s %-20s%n", "ID", "Name", "Username", "Phone", "Address");
        for (Customer c : customers) {
            System.out.printf("%-6s %-15s %-12s %-12s %-20s%n",
                    c.getUserId(), c.getName(), c.getUsername(), c.getPhone(), c.getAddress());
        }
    }

    static void revenueReport() {
        double revenue = orderService.getTotalRevenue();
        int totalOrders = orderService.getAllOrders().size();
        System.out.println("\n--- Revenue Report ---");
        System.out.println("Total Revenue (excluding cancelled orders): Rs." + revenue);
        System.out.println("Total Orders Placed: " + totalOrders);
    }
}
