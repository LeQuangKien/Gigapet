package com.example.expandablelv.Adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.expandablelv.Model.PetName;
import com.example.expandablelv.Model.Payment;
import com.example.expandablelv.R;

import java.util.List;

public class PetnameAdapter extends ArrayAdapter<PetName> {
    public PetnameAdapter(@NonNull Context context, int resource, @NonNull List<PetName> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position,@Nullable View convertView,@NonNull  ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_selected_drop_down, parent,false);
        TextView selected = convertView.findViewById(R.id.selectedTxt);

        PetName petName = this.getItem(position);
        if (petName != null){
            selected.setText(petName.getName());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_drop_down, parent,false);
        TextView dropDown = convertView.findViewById(R.id.dropDown);

        PetName petName = this.getItem(position);
        if (petName != null){
            dropDown.setText(petName.getName());
        }
        return convertView;
    }



}
