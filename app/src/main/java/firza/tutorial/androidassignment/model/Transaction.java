package firza.tutorial.androidassignment.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;

public class Transaction {
    Integer id;
    String customerName;
    Integer payAmount;

    public Transaction() {

    }

    public Transaction(Integer id, String customerName, Integer payAmount) {
        this.id = id;
        this.customerName = customerName;
        this.payAmount = payAmount;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
