package com.budgetemprunt.seydou.petitbidge;

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

import java.util.List;


public class Pret extends Fragment implements MyDialog.CallBackDialog{

    ListView listView;
    ArgentBD argentBD ;
    List argents ;
    View v;
    boolean editOption = false;

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

        argents= argentBD.getArgentAll();

        // Inflate the layout for this fragment


        listView = (ListView)v.findViewById(R.id.listpret);





        MyAdapter adapter = new MyAdapter(getActivity(), argents);
        listView.setAdapter(adapter);

        registerForContextMenu(listView);
        //context menu

        Button ajout = (Button)v.findViewById(R.id.nouveau);
        ajout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editOption = false;
                openNewDiallog();

            }
        });
        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        argentBD.close();
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
        Argent argent = (Argent) listView.getItemAtPosition(acmi.position);
        switch (menuTitle) {
            case "Editer":
                // User chose the "Settings" item, show the app settings UI...
                editOption = true;
                EditDiallog(argent);

                return true;

            case "Payer":
                // User chose the "Favorite" action, mark the current item
                // as a favorite...
                Toast.makeText(getActivity(),"Payer "+argent.getNom(),Toast.LENGTH_SHORT).show();
                return true;

            case "Supprimer":
                argentBD.deleteArgent(argent.getId());
                argents=argentBD.getArgentAll();

                ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
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
        argents = argentBD.getArgentAll();
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void editValue(Argent argent,String nom,String montant,String date) {
        argentBD.updateArgent(argent.getId(),new Argent(nom,Double.parseDouble(montant),date));
        argents = argentBD.getArgentAll();
        ((BaseAdapter)listView.getAdapter()).notifyDataSetChanged();
    }

    public void openNewDiallog(){

        MyDialog dialog = new MyDialog(false);
        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(Pret.this,300);

        dialog.show(fm,"Prêt");
    }
    public void EditDiallog(Argent argent){
        MyDialog dialog = new MyDialog(argent,true);
        FragmentManager fm = getFragmentManager();
        dialog.setTargetFragment(Pret.this,300);

        dialog.getEdtNom().setText(argent.getNom());
        dialog.getEdtMontant().setText(argent.getMontant().toString());
        dialog.getEdtDate().setText(argent.getDate());
        dialog.show(fm,"Prêt");
    }
}
