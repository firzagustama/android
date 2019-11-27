package firza.tutorial.androidassignment.model;

import android.content.Intent;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Detail {
    Integer productId;
    Product product;
    Integer transactionId;
    Transaction transaction;
    Integer qty;

    public Detail() {}

    public Detail(Integer productId, Integer transactionId, Integer qty) {
        this.productId = productId;
        this.transactionId = transactionId;
        this.qty = qty;
    }

    public Detail(Product product, Transaction transaction, Integer qty) {
        this.product = product;
        this.transaction = transaction;
        this.qty = qty;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
