package firza.tutorial.androidassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import firza.tutorial.androidassignment.R;
import firza.tutorial.androidassignment.model.Product;

public class ProductAdapter extends BaseAdapter {

    private List<Product> productList;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_product, parent, false);

        ANImageView imageView = convertView.findViewById(R.id.image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);

        Product product = productList.get(position);
        imageView.setImageUrl(product.getImageUrl());
        productName.setText(product.getName());
        productPrice.setText("Rp. " + NumberFormat.getNumberInstance(Locale.GERMAN).format(product.getPrice()));

        return convertView;
    }
}
