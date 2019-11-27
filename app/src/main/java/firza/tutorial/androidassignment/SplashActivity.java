package firza.tutorial.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firza.tutorial.androidassignment.model.Product;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SplashActivity extends AppCompatActivity {

    @BindView(R2.id.splash_progress)
    ProgressBar progressBar;

    @BindView(R2.id.splash_button)
    Button button;

    private Api api;
    private List<Product> products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);

        api = new Api(this);
        getProduct();
    }

    private void showLoading(Boolean bool) {
        if (bool) {
            progressBar.setVisibility(View.VISIBLE);
            button.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            button.setVisibility(View.VISIBLE);
        }
    }

    public void getProduct() {
        showLoading(true);
        api.getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> productList) throws Exception {
                        showLoading(false);
                        products = productList;
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        Toast.makeText(SplashActivity.this, "Please restart your App", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @OnClick(R2.id.splash_button)
    public void showMainActivity() {
        Intent intent = MainActivity.getStartIntent(this);
        intent.putExtra("products", products.toString());
        startActivity(intent);
    }
}
