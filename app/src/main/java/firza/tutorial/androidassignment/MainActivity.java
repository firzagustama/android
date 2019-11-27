package firza.tutorial.androidassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firza.tutorial.androidassignment.adapter.ProductAdapter;
import firza.tutorial.androidassignment.model.Product;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.grid_view)
    GridView gridView;

    @BindView(R2.id.btn_chart)
    ImageButton btnCart;

    private Api api;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        api = new Api(this);

        showProduct();
    }

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }

    public void showLoading(Boolean bool) {
        if (bool) setContentView(R.layout.activity_loading);
        else      {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
        }
    }

    public void showProduct() {
        String productsJson = getIntent().getStringExtra("products");
        if (productsJson != null) {
            Product[] products = new Gson().fromJson(productsJson, Product[].class);
            List<Product> productList = Arrays.asList(products);
            gridView.setAdapter(new ProductAdapter(productList));
            return;
        }

        showLoading(true);
        api.getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        showLoading(false);
                        gridView.setAdapter(new ProductAdapter(products));
                    }
                });
    }

    public void setEnabledCartButton(Boolean bool) {
        Integer resource = bool ? R.drawable.btn_info : R.drawable.btn_disabled;
        btnCart.setEnabled(bool);
        btnCart.setBackgroundResource(resource);
    }

    @OnClick(R2.id.btn_chart)
    public void openCartActivity() {
        Intent intent = CartActivity.getStartIntent(this);
        startActivity(intent);
    }

}
