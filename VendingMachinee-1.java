import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private double price;
    private int stock;

    Product(int id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void reduceStock() {
        if (stock > 0) {
            stock--;
        }
    }

    public void addStock(int quantity) {
        stock += quantity;
    }

    public String toString() {
        return id + "  " + name + "  Rs." + price
                + "  Stock: " + stock;
    }
}

public class VendingMachinee {

    static Scanner sc = new Scanner(System.in);
    static double balance = 0;

    static Product[] products = {
        new Product(101, "Chips", 20, 5),
        new Product(102, "Chocolate", 30, 5),
        new Product(103, "Juice", 40, 5),
        new Product(104, "Biscuits", 25, 5),
        new Product(105, "Water", 15, 5)
    };

    public static void main(String[] args) {

        int choice = 0;
        System.out.println("=============================");
        System.out.println("      VENDING MACHINE    ");
        System.out.println("=============================");
        while (choice != 7) {
            try {
                System.out.println("\n1. View Products");
                System.out.println("2. Select Product");
                System.out.println("3. Buy Product");
                System.out.println("4. Add Money");
                System.out.println("5. Check Balance");
                System.out.println("6. Restock Product");
                System.out.println("7. Exit");

                System.out.print("Enter your choice: ");
                choice = Integer.parseInt(sc.nextLine());

                switch (choice) {
                    case 1:
                        viewProducts();
                        break;

                    case 2:
                        selectProduct();
                        break;

                    case 3:
                        buyProduct();
                        break;

                    case 4:
                        addMoney();
                        break;

                    case 5:
                        checkBalance();
                        break;

                    case 6:
                        restockProduct();
                        break;

                    case 7:
                        System.out.println("Thank you for using the vending machine!");
                        return;

                    default:
                        System.out.println("Invalid choice. Try again.");    


                }

            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            } catch (Exception e) {
                System.out.println("Something went wrong. Please try again.");
            }
        }

        sc.close();
    }

    static void viewProducts() {
        System.out.println("\nID   Product       Price    Stock");

        for (Product p : products) {
            System.out.println(p);
        }
    }

    static Product findProduct(int id) {
        for (Product p : products) {
            if (p.getId() == id) {
                return p;
            }
        }

        return null;
    }

    static void selectProduct() {
        System.out.print("Enter product ID: ");
        try {
            int id = Integer.parseInt(sc.nextLine());
            Product p = findProduct(id);

            if (p == null) {
                System.out.println("Product not found.");
            } else {
                System.out.println("Selected: " + p.getName());
                System.out.println("Price: Rs." + p.getPrice());
                System.out.println("Available stock: " + p.getStock());
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid product ID.");
        }
    }

    static void buyProduct() {
        System.out.print("Enter product ID to buy: ");

        try {
            int id = Integer.parseInt(sc.nextLine());
            Product p = findProduct(id);

            if (p == null) {
                System.out.println("Product not found.");
            } else if (p.getStock() == 0) {
                System.out.println("Sorry, this product is out of stock.");
            } else if (balance < p.getPrice()) {
                System.out.println("Insufficient balance.");
                System.out.println("Please add Rs."
                        + (p.getPrice() - balance) + " more.");
            } else {
                balance -= p.getPrice();
                p.reduceStock();

                System.out.println("Purchase successful!");
                System.out.println("You received: " + p.getName());
                System.out.println("Remaining balance: Rs." + balance);

                savePurchase(p);
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid product ID.");
        }
    }

    static void addMoney() {
        System.out.print("Enter amount to add: ");

        try {
            double amount = Double.parseDouble(sc.nextLine());

            if (amount > 0) {
                balance += amount;
                System.out.println("Money added successfully.");
                System.out.println("Current balance: Rs." + balance);
            } else {
                System.out.println("Amount must be greater than zero.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid amount.");
        }
    }

    static void checkBalance() {
        System.out.println("Your current balance is Rs." + balance);
    }

    static void restockProduct() {
        System.out.print("Enter product ID: ");

        try {
            int id = Integer.parseInt(sc.nextLine());
            Product p = findProduct(id);

            if (p == null) {
                System.out.println("Product not found.");
                return;
            }

            System.out.print("Enter quantity to add: ");
            int quantity = Integer.parseInt(sc.nextLine());

            if (quantity > 0) {
                p.addStock(quantity);
                System.out.println("Product restocked successfully.");
                System.out.println("Updated stock: " + p.getStock());
            } else {
                System.out.println("Quantity must be greater than zero.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Please enter valid numbers.");
        }
    }

    static void savePurchase(Product p) {
        try (FileWriter writer =
                     new FileWriter("purchase_history.txt", true)) {

            writer.write("Product: " + p.getName()
                    + ", Price: Rs." + p.getPrice() + "\n");

        } catch (IOException e) {
            System.out.println("Could not save purchase history.");
        }
    }
}
