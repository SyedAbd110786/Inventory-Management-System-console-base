package ui;

import Backend.FileHandling.ProductFileHandling;
import InputHelper.Input;
import model.Product;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductMenu extends Menu {
    ProductFileHandling pfh = new ProductFileHandling();

    Product product;

    ProductMenu() {
        super("Product Menu");
    }

    @Override
    protected void printOptions() {
        System.out.println("1. Add Product");
        System.out.println("2. View All Products");
        System.out.println("3. Search Product");
        System.out.println("4. Update Product");
        System.out.println("5. Delete Product");
        System.out.println("6. Restock Product");
        System.out.println("7. Sell Product");
        System.out.println("8. View Low Stock");
        System.out.println("0. Back to Main Menu");
        printLine();
    }

    @Override
    protected void handleChoice() {
        System.out.print("Choose an option (0-8): ");
        int choice = Input.getIntInRange(0, 8);

        switch(choice){
            case 1:
                addProduct();
                Input.pressEnterToContinue();
                break;
            case 2:
                viewAllProducts();
                Input.pressEnterToContinue();
                break;
            case 3:
                searchProduct();
                Input.pressEnterToContinue();
                break;
            case 4:
                updateProduct();
                Input.pressEnterToContinue();
                break;
            case 5:
                deleteProduct();
                Input.pressEnterToContinue();
                break;
            case 6:
                reStockProduct();
                Input.pressEnterToContinue();
                break;
            case 7:
                sellProduct();
                Input.pressEnterToContinue();
                break;
            case 8:
                viewLowStock();
                Input.pressEnterToContinue();
                break;
            case 0:
                running = false;
                return;
        }
    }

    private void addProduct(){
        printLine();
        System.out.println("ADD NEW PRODUCT");
        printLine();

        System.out.print("Product name: ");
        String productName = Input.getNonEmptyString();

        System.out.print("Product type: ");
        String productType = Input.getNonEmptyString();

        System.out.print("Quantity: ");
        int stock = Input.getPositiveInt();

        System.out.print("Price: ");
        double price = Input.getPositiveDouble();

        product = new Product(productType, productName, stock, price);
        pfh.saveProduct(product);
        printSuccess("Product saved successfully with ID " + product.getProductId() + " in Products.txt.");
    }

    private void viewAllProducts(){
        printLine();
        System.out.println("ALL PRODUCTS");
        printLine();
        pfh.viewProducts();
    }

    private void searchProduct(){
        printLine();
        System.out.println("SEARCH PRODUCT");
        printLine();

        System.out.print("Product ID: ");
        String productId = Input.getNonEmptyString();
        pfh.searchProduct(productId);
    }

    private void updateProduct(){
        printLine();
        System.out.println("UPDATE PRODUCT");
        printLine();

        System.out.print("Product ID: ");
        String productId = Input.getNonEmptyString();
        Product product = pfh.productMap.get(productId);

        if (product == null) {
            printError("No product found with ID " + productId + ".");
            return;
        }

        System.out.println();
        System.out.println("Current product details:");
        product.printProductInfo();
        System.out.println();
        System.out.println("What do you want to update?");
        System.out.println("1. Product Type");
        System.out.println("2. Product Name");
        System.out.println("3. Quantity");
        System.out.println("4. Price");
        System.out.println("0. Cancel");
        printLine();

        System.out.print("Choose an option (0-4): ");
        int choice = Input.getIntInRange(0, 4);

        switch(choice){
            case 1:
                System.out.print("New product type: ");
                product.setProductType(Input.getNonEmptyString());
                pfh.updateProduct(product);
                showUpdatedProduct(product);
                break;
            case 2:
                System.out.print("New product name: ");
                product.setProductName(Input.getNonEmptyString());
                pfh.updateProduct(product);
                showUpdatedProduct(product);
                break;
            case 3:
                System.out.print("New quantity: ");
                product.setStock(Input.getPositiveInt());
                pfh.updateProduct(product);
                showUpdatedProduct(product);
                break;
            case 4:
                System.out.print("New price: ");
                product.setPrice(Input.getPositiveDouble());
                pfh.updateProduct(product);
                showUpdatedProduct(product);
                break;
            case 0:
                printInfo("Update cancelled.");
                return;
        }
    }

    private void showUpdatedProduct(Product product) {
        printSuccess("Product updated and saved in Products.txt.");
        System.out.println();
        product.printProductInfo();
    }

    private void deleteProduct(){
        System.out.print("Enter product ID:  ");
        pfh.deleteProduct(Input.getNonEmptyString());

    }

    private void reStockProduct(){
        System.out.print("Enter Product ID:");
        String productId = Input.getNonEmptyString();

        Product product  = pfh.productMap.get(productId);
        if(product == null ){
            System.out.println("Product not found with this ID:"+productId);
            return;
        }
        product.printProductInfo();
        System.out.print("Enter new quantity :");
        product.setStock(Input.getPositiveInt());
        pfh.updateProduct(product);
        System.out.println("Stock updated successfully.");
        showUpdatedProduct(product);

    }
    private void sellProduct(){
        System.out.print("Enter Product ID:");
        String productId = Input.getNonEmptyString();
        pfh.markProductSold(productId);

    }

    private void viewLowStock() {
        List<Product> productList = pfh.getProductList();

        boolean lowStockFound = false;

        for (Product product : productList) {
            if (product.getStock() < 10) {
                if (!lowStockFound) {
                    System.out.println("Low-stock products:");
                    lowStockFound = true;
                }
                product.printProductInfo();
            }
        }

        if (!lowStockFound) {
            System.out.println("All products have sufficient stock levels.");
        }
    }
}
