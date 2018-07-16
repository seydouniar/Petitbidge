package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class HistAdapter extends ArrayAdapter<Historique>{

    public HistAdapter(Context context, List<Historique> historiques) {
        super(context, 0,historiques);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_hist,parent, false);
        }
        HistAdapter.HistHolder viewHolder = (HistAdapter.HistHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new HistAdapter.HistHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.ith_name);
            viewHolder.montant=(TextView)convertView.findViewById(R.id.ith_montant);
            viewHolder.date = (TextView)convertView.findViewById(R.id.ith_date);
            viewHolder.action = (TextView) convertView.findViewById(R.id.ith_action);
            convertView.setTag(viewHolder);
        }

        Historique hist = getItem(position);
        viewHolder.nom.setText(hist.getNom());
        viewHolder.montant.setText(String.valueOf(hist.getMontant()));
        viewHolder.date.setText(hist.getDate());
        viewHolder.action.setText(hist.getAction());

        return convertView;
    }
    private class HistHolder {
        public TextView nom;
        public TextView montant;
        public TextView date;
        public TextView action;
    }
}
