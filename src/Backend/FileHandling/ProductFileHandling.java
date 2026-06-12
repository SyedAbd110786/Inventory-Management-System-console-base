package Backend.FileHandling;

import model.Product;
import InputHelper.Input;

import java.io.*;
import java.util.*;

public class ProductFileHandling extends FileManager {
    private static final String FILE_PATH = "Products.txt";
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

    private void loadProductsFromFile() {
        productList.clear();
        productMap.clear();

        File file = new File(FILE_PATH);
        if (!file.exists()) {
            writeFile(FILE_PATH, getAllProductsData());
            return;
        }

        if (isOldBlockFormat(file)) {
            loadProductsFromOldBlockFormat();
            writeFile(FILE_PATH, getAllProductsData());
        } else {
            loadProductsFromTableFormat();
        }
    }

    private boolean isOldBlockFormat(File file) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String firstLine = br.readLine();
            return firstLine != null && firstLine.startsWith("-------Product--");
        } catch (IOException e) {
            return false;
        }
    }

    private void loadProductsFromTableFormat() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;

            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty() || line.startsWith("Product ID") || line.startsWith("-")) {
                    continue;
                }

                String[] values = line.split("\\|");
                if (values.length < 5) continue;

                addLoadedProduct(
                        values[0].trim(),
                        values[2].trim(),
                        values[1].trim(),
                        Integer.parseInt(values[3].trim()),
                        Double.parseDouble(values[4].trim())
                );
            }
        } catch (IOException e) {
            System.out.println("Could not load products: " + e.getMessage());
        }
    }

    private void loadProductsFromOldBlockFormat() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            String id = "", name = "", type = "";
            int stock = 0;
            double price = 0.0;
            boolean inBlock = false;

            while ((line = br.readLine()) != null) {
                if (line.startsWith("-------Product--")) {
                    inBlock = true;
                    id = ""; name = ""; type = "";
                    stock = 0; price = 0.0;
                    continue;
                }

                if (inBlock) {
                    if (line.trim().isEmpty()) {
                        addLoadedProduct(id, type, name, stock, price);
                        inBlock = false;
                        continue;
                    }

                    if (line.startsWith("Product ID : "))
                        id = line.replace("Product ID : ", "").trim();

                    else if (line.startsWith("Product Name : "))
                        name = line.replace("Product Name : ", "").trim();

                    else if (line.startsWith("Product Type : "))
                        type = line.replace("Product Type : ", "").trim();

                    else if (line.startsWith("Product Quantity : "))
                        stock = Integer.parseInt(line.replace("Product Quantity : ", "").trim());

                    else if (line.startsWith("Product Price : "))
                        price = Double.parseDouble(line.replace("Product Price : ", "").trim());
                }
            }

            if (inBlock) {
                addLoadedProduct(id, type, name, stock, price);
            }
        } catch (IOException e) {
            System.out.println("Could not load products: " + e.getMessage());
        }
    }

    private void addLoadedProduct(String id, String type, String name, int stock, double price) {
        if (id.isEmpty()) return;

        Product product = new Product(id, type, name, stock, price);
        productList.add(product);
        productMap.put(id, product);
    }

    public void saveProduct(Product product) {
        productList.add(product);
        productMap.put(product.getProductId(), product);
        writeFile(FILE_PATH, getAllProductsData());
    }

    public void updateProduct(Product product) {
        productMap.put(product.getProductId(), product);

        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getProductId().equals(product.getProductId())) {
                productList.set(i, product);
                break;
            }
        }

        writeFile(FILE_PATH, getAllProductsData());
    }

    private String getAllProductsData() {
        StringBuilder data = new StringBuilder();
        data.append(TABLE_HEADER).append("\n");
        data.append(TABLE_LINE).append("\n");

        for (Product product : productList) {
            data.append(formatProductRow(product)).append("\n");
        }

        return data.toString();
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
        }
        productMap.remove(productId);
        productList.remove(product);
        saveProductsToFile();
        System.out.println("Product  deleted successfully.");

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
        try (BufferedWriter writer = new BufferedWriter(
                new FileWriter("products.txt"))) {

            for (Product p : productList) {
                writer.write(p.getProductId() + "," +
                        p.getProductName() + "," +
                        p.getPrice());
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
