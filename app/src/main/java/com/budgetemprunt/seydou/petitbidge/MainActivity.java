package com.budgetemprunt.seydou.petitbidge;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoginDialog.SendCall{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private TextView profil;
    static final String LOGIN = "login";
    static final String PASS = "pass";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoginDialog dialog = new LoginDialog();
        dialog.setCancelable(false);
        dialog.show(getSupportFragmentManager(),"show");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.container);

        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        profil = (TextView)findViewById(R.id.profil);
        // Set up the ViewPager with the sections adapter.


    }



    private void setupViewPager(ViewPager viewPager) {
        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Credit(), "Credit");
        adapter.addFragment(new Pret(), "Prêt");
        adapter.addFragment(new Compte(),"Compte");
        adapter.addFragment(new Compte(),"A propos");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void sendInfo(String login, String pass) {
        profil.setText(login);
        Bundle args = new Bundle();
        args.putString(LOGIN,login);
        args.putString(PASS,pass);
        for (Fragment f:adapter.getmFragmentList()
             ) {
            f.setArguments(args);
        }

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

        public List<Fragment> getmFragmentList() {
            return mFragmentList;
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
