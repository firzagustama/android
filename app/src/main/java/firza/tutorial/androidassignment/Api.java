package firza.tutorial.androidassignment;

import android.content.Context;
import android.util.Log;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import firza.tutorial.androidassignment.model.Product;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class Api {
    private final String BASE_URL = "https://pegadaian.herokuapp.com";
    private final String API_PRODUCT = BASE_URL + "/product";

    public Api(Context context) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
    }

    public Observable<List<Product>> getAllProduct() {
        return Rx2AndroidNetworking.get(API_PRODUCT)
                .build()
                .getObjectListObservable(Product.class);
    }

    public Observable<Product> updateProduct(Product product) {
        return Rx2AndroidNetworking.post(API_PRODUCT)
                .setContentType("application/json")
                .addStringBody(product.toString())
                .build()
                .getObjectObservable(Product.class);
    }
}
