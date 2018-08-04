package com.budgetemprunt.seydou.petitbidge;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;



public class KeyboardAdapter extends ArrayAdapter<MyKeyValue> {

    Context context;
    int layoutResourceId;
    ArrayList<MyKeyValue> data = new ArrayList<MyKeyValue>();

    public KeyboardAdapter(Context context, int layoutResourceId, ArrayList<MyKeyValue> data) {
        super(context, layoutResourceId, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecordHolder holder = null;

        if (row == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecordHolder();
            holder.txtTitle = (TextView) row.findViewById(R.id.key_text);
            holder.imageItem = (ImageView) row.findViewById(R.id.img_key);
            row.setTag(holder);
        } else {
            holder = (RecordHolder) row.getTag();
        }

        MyKeyValue item = data.get(position);
        holder.txtTitle.setText(item.getStrValue());
        holder.imageItem.setImageBitmap(item.getIcon());
        return row;

    }

    static class RecordHolder {
        TextView txtTitle;
        ImageView imageItem;

    }

}
