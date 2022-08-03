package com.example.expandablelv.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.example.expandablelv.Model.Payment;
import com.example.expandablelv.R;

import java.util.List;

public class PaymentAdapter extends ArrayAdapter<Payment> {
    public PaymentAdapter(@NonNull Context context, int resource, @NonNull List<Payment> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_drop_down, parent,false);
        TextView selected = convertView.findViewById(R.id.selectedTxt);

        Payment payment = this.getItem(position);
        if (payment != null){
            selected.setText(payment.getPayment());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drop_down, parent,false);
        TextView dropDown = convertView.findViewById(R.id.dropDown);

        Payment payment = this.getItem(position);
        if (payment != null){
            dropDown.setText(payment.getPayment());
        }
        return convertView;
    }
}