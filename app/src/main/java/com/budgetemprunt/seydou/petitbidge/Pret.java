package com.budgetemprunt.seydou.petitbidge;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class Pret extends Fragment implements MyDialog.CallBackDialog{

    ListView listView;
    ArgentBD argentBD ;
    List argents ;
    View v;
    MyAdapter adapter;

    public Pret() {
        // Required empty public constructor



    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //bdd
        v=inflater.inflate(R.layout.fragment_pret, container, false);
        argentBD = new ArgentBD(getActivity());
        argentBD.open();


        // Inflate the layout for this fragment
        //argents= argentBD.getArgentAll();

        listView = (ListView)v.findViewById(R.id.listpret);



        //adapter = new MyAdapter(getActivity(), argents);
        //listView.setAdapter(adapter);
        (new MyAsyncTask()).execute();
        registerForContextMenu(listView);
        //context menu

        Button ajout = (Button)v.findViewById(R.id.nouveau);
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
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;

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
                Log.i("DANSEDITE",argent.toString());
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




    @Override
    public void getValues(String nom,String montant,String date) {
        Argent argent= new Argent(nom,Double.parseDouble(montant),date);
        argentBD.insertArgent(argent);
        (new MyAsyncTask()).execute();

    }

    @Override
    public void editValue(Argent argent,String nom,String montant,String date) {
        argentBD.updateArgent(argent.getId(),new Argent(nom,Double.parseDouble(montant),date));
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
            argents= argentBD.getArgentAll();
            return argents;
        }


        @Override
        protected void onPostExecute(List<Argent> results) {
            adapter = new MyAdapter(getActivity(), results);
            listView.setAdapter(adapter);
            listView.setTextFilterEnabled(true);
            adapter.notifyDataSetChanged();

        }
    }

}
