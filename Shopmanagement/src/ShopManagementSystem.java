import java.util.*;
import java.util.stream.Collectors;

public class ShopManagementSystem {

    private List<Product> products;

    public ShopManagementSystem() {
        products = new ArrayList<>();
        initializeProducts();
    }

    public void initializeProducts() {
        products.add(new Product("P001", "Bánh Tráng Trộn", Category.FOOD, 5000, 100));
        products.add(new Product("P002", "Chả Cá Lăng", Category.FOOD, 15000, 50));
        products.add(new Product("P003", "Đèn Trang Trí", Category.HOUSEWARE, 250000, 20));
        products.add(new Product("P004", "Son Kem Lì", Category.COSMETICS, 120000, 80));
        products.add(new Product("P005", "Áo Thun Nam", Category.CLOTHING, 200000, 30));
    }

    public void printMainMenu() {
        System.out.println("\n----- MAIN MENU -----");
        System.out.println("1. Xem thông tin sản phẩm");
        System.out.println("2. In danh sách sản phẩm có giá trên 10.000");
        System.out.println("3. Đếm số sản phẩm có số lượng bán từ 50 trở lên");
        System.out.println("4. Liệt kê sản phẩm dựa theo loại sản phẩm");
        System.out.println("5. Sắp xếp sản phẩm theo số lượng bán được");
        System.out.println("6. Đưa ra sản phẩm bán được nhiều nhất");
        System.out.println("7. Sắp xếp sản phẩm theo tên");
        System.out.println("8. Tạo mới sản phẩm");
        System.out.println("9. Chỉnh sửa thông tin sản phẩm");
        System.out.println("10. Xoá sản phẩm");
        System.out.println("0. Thoát chương trình");
        System.out.println("---------------------");
    }

    public void viewProductInfo() {
        System.out.println("\n----- PRODUCT INFORMATION -----");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void printProductsAbovePrice(long price) {
        List<Product> productsAbovePrice = products.stream()
                .filter(product -> product.getPrice() > price)
                .collect(Collectors.toList());
        System.out.println("\n----- PRODUCTS ABOVE " + price + " -----");
        for (Product product : productsAbovePrice) {
            System.out.println(product);
        }
    }

    public int countProductsSoldAboveQuantity(int quantity) {
        long count = products.stream()
                .filter(product -> product.getQuantitySold() >= quantity)
                .count();
        System.out.println("\nNumber of products sold above " + quantity + ": " + count);
        return (int) count;
    }

    public void listProductsByCategory() {
        Map<Category, List<Product>> productsByCategory = products.stream()
                .collect(Collectors.groupingBy(Product::getCategory));
        System.out.println("\n----- PRODUCTS BY CATEGORY -----");
        for (Map.Entry<Category, List<Product>> entry : productsByCategory.entrySet()) {
            System.out.println("Category: " + entry.getKey());
            for (Product product : entry.getValue()) {
                System.out.println(product);
            }
        }
    }

    public void sortProductsByQuantitySold() {
        products.sort(Comparator.comparingInt(Product::getQuantitySold).reversed());
        System.out.println("\n----- PRODUCTS SORTED BY QUANTITY SOLD -----");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public Product getBestSellingProduct() {
        Product bestSellingProduct = products.stream()
                .max(Comparator.comparingInt(Product::getQuantitySold))
                .orElse(null);
        System.out.println("\n----- BEST SELLING PRODUCT -----");
        if (bestSellingProduct != null) {
            System.out.println(bestSellingProduct);
        }
        return bestSellingProduct;
    }

    public void sortProductsByName() {
        products.sort(Comparator.comparing(Product::getName));
        System.out.println("\n----- PRODUCTS SORTED BY NAME -----");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    public void createNewProduct(Scanner scanner) {
        System.out.println("\n----- CREATE NEW PRODUCT -----");
        System.out.print("Enter product ID: ");
        String id = scanner.next();
        System.out.print("Enter product name: ");
        String name = scanner.next();
        System.out.print("Enter product category (FOOD, HOUSEWARE, COSMETICS, CLOTHING): ");
        Category category = Category.valueOf(scanner.next().toUpperCase());
        System.out.print("Enter product price: ");
        long price = scanner.nextLong();
        System.out.print("Enter initial quantity sold: ");
        int quantitySold = scanner.nextInt();
        Product newProduct = new Product(id, name, category, price, quantitySold);
        products.add(newProduct);
        System.out.println("New product added successfully.");
    }

    public void editProductInfo(Scanner scanner) {
        System.out.println("\n----- EDIT PRODUCT INFORMATION -----");
        System.out.print("Enter product ID to edit: ");
        String id = scanner.next();
        Product productToEdit = findProductById(id);
        if (productToEdit != null) {
            System.out.println("Enter new product name (current: " + productToEdit.getName() + "): ");
            String newName = scanner.next();
            productToEdit.setName(newName);

            System.out.println("Enter new product category (current: " + productToEdit.getCategory() + "): ");
            Category newCategory = Category.valueOf(scanner.next().toUpperCase());
            productToEdit.setCategory(newCategory);

            System.out.println("Enter new product price (current: " + productToEdit.getPrice() + "): ");
            long newPrice = scanner.nextLong();
            productToEdit.setPrice(newPrice);

            System.out.println("Enter new quantity sold (current: " + productToEdit.getQuantitySold() + "): ");
            int newQuantitySold = scanner.nextInt();
            productToEdit.setQuantitySold(newQuantitySold);

            System.out.println("Product information updated successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public void deleteProduct(Scanner scanner) {
        System.out.println("\n----- DELETE PRODUCT -----");
        System.out.print("Enter product ID to delete: ");
        String id = scanner.next();
        Product productToDelete = findProductById(id);
        if (productToDelete != null) {
            products.remove(productToDelete);
            System.out.println("Product deleted successfully.");
        } else {
            System.out.println("Product not found.");
        }
    }

    public Product findProductById(String id) {
        for (Product product : products) {
            if (product.getId().equals(id)) {
                return product;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ShopManagementSystem shop = new ShopManagementSystem();

        int choice;
        do {
            shop.printMainMenu();
            System.out.print("Your choice: ");
            choice = scanner.nextInt();

            switch (choice) { case 1:
                shop.viewProductInfo();
                break;
                case 2:
                    shop.printProductsAbovePrice(10000);
                    break;
                case 3:
                    shop.countProductsSoldAboveQuantity(50);
                    break;
                case 4:
                    shop.listProductsByCategory();
                    break;
                case 5:
                    shop.sortProductsByQuantitySold();
                    break;
                case 6:
                    shop.getBestSellingProduct();
                    break;
                case 7:
                    shop.sortProductsByName();
                    break;
                case 8:
                    shop.createNewProduct(scanner);
                    break;
                case 9:
                    shop.editProductInfo(scanner);
                    break;
                case 10:
                    shop.deleteProduct(scanner);
                    break;
                case 0:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);

        scanner.close();
    }
}

class Product {
    private String id;
    private String name;
    private Category category;
    private long price;
    private int quantitySold;

    public Product(String id, String name, Category category, long price, int quantitySold) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantitySold = quantitySold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public void setQuantitySold(int quantitySold) {
        this.quantitySold = quantitySold;
    }

    @Override
    public String toString() {
        return "ID: " + id + ", Name: " + name + ", Category: " + category + ", Price: " + price + ", Quantity Sold: " + quantitySold;
    }
}

enum Category {
    FOOD, HOUSEWARE, COSMETICS, CLOTHING;
}


