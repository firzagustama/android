package firza.tutorial.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firza.tutorial.androidassignment.adapter.CartAdapter;
import firza.tutorial.androidassignment.model.Detail;
import firza.tutorial.androidassignment.model.Product;
import firza.tutorial.androidassignment.model.Transaction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class CartActivity extends AppCompatActivity {

    @BindView(R.id.list_view)
    ListView listView;

    @BindView(R.id.btn_checkout)
    Button button;

    @BindView(R.id.cust_name)
    EditText customerName;

    @BindView(R.id.pay_amount)
    EditText payAmount;

    @BindView(R2.id.total)
    TextView total;

    private Api api;

    private List<Product> productList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ButterKnife.bind(this);

        api = new Api(this);

        showChart();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, CartActivity.class);
    }

    public void showChart() {
        showLoading(true);
        api.getProductInCart()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Product>>() {
                    @Override
                    public void accept(List<Product> products) throws Exception {
                        showLoading(false);
                        productList = products;
                        listView.setAdapter(new CartAdapter(products));

                        Integer sum = 0;
                        for(Product product : productList) {
                            sum += product.getPrice();
                        }
                        total.setText("Total : Rp. " + NumberFormat.getNumberInstance(Locale.GERMAN).format(sum));
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        Toast.makeText(CartActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void showLoading(Boolean bool) {
        if (bool) setContentView(R.layout.activity_loading);
        else      {
            setContentView(R.layout.activity_cart);
            ButterKnife.bind(this);
        }
    }

    @OnClick(R2.id.btn_checkout)
    public void checkout() {
        if (customerName.getText().length() == 0 || payAmount.getText().length() == 0) {
            Toast.makeText(this, "You must include customer name and pay amount!", Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setCustomerName(customerName.getText().toString());
        transaction.setPayAmount(Integer.valueOf(payAmount.getText().toString()));

        showLoading(true);
        api.storeTransaction(transaction)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Transaction>() {
                    @Override
                    public void accept(Transaction transaction) throws Exception {
                        postDetail(transaction);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        Toast.makeText(CartActivity.this, "Error storing data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void postDetail(Transaction transaction) {
        List<Detail> details = new ArrayList<>();
        for(Product product : productList) {
            // Set all qty to 1
            details.add(new Detail(product.getId(), transaction.getId(), 1));
        }

        api.storeBatchDetail(details)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Detail>>() {
                    @Override
                    public void accept(List<Detail> details) throws Exception {
//                        showLoading(false);
                        openTransactionActivity();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        Toast.makeText(CartActivity.this, "Error storing data", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void openTransactionActivity() {
        Intent intent = TransactionActivity.getStartIntent(this);
        startActivity(intent);
    }
}
