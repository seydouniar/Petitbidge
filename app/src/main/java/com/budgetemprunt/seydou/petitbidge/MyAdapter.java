package com.budgetemprunt.seydou.petitbidge;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class MyAdapter extends ArrayAdapter<Argent> {
    static final int MIN_MAX []={0,100};

    public MyAdapter(Context context, List<Argent> argents) {
        super(context, 0, argents);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View getView(int position, View convertView,ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_pret,parent, false);
        }
        ArgentHolder viewHolder = (ArgentHolder) convertView.getTag();
        if(viewHolder == null){
            viewHolder = new ArgentHolder();
            viewHolder.nom = (TextView) convertView.findViewById(R.id.itm_name);
            viewHolder.montant=(TextView)convertView.findViewById(R.id.itm_montant);
            viewHolder.date = (TextView)convertView.findViewById(R.id.itm_date);
            viewHolder.progress = (ProgressBar)convertView.findViewById(R.id.progress);
            convertView.setTag(viewHolder);
        }

        Argent argent = getItem(position);
        viewHolder.nom.setText(argent.getNom());
        viewHolder.montant.setText(argent.getMontant().toString());
        viewHolder.date.setText(argent.getDate());

        if(argent.getMontant()>100){
            viewHolder.progress.setProgress(100);
            viewHolder.progress.setProgressTintList(ColorStateList.valueOf(Color.RED));

        }else if(argent.getMontant()>50&&argent.getMontant()<100) {
            double a =argent.getMontant();
            viewHolder.progress.setProgress((int)a);
            viewHolder.progress.setProgressTintList(ColorStateList.valueOf(Color.YELLOW));

        }else {
            double a =argent.getMontant();
            viewHolder.progress.setProgress((int)a);
            viewHolder.progress.setProgressTintList(ColorStateList.valueOf(Color.GREEN));

        }
        return convertView;
    }
    private class ArgentHolder {
        public TextView nom;
        public TextView montant;
        public TextView date;
        public int id;
        public ProgressBar progress;
    }
}
