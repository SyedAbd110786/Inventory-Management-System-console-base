package Backend.FileHandling;

import model.Product;
import InputHelper.Input;

import java.io.*;
import java.util.*;

public class ProductFileHandling extends FileManager {
    private static final String FILE_PATH = "Products.ser";
    private static final String TABLE_HEADER = String.format(
            "%-12s | %-22s | %-18s | %-8s | %-10s",
            "Product ID", "Product Name", "Product Type", "Quantity", "Price"
    );
    private static final String TABLE_LINE = "-".repeat(TABLE_HEADER.length());

    public  List<Product> productList = new ArrayList<>();
    public  Map<String, Product> productMap = new HashMap<>();

    public ProductFileHandling() {
        loadProductsFromFile();
    }

    public List<Product> getProductList() {
        return productList;
    }

    @SuppressWarnings("unchecked")
    private void loadProductsFromFile() {
        productList.clear();
        productMap.clear();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            saveProductsToFile();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = ois.readObject();
            if (obj instanceof List) {
                productList = (List<Product>) obj;
                for (Product product : productList) {
                    productMap.put(product.getProductId(), product);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Could not load products: " + e.getMessage());
        }
    }

    public void saveProduct(Product product) {
        productList.add(product);
        productMap.put(product.getProductId(), product);
        saveProductsToFile();
    }

    public void updateProduct(Product product) {
        productMap.put(product.getProductId(), product);

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductId().equals(product.getProductId())) {
                productList.set(i, product);
                break;
            }
        }

        saveProductsToFile();
    }

    private String formatProductRow(Product product) {
        return String.format(
                "%-12s | %-22s | %-18s | %-8d | %-10.2f",
                product.getProductId(),
                product.getProductName(),
                product.getProductType(),
                product.getStock(),
                product.getPrice()
        );
    }

    public void viewProducts() {
        if (productList.isEmpty()) {
            System.out.println("No products found. Add a product first.");
            return;
        }

        System.out.println(TABLE_HEADER);
        System.out.println(TABLE_LINE);

        for (Product product : productList) {
            System.out.println(formatProductRow(product));
        }
    }

    public void searchProduct(String productId) {
        String searchId = productId.trim();

        if (searchId.isEmpty()) {
            System.out.println("Product ID cannot be empty.");
            return;
        }

        Product product = productMap.get(searchId);

        if (product != null) {
            System.out.println();
            System.out.println("Product found:");
            product.printProductInfo();
        } else {
            System.out.println("No product found with ID " + searchId + ".");
        }
    }

    public void deleteProduct(String productId) {
        Product product  = productMap.get(productId);
        if(product == null){
            System.out.println("Product not fouund with this ID: " +productId);
        } else {
            productMap.remove(productId);
            productList.remove(product);
            saveProductsToFile();
            System.out.println("Product deleted successfully.");
        }
    }

    public void markProductSold(String productId) {
        String searchId = productId.trim();
        Product product = productMap.get(searchId);

        if (product == null) {
            System.out.println("No product found with ID " + searchId + ".");
            return;
        }

        System.out.println();
        System.out.println("Product Details:");
        product.printProductInfo();
        System.out.println();

        System.out.print("Enter quantity to sell: ");
        int quantity = Input.getPositiveInt();

        if (quantity > product.getStock()) {
            System.out.println("[ERROR] Insufficient stock. Available stock: " + product.getStock());
            return;
        }

        product.setStock(product.getStock() - quantity);
        updateProduct(product);
        System.out.println("Product sold successfully. Remaining stock: " + product.getStock());
    }

    public void saveProductsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(productList);
        } catch (IOException e) {
            System.out.println("Could not save products: " + e.getMessage());
        }
    }
}
