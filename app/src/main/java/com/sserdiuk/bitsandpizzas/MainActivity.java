package com.sserdiuk.bitsandpizzas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;
    DrawerLayout drawerLayout;

    private String[] titles;
    private ListView drawerList;

    List<String> titlesList;

    private int orderClickedCount = 0;


    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        titles = getResources().getStringArray(R.array.titles);
        System.out.println("LIST OF TITLES COUNT : " + titles.length);
        drawerList = (ListView) findViewById(R.id.drawer);
//        Get link on drawerLayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        /*
        * switching mode simple_list_item_activated_1 ->
        * means: variant which was clicked by user will be highlighted
        */
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));

//        Add listener for items in left menu
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        /*
        * in first creating MainActivity
        * use method selectItem()
        * for displaying TopFragment
        * */
        if (savedInstanceState == null) {
            selectItem(0);
        }
    }

    /*
    * This method add menu on layout
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
//        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*
        * Realisation for resend for getting information from menu
        *
        * get lin on provider actions sending information
        * and set is as private variable
        * then, call method setIntent
        * */
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);

        /*
        * This text will be send on shareAction intent
        * */
        setShareActionIntent("Want to join me for pizza?");

        return true;
    }

    /**
     * We create method which create Intent and send him to provider action
     * in his method setShareIntent
     * */
    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                System.out.println("action_create_order clicked");
                System.out.println(orderClickedCount++);
                return true;
            case R.id.action_settings:
                System.out.println("action_strings clicked");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Class for item click listener realisation for drawer (left menu)
     * */
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment fragment;
        switch (position) {
            case 1:
                fragment = new PizzaFragment();
                break;
            case 2:
                fragment = new PastaFragment();
                break;
            case 3:
                fragment = new StoresFragment();
                break;
            default:
                fragment = new TopFragment();
        }

//        setActionBarTitle(position);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment);
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
}

    /**
     * method for changing header for menu
     * */
    private void setActionBarTitle(int position) {
        String title;
        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = titles[position];
        }
        getActionBar().setTitle(title);
    }
}