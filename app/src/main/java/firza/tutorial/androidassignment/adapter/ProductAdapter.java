package firza.tutorial.androidassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.widget.ANImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import firza.tutorial.androidassignment.Api;
import firza.tutorial.androidassignment.MainActivity;
import firza.tutorial.androidassignment.R;
import firza.tutorial.androidassignment.model.Product;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

import static android.view.View.*;

public class ProductAdapter extends BaseAdapter {

    private List<Product> productList;
    private Api api;

    public ProductAdapter(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Product getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return productList.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product, parent, false);

        ANImageView imageView = convertView.findViewById(R.id.image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);
        ImageButton btnChart = convertView.findViewById(R.id.add_to_chart);

        final Product product = productList.get(position);
        imageView.setImageUrl(product.getImageUrl());
        productName.setText(product.getName());
        productPrice.setText("Rp. " + NumberFormat.getNumberInstance(Locale.GERMAN).format(product.getPrice()));

        if (product.getInChart() == 1) {
            btnChart.setImageResource(R.drawable.ic_remove_shopping_cart_white_24dp);
            btnChart.setBackgroundResource(R.drawable.btn_danger);
        } else {
            btnChart.setImageResource(R.drawable.ic_add_shopping_cart_white_24dp);
            btnChart.setBackgroundResource(R.drawable.btn_success);
        }

        api = new Api(parent.getContext());
        btnChart.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer inCart = product.getInChart() == 0 ? 1 : 0;
                product.setInChart(inCart);
                notifyDataSetChanged();

                ((MainActivity)parent.getContext()).setEnabledCartButton(false);
                api.updateProduct(product)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Consumer<Product>() {
                            @Override
                            public void accept(Product product) throws Exception {
                                ((MainActivity)parent.getContext()).setEnabledCartButton(true);
                            }
                        });
            }
        });
        return convertView;
    }
}
