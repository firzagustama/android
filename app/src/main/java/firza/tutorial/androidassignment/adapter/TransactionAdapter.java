package firza.tutorial.androidassignment.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import firza.tutorial.androidassignment.R;
import firza.tutorial.androidassignment.model.Transaction;

public class TransactionAdapter extends BaseAdapter {

    List<Transaction> transactions;

    public TransactionAdapter(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public int getCount() {
        return transactions.size();
    }

    @Override
    public Transaction getItem(int position) {
        return transactions.get(position);
    }

    @Override
    public long getItemId(int position) {
        return transactions.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_transaction, parent, false);
        TextView customerName = convertView.findViewById(R.id.customer_name);
        TextView payAmount = convertView.findViewById(R.id.pay_amount);

        Transaction transaction = getItem(position);
        customerName.setText(transaction.getCustomerName());
        payAmount.setText("Total : Rp. " + NumberFormat.getNumberInstance(Locale.GERMAN).format(transaction.getPayAmount()));
        return convertView;
    }
}
