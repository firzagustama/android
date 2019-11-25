package firza.tutorial.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import firza.tutorial.androidassignment.adapter.ProductAdapter;
import firza.tutorial.androidassignment.model.Product;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @BindView(R2.id.grid_view)
    GridView gridView;

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

    public void showLoading(Boolean bool) {
        if (bool) setContentView(R.layout.activity_loading);
        else      {
            setContentView(R.layout.activity_main);
            ButterKnife.bind(this);
        }
    }

    public void showProduct() {
        showLoading(true);
        api.getAllProduct()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Product>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Product> products) {
                        showLoading(false);
                        gridView.setAdapter(new ProductAdapter(products));
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
