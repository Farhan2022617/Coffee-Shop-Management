import java.util.*;

// Enum for user roles
enum UserRole {
    CUSTOMER,
    EMPLOYEE
}

// Enum for payment methods
enum PaymentMethod {
    CASH,
    CARD
}

// Class representing a user
class User {
    private String username;
    private String password;
    private UserRole role;

    // Constructor
    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserRole getRole() {
        return role;
    }
}

// Class representing a coffee drink
class CoffeeDrink {
    private String name;
    private double price;

    // Constructor
    public CoffeeDrink(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Getters
    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }
}

// Class representing an order item
class OrderItem {
    private CoffeeDrink coffeeDrink;
    private int quantity;

    // Constructor
    public OrderItem(CoffeeDrink coffeeDrink, int quantity) {
        this.coffeeDrink = coffeeDrink;
        this.quantity = quantity;
    }

    // Getters
    public CoffeeDrink getCoffeeDrink() {
        return coffeeDrink;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getItemTotal() {
        return coffeeDrink.getPrice() * quantity;
    }
}

// Class representing a coffee order
class CoffeeOrder {
    private List<OrderItem> items;
    private double totalAmount;

    // Constructor
    public CoffeeOrder() {
        items = new ArrayList<>();
        totalAmount = 0;
    }

    // Method to add an order item
    public void addOrderItem(OrderItem item) {
        items.add(item);
        totalAmount += item.getItemTotal();
    }

    // Getters
    public List<OrderItem> getItems() {
        return items;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Method to display the order items
    public void displayOrderItems() {
        System.out.println("Cart Items:");
        for (OrderItem item : items) {
            System.out.printf("%s x%d - $%.2f\n", item.getCoffeeDrink().getName(), item.getQuantity(), item.getItemTotal());
        }
        System.out.printf("Total Amount: $%.2f\n", totalAmount);
    }
}

// Main class for Coffeeshop Management System
public class Coffeeshop {
    private List<CoffeeDrink> menu;
    private List<User> users;
    private CoffeeOrder currentOrder;

    // Constructor
    public Coffeeshop() {
        menu = new ArrayList<>();
        users = new ArrayList<>();
        currentOrder = new CoffeeOrder();
    }

    // Method to add a coffee drink to the menu
    public void addCoffeeDrink(CoffeeDrink coffeeDrink) {
        menu.add(coffeeDrink);
    }

    // Method to add a user
    public void addUser(User user) {
        users.add(user);
    }

    // Method to authenticate a user
    public User authenticateUser(String username, String password) {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;
    }

    // Method to display the menu
    public void displayMenu() {
        System.out.println("Menu:");
        for (int i = 0; i < menu.size(); i++) {
            CoffeeDrink drink = menu.get(i);
            System.out.printf("%d. %s - $%.2f\n", i + 1, drink.getName(), drink.getPrice());
        }
    }

    // Method to add items to the cart
    public void addToCart(Scanner scanner) {
        boolean adding = true;

        while (adding) {
            displayMenu();
            System.out.print("Enter the number of the coffee drink to add to the cart (or 0 to finish): ");
            int drinkNumber = scanner.nextInt();

            if (drinkNumber == 0) {
                adding = false;
            } else if (drinkNumber > 0 && drinkNumber <= menu.size()) {
                CoffeeDrink drink = menu.get(drinkNumber - 1);
                System.out.print("Enter the quantity: ");
                int quantity = scanner.nextInt();
                currentOrder.addOrderItem(new OrderItem(drink, quantity));
                System.out.println("Item added to the cart.");
            } else {
                System.out.println("Invalid selection. Please try again.");
            }
        }
    }

    // Method to choose payment method
    public PaymentMethod choosePaymentMethod(Scanner scanner) {
        System.out.println("Choose payment method:");
        System.out.println("1. Cash");
        System.out.println("2. Card");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        return choice == 1 ? PaymentMethod.CASH : PaymentMethod.CARD;
    }

    // Method to confirm the payment method
    public void confirmPaymentMethod(PaymentMethod paymentMethod) {
        System.out.println("Payment method confirmed: " + paymentMethod);
    }

    // Method to confirm the order
    public void confirmOrder(Scanner scanner) {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("No items in the cart to confirm.");
        } else {
            currentOrder.displayOrderItems();
            PaymentMethod paymentMethod = choosePaymentMethod(scanner);
            confirmPaymentMethod(paymentMethod);
            System.out.println("Order confirmed. Thank you!");
            generateReceipt();
            currentOrder = new CoffeeOrder(); // Reset the order after confirmation
        }
    }

    // Method to generate a receipt
    public void generateReceipt() {
        System.out.println("Receipt:");
        currentOrder.displayOrderItems();
        System.out.println("Thank you for shopping with us!");
    }

    // Method to display the cart items
    public void showCartItems() {
        if (currentOrder.getItems().isEmpty()) {
            System.out.println("Cart is empty.");
        } else {
            currentOrder.displayOrderItems();
        }
    }

    // Main method
    public static void main(String[] args) {
        // Create a Coffeeshop instance
        Coffeeshop system = new Coffeeshop();

        // Add coffee drinks to the menu
        system.addCoffeeDrink(new CoffeeDrink("Espresso", 2.5));
        system.addCoffeeDrink(new CoffeeDrink("Latte", 3.0));
        system.addCoffeeDrink(new CoffeeDrink("Cappuccino", 3.0));

        // Add users (customers and employees)
        system.addUser(new User("customer1", "024222", UserRole.CUSTOMER));
        system.addUser(new User("employee1", "password2", UserRole.EMPLOYEE));

        // Create a Scanner object to read input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user for username and password
        System.out.println("Welcome to Coffeeshop!");
        System.out.print("Enter username: ");
        String username = scanner.nextLine();

        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        // Authenticate the user
        User authenticatedUser = system.authenticateUser(username, password);
        if (authenticatedUser != null && authenticatedUser.getRole() == UserRole.CUSTOMER) {
            System.out.println("User authenticated. Role: " + authenticatedUser.getRole());

            // If the user is authenticated, provide interactive options
            boolean running = true;
            while (running) {
                System.out.println("\nOptions:");
                System.out.println("1. View menu");
                System.out.println("2. Add to cart");
                System.out.println("3. Show cart items");
                System.out.println("4. Confirm order");
                System.out.println("5. Exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        system.displayMenu();
                        break;
                    case 2:
                        system.addToCart(scanner);
                        break;
                    case 3:
                        system.showCartItems();
                        break;
                    case 4:
                        system.confirmOrder(scanner);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Authentication failed. Invalid username or password.");
        }

        // Close the scanner
        scanner.close();
    }
}
