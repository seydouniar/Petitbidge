package com.budgetemprunt.seydou.petitbidge;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;



public class Pret extends Fragment implements MyDialog.CallBackDialog{

    ListView listView;
    ProgressBar progressLayout;
    ArgentBD argentBD ;
    TextView textTotale;
    List argents ;
    View v;
    MyAdapter adapter;
    int user_id;
    String login;
    SessionManager session;
    Context _context;


    public Pret() {
        // Required empty public constructor
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        _context = getActivity();
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //bdd
        v=inflater.inflate(R.layout.fragment_pret, container, false);


        session =new SessionManager(getActivity());
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        String login = user.get(SessionManager.KEY_LOGIN);
        String strId = user.get(SessionManager.KEY_ID);

        user_id = Integer.parseInt(strId);
        argentBD = new ArgentBD(getActivity());
        argentBD.open();

        textTotale =(TextView)v.findViewById(R.id.totale);

        progressLayout = (ProgressBar) v.findViewById(R.id.layout_progress);
        listView = (ListView)v.findViewById(R.id.listpret);
        //adapter = new MyAdapter(getActivity(), argents);
        //listView.setAdapter(adapter);

        registerForContextMenu(listView);

        //context menu

        Button ajout = (Button)v.findViewById(R.id.nouveau);

        if(user_id!=0)
            (new MyAsyncTask()).execute();

        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openNewDiallog();

            }
        });
        return v;
    }





    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

        if (v.getId() == R.id.listpret) {
            String [] menuItems = getResources().getStringArray(R.array.menu);
            for (int i=0;i<menuItems.length;i++){
                menu.add(menu.NONE,i,i,menuItems[i]);
            }

        }
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        int pos = item.getItemId();
        String [] menuItems = getResources().getStringArray(R.array.menu);
        String menuTitle = menuItems[pos];
    final Argent argent = (Argent) listView.getItemAtPosition(acmi.position);
        switch (menuTitle) {
            case "Editer":
                // User chose the "Settings" item, show the app settings UI...
                EditDiallog(argent);

                return true;

            case "Payer":
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(getActivity(),"Payer "+argent.getNom(),Toast.LENGTH_SHORT).show();
                return true;

            case "Supprimer":
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Voulez vous vraiment le supprimé")
                        .setNegativeButton("Annulez", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Supprimer", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                argentBD.deleteArgent(argent.getId());
                                (new MyAsyncTask()).execute();
                            }
                        }).setCancelable(false);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }


    public double totaleArgent(ArrayList<Argent> argentsListe){
        double totale =0;
        for (int i = 0;i< argentsListe.size();i++) {
            totale += argentsListe.get(i).getMontant();
        }
        return totale;
    }


    @Override
    public void getValues(String nom,String montant,String date) {
        Argent argent= new Argent(nom,Double.parseDouble(montant),date);
        argentBD.insertArgent(argent,user_id);
        argentBD.insertHist(user_id,"pret");
        (new MyAsyncTask()).execute();

    }

    @Override
    public void editValue(Argent argent,String nom,String montant,String date) {
        argentBD.updateArgent(argent.getId(),new Argent(nom,Double.parseDouble(montant),date));
        argentBD.insertHist(user_id,"Edit prêt");
        (new MyAsyncTask()).execute();
    }

    public void openNewDiallog(){

        MyDialog dialog = new MyDialog(false);
        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(Pret.this,300);
        dialog.setCancelable(false);
        dialog.show(fm,"Prêt");
    }
    public void EditDiallog(Argent argent){
        MyDialog dialog = new MyDialog(argent,true);
        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(Pret.this,300);
        dialog.setCancelable(false);
        dialog.show(fm,"Prêt");
    }


    private class MyAsyncTask extends AsyncTask<Void,Void,List<Argent>>{

        @Override
        protected List<Argent> doInBackground(Void... voids) {
            argents= argentBD.getArgentAll(user_id);
            return argents;
        }


        @Override
        protected void onPostExecute(List<Argent> results) {
            adapter = new MyAdapter(getActivity(), results);
            double val = totaleArgent((ArrayList<Argent>) argents);
            listView.setAdapter(adapter);
            textTotale.setText(val+" €");
            progressLayout.setProgress((int)val);
            listView.setTextFilterEnabled(true);
            adapter.notifyDataSetChanged();

        }
    }

}
