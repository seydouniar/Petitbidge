package com.budgetemprunt.seydou.petitbidge;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginDialog.SendCall,SubscribDialog.UserData{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    boolean isConnected = false;

    //Fragments
    private Pret pret;
    private Compte compte;
    private Credit credit;

    private TextView profil;
    static final String LOGIN = "login";
    static final String PASS = "pass";
    static final String ID = "id";
    private  ArgentBD argentBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        argentBD = new ArgentBD(getApplicationContext());
        argentBD.open();

        LoginDialog dialog = new LoginDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"show");



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(viewPager);
        viewPager.getAdapter().notifyDataSetChanged();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        profil = (TextView)findViewById(R.id.profil);
        // Set up the ViewPager with the sections adapter.


    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pret = new Pret();
        credit = new Credit();
        compte = new Compte();

        adapter.addFragment(compte,"Compte");
        adapter.addFragment(pret, "Prêt");
        adapter.addFragment(credit, "Credit");
       


        viewPager.setAdapter(adapter);
    }

    @Override
    public void sendInfo(int id,String login, String pass,boolean connected) {

        if (login.equals("admin@admin.com")&&pass.equals("admin1")){
            SubscribDialog dialog = new SubscribDialog();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(),"useradd");
        }
        isConnected = connected;
        Intent intent = new Intent("com.budgetemprunt.seydou.petitbidge.SOME_ACTION");
        intent.putExtra(LOGIN,login);
        intent.putExtra(ID,id);

        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);

        profil.setText(login);

    }

    @Override
    public void sendUserData(String mail, String pass) {
        argentBD.insertUser(new User(mail,pass));
    }


    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

            super.onCreateOptionsMenu(menu);
            MenuInflater inflater = getMenuInflater();
            //R.menu.menu est l'id de notre menu
            inflater.inflate(R.menu.menu_main, menu);
            return true;
        }
    @Override
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                
            case R.id.deconnect:
                Intent intent = new Intent(MainActivity.this,MainActivity.class);
                startActivity(intent);
                finish();

                return true;

        }
        return super.onOptionsItemSelected(item);
    }

}
