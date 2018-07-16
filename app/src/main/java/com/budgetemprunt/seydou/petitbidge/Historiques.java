package com.budgetemprunt.seydou.petitbidge;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Historiques extends Fragment {

   int user_id;
    List historiques;
    ArgentBD argentBD;
    ListView listHist;
    HistAdapter adapter;
    SessionManager session;

    private static String LOGIN= "login";
    private static String ID = "id";
    public Historiques() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_historiques, container, false);
        listHist = (ListView) view.findViewById(R.id.listhist);
        argentBD= new ArgentBD(getActivity());
        argentBD.open();
//        Log.i("listhist",historiques.size()+"");
        session =new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        String login = user.get(SessionManager.KEY_LOGIN);
        String strId = user.get(SessionManager.KEY_ID);

        user_id = Integer.parseInt(strId);

        historiques= argentBD.getHistoriques(user_id);
        Log.i("taille",historiques.size()+"");
        adapter = new HistAdapter(getActivity(),historiques);

        listHist.setAdapter(adapter);

        adapter.notifyDataSetChanged();

        return view;
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    private class MyAsyncTask extends AsyncTask<Void,Void,List<Historique>> {

        @Override
        protected List<Historique> doInBackground(Void... voids) {


            return historiques;
        }


        @Override
        protected void onPostExecute(List<Historique> results) {


        }
    }

}
