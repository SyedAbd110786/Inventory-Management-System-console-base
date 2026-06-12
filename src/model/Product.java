package model;

public class Product {
    private String productType;
    private String productName;
    private String productId;
    private int stock;
    private double price;
    private static int countProducts = 0;

    public Product(String productType, String productName, int stock, double price) {
        this.productType = productType;
        this.productName = productName;
        countProducts++;
        this.productId = "P" + countProducts;
        this.stock = stock;
        this.price = price;
    }

    public Product(String productId, String productType, String productName, int stock, double price) {
        this.productId = productId;
        this.productType = productType;
        this.productName = productName;
        this.stock = stock;
        this.price = price;

        int idNumber = Integer.parseInt(productId.replace("P", ""));
        if (idNumber > countProducts) {
            countProducts = idNumber;
        }
    }

    public String getProductType() { return productType; }
    public String getProductName() { return productName; }
    public String getProductId() { return productId; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }

    public void setProductType(String productType) {this.productType = productType;}
    public void setProductName(String productName) {this.productName = productName;}
    public void setStock(int stock) {this.stock = stock;}
    public void setPrice(double price) {this.price = price;}

    public void printProductInfo() {
        System.out.println("Product Saved Information");
        System.out.println("Product ID   : " + getProductId());
        System.out.println("Product Type : " + getProductType());
        System.out.println("Product Name : " + getProductName());
        System.out.println("Quantity     : " + getStock());
        System.out.println("Price        : " + getPrice());
    }

    public String getData() {
        return String.format("%-12s | %-22s | %-18s | %-8d | %-10.2f",
                getProductId(),
                getProductName(),
                getProductType(),
                getStock(),
                getPrice());
    }
}
