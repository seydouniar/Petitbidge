package com.budgetemprunt.seydou.petitbidge;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.ValueDependentColor;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import at.grabner.circleprogress.BarStartEndLine;
import at.grabner.circleprogress.CircleProgressView;
import at.grabner.circleprogress.ColorUtils;
import at.grabner.circleprogress.Direction;


/**
 * A simple {@link Fragment} subclass.
 */
public class Compte extends Fragment {
    CircleProgressView compteProgress;
    GraphView graph;
    TextView textView;
    public Compte() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_compte, container, false);
        // Inflate the layout for this fragment
        textView = (TextView)view.findViewById(R.id.txt_compte);
        compteProgress = (CircleProgressView) view.findViewById(R.id.compte_progress);
        compteProgress.setBarColor(Color.RED,Color.GREEN,Color.alpha(0));


        compteProgress.setText(1+"");
        compteProgress.setValue(12);
        compteProgress.setDirection(Direction.CW);
        compteProgress.setAlpha(0.6f);
        compteProgress.setAutoTextSize(true);

        //LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mNotificationReceiver,new IntentFilter("KEY"));
        graph = (GraphView)view.findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series1 = new LineGraphSeries<DataPoint>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 20)
        });

        BarGraphSeries<DataPoint> series = new BarGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3),
                new DataPoint(3, 2),
                new DataPoint(4, 6)
        });

        // styling
        series.setValueDependentColor(new ValueDependentColor<DataPoint>() {
            @Override
            public int get(DataPoint data) {
                return Color.rgb((int) data.getX()*255/4, (int) Math.abs(data.getY()*255/6), 100);
            }
        });

        series.setSpacing(50);

// draw values on top
        series.setDrawValuesOnTop(true);
        series.setValuesOnTopColor(Color.RED);
//series.setValuesOnTopSize(50);
        graph.addSeries(series);
        graph.addSeries(series1);
        return view;
    }

    private BroadcastReceiver mNotificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int id = intent.getExtras().getInt("id");
            String login = intent.getExtras().getString("login");
            textView.setText(login+id);

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mNotificationReceiver,
                new IntentFilter("com.budgetemprunt.seydou.petitbidge.SOME_ACTION"));
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mNotificationReceiver);
    }

}
