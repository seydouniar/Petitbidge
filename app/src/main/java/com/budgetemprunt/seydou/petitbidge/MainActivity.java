package com.budgetemprunt.seydou.petitbidge;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginDialog.SendCall,SubscribDialog.UserData,
        NavigationView.OnNavigationItemSelectedListener{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;


    LocalBroadcastManager localBroadcastManager;
    private DrawerLayout mDrawerLayout;

    //Fragments
    private Pret pret;
    private Compte compte;
    private Credit credit;
    private Historiques historiques;

    private TextView profil;
    static final String LOGIN = "login";
    static final String PASS = "pass";
    static final String ID = "id";
    private  ArgentBD argentBD;

    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session= new SessionManager(getApplicationContext());


        argentBD = new ArgentBD(getApplicationContext());
        argentBD.open();
        localBroadcastManager = LocalBroadcastManager.getInstance(this);

        LoginDialog dialog = new LoginDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"show");




        mDrawerLayout = findViewById(R.id.drawer_view);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(viewPager);
        viewPager.getAdapter().notifyDataSetChanged();
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        // Set up the ViewPager with the sections adapter.
        setNavigationViewListner();

    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        pret = new Pret();
        credit = new Credit();
        compte = new Compte();
        historiques = new Historiques();
        adapter.addFragment(compte,"Compte");
        adapter.addFragment(pret, "Prêt");
        adapter.addFragment(credit, "Credit");
        adapter.addFragment(historiques,"Historiques");
       


        viewPager.setAdapter(adapter);
    }

    @Override
    public void sendInfo(int id,String login, String pass,boolean connected) {

        if (login.equals("admin@admin.com")&&pass.equals("admin1")){
            SubscribDialog dialog = new SubscribDialog();
            dialog.setCancelable(false);
            dialog.show(getSupportFragmentManager(),"useradd");
        }

        session.createLoginSession(login,id);
        session.checkLogin();
        HashMap<String,String> user = session.getUserDetails();
        login = user.get(SessionManager.KEY_LOGIN);
        String strId = user.get(SessionManager.KEY_ID);

        profil = (TextView)findViewById(R.id.profil);
        profil.setText(login+strId);



    }

    @Override
    public void sendUserData(String mail, String pass) {
        argentBD.insertUser(new User(mail,pass));
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.action_settings: {
                //do somthing
                break;
            }
            case R.id.deconnect:{
                session.logoutUser();break;
            }
            case R.id.compte_user:
                break;
            case R.id.aide:
                break;
            case R.id.apropos:
                break;
        }
        //close navigation drawer
        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
    public boolean onOptionsItemSelected (MenuItem item)
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    private void setNavigationViewListner() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


}
