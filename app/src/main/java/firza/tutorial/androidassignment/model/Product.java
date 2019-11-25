package firza.tutorial.androidassignment.model;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Product {
    Integer id;
    String imageUrl;
    String name;
    Integer price;
    Integer chartQty;
    Integer inChart;

    public Product(Integer id, String imageUrl, String name, Integer price, Integer chartQty, Integer inChart) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.name = name;
        this.price = price;
        this.chartQty = chartQty;
        this.inChart = inChart;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getChartQty() {
        return chartQty;
    }

    public void setChartQty(Integer chartQty) {
        this.chartQty = chartQty;
    }

    public Integer getInChart() {
        return inChart;
    }

    public void setInChart(Integer inChart) {
        this.inChart = inChart;
    }

    @NonNull
    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
