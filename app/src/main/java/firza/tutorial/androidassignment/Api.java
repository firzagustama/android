package firza.tutorial.androidassignment;

import android.content.Context;


import com.androidnetworking.AndroidNetworking;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import firza.tutorial.androidassignment.model.Product;
import io.reactivex.Observable;

public class Api {
    private final String BASE_URL = "https://pegadaian.herokuapp.com";
    private final String API_PRODUCT = BASE_URL + "/product";

    public Api(Context context) {
        AndroidNetworking.initialize(context);
    }

    public Observable<List<Product>> getAllProduct() {
        return Rx2AndroidNetworking.get(API_PRODUCT)
                .build()
                .getObjectListObservable(Product.class);
    }
}
