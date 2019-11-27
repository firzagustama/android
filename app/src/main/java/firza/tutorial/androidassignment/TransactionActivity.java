package firza.tutorial.androidassignment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import firza.tutorial.androidassignment.adapter.TransactionAdapter;
import firza.tutorial.androidassignment.model.Transaction;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TransactionActivity extends AppCompatActivity {

    @BindView(R2.id.list_view)
    ListView listView;

    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        ButterKnife.bind(this);

        api = new Api(this);
        showTransaction();
    }

    public static Intent getStartIntent(Context context) {
        return new Intent(context, TransactionActivity.class);
    }

    public void setupListView(List<Transaction> transactions) {
        View footer = View.inflate(this, R.layout.row_transaction_footer, null);
        Button btnFooter = footer.findViewById(R.id.btn_product);
        btnFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.getStartIntent(TransactionActivity.this);
                startActivity(intent);
            }
        });

        listView.addFooterView(footer);
        listView.setAdapter(new TransactionAdapter(transactions));
    }

    public void showLoading(Boolean bool) {
        if (bool) setContentView(R.layout.activity_loading);
        else      {
            setContentView(R.layout.activity_transaction);
            ButterKnife.bind(this);
        }
    }

    public void showTransaction() {
        showLoading(true);
        api.getAllTransaction()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<Transaction>>() {
                    @Override
                    public void accept(List<Transaction> transactions) throws Exception {
                        showLoading(false);
                        setupListView(transactions);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        showLoading(false);
                        Toast.makeText(TransactionActivity.this, "Error getting data", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
