package com.example.in_class_09;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ContactAdapter extends ArrayAdapter<contact> {
    public ContactAdapter(@NonNull Context context, int resource, @NonNull List<contact> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        contact contacts = getItem(position);

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView fname =  convertView.findViewById(R.id.fname);
        TextView number =  convertView.findViewById(R.id.number);
        TextView email = convertView.findViewById(R.id.email);
        ImageView imageView = convertView.findViewById(R.id.imageView3);

        fname.setText(contacts.fname);
        number.setText(contacts.phone);
        email.setText(contacts.email);
        Picasso.get().load(contacts.url).into(imageView);
        return convertView;
    }
}
