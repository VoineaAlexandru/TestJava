package com.company;

public class Orders {
    String orderId;

    String supplier;
    String description;
    String gtin;
    String currency;
    String price;
    public Orders() {

    }
    public Orders(String orderId, String supplier) {
        this.orderId = orderId;
        this.supplier = supplier;
        this.description = "desc";
        this.gtin = "gtin";
        this.currency = "currency";
        this.price = "price";
    }

    public Orders(String orderId, String supplier, String description, String gtin, String currency, String price) {
        this.orderId = orderId;
        this.supplier = supplier;
        this.description = description;
        this.gtin = gtin;
        this.currency = currency;
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGtin() {
        return gtin;
    }

    public void setGtin(String gtin) {
        this.gtin = gtin;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Orders:" +
                "orderId='" + orderId + '\n' +
                "     supplier='" + supplier + '\n' +
                "     description='" + description + '\n' +
                "     gtin='" + gtin + '\n' +
                "     currency='" + currency + '\n' +
                "     price='" + price ;
    }
}
