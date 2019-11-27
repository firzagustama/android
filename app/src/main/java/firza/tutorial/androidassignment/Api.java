package firza.tutorial.androidassignment;

import android.content.Context;
import android.util.Log;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.interceptors.HttpLoggingInterceptor;
import com.google.gson.Gson;
import com.rx2androidnetworking.Rx2AndroidNetworking;

import java.util.List;

import firza.tutorial.androidassignment.model.Detail;
import firza.tutorial.androidassignment.model.Product;
import firza.tutorial.androidassignment.model.Transaction;
import io.reactivex.Observable;
import okhttp3.OkHttpClient;

public class Api {
    private final String BASE_URL = "https://pegadaian.herokuapp.com";
    private final String API_PRODUCT = BASE_URL + "/product";
    private final String API_CART = BASE_URL + "/product/chart";
    private final String API_TRANSACTION = BASE_URL + "/transaction";
    private final String API_DETAIL = BASE_URL + "/detail";
    private final String API_DETAIL_BATCH = BASE_URL + "/detail/all";

    private final String CONTENT_TYPE_JSON = "application/json";

    public Api(Context context) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.enableLogging(HttpLoggingInterceptor.Level.BODY);
    }

    public Observable<List<Product>> getAllProduct() {
        return Rx2AndroidNetworking.get(API_PRODUCT)
                .build()
                .getObjectListObservable(Product.class);
    }

    public Observable<List<Product>> getProductInCart() {
        return Rx2AndroidNetworking.get(API_CART)
                .build()
                .getObjectListObservable(Product.class);
    }

    public Observable<List<Transaction>> getAllTransaction() {
        return Rx2AndroidNetworking.get(API_TRANSACTION)
                .build()
                .getObjectListObservable(Transaction.class);
    }

    public Observable<Transaction> storeTransaction(Transaction transaction) {
        return Rx2AndroidNetworking.post(API_TRANSACTION)
                .setContentType(CONTENT_TYPE_JSON)
                .addStringBody(transaction.toString())
                .build()
                .getObjectObservable(Transaction.class);
    }

    public Observable<Detail> storeDetail(Detail detail) {
        return Rx2AndroidNetworking.post(API_DETAIL)
                .setContentType(CONTENT_TYPE_JSON)
                .addStringBody(detail.toString())
                .build()
                .getObjectObservable(Detail.class);
    }

    public Observable<List<Detail>> storeBatchDetail(List<Detail> details) {
        return Rx2AndroidNetworking.post(API_DETAIL_BATCH)
                .setContentType(CONTENT_TYPE_JSON)
                .addStringBody(details.toString())
                .build()
                .getObjectListObservable(Detail.class);
    }

    public Observable<Product> updateProduct(Product product) {
        return Rx2AndroidNetworking.post(API_PRODUCT)
                .setContentType(CONTENT_TYPE_JSON)
                .addStringBody(product.toString())
                .build()
                .getObjectObservable(Product.class);
    }
}
