package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;



public class KeyboardAdapter extends ArrayAdapter<MyKeyValue> {


    public KeyboardAdapter(Context context, ArrayList<MyKeyValue> data) {
        super(context, 0, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            row = inflater.inflate(R.layout.key_item, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.key_text);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }


        MyKeyValue item = getItem(position);
        holder.txtTitle.setText(item.getStrValue());

        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
    }

}
