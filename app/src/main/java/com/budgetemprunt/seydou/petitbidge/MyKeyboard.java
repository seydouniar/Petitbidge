package com.budgetemprunt.seydou.petitbidge;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;

import java.time.Instant;
import java.util.ArrayList;

public class MyKeyboard extends DialogFragment {
    GridView gridView;

    KeyValue mlistener;
    interface KeyValue{
        void SendKeyValue(String keyValue);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mlistener = (KeyValue)getTargetFragment();
        }catch (ClassCastException e){
            throw new ClassCastException(getTargetFragment().toString()+" must implement KeyValue");
        }

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        final ArrayList<MyKeyValue> gridArray;
        final KeyboardAdapter customGridAdapter;


        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.keyboard_secure,null,false);
        gridView  = view.findViewById(R.id.gridView1);
        gridArray = getGridArray();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(view);
        Log.i("taille", " "+gridArray.size());

        customGridAdapter = new KeyboardAdapter(getActivity(), gridArray);
        gridView.setAdapter(customGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mlistener.SendKeyValue(gridArray.get(position).getStrValue());
                Log.i("key"," : "+gridArray.get(position).getStrValue());
            }
        });
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        WindowManager.LayoutParams wlp = window.getAttributes();
        wlp.gravity = Gravity.BOTTOM;
        wlp.flags &= ~WindowManager.LayoutParams.FLAG_DIM_BEHIND;
        window.setAttributes(wlp);
        return dialog;

    }

    public ArrayList<MyKeyValue> getGridArray() {
        final MyRandomArray myRandomArray = new MyRandomArray();
        final ArrayList<MyKeyValue> data = new ArrayList<>();
        final ArrayList<Integer> list = myRandomArray.generates();
        for (int i:list){
            data.add(new MyKeyValue(i));

        }
        data.add(new MyKeyValue("x"));
        data.add(new MyKeyValue("C"));
        return data;

    }
}
