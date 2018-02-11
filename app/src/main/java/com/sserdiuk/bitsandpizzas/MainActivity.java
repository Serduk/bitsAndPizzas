package com.sserdiuk.bitsandpizzas;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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


public class MainActivity extends AppCompatActivity {
    private ShareActionProvider shareActionProvider;
    private DrawerLayout drawerLayout;
    ActionBarDrawerToggle drawerToggle;

    private String[] titles;
    private ListView drawerList;
    private int currentPosition = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        titles = getResources().getStringArray(R.array.titles);
        drawerList = findViewById(R.id.drawer);

        /*
        * switching mode simple_list_item_activated_1 ->
        * means: variant which was clicked by user will be highlighted
        */
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, titles));


//        Add listener for items in left menu
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

        /*
        * display correct text on header
        * we get current position on screen, and if it != null = we change title on screen
        *
        * in first creating MainActivity
        * use method selectItem()
        * for displaying TopFragment
        * */
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt("position");
            setActionBarTitle(currentPosition);
        } else {
            selectItem(0);
        }

//        Creating of ActionBarDrawerToggle
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.string.open_drawer,
                R.string.close_drawer) {
            @Override
            public void onDrawerClosed(View view) {
                System.out.println("Close Drawer");
                super.onDrawerClosed(view);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View view) {
                System.out.println("Open Drawer");
                super.onDrawerOpened(view);
                invalidateOptionsMenu();
            }
        };

//        set ActionBarDrawerToggle as listener for drawerLayout
        drawerLayout.setDrawerListener(drawerToggle);
//        add button "up", she should be usable in by object ActionBarDrawToggle
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        getSupportFragmentManager().addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    //                    call when changing back stack
                    @Override
                    public void onBackStackChanged() {
                        FragmentManager fragmentManager = getSupportFragmentManager();
                        Fragment fragment =
                                fragmentManager.findFragmentByTag("visible_fragment");

                /*
                * Проверить, экземпляром какого класса является фраг- мент, связанный с активностью,
                * и присвоить currentPosition соответствую- щее значение.
                * */
                        if (fragment instanceof TopFragment) {
                            currentPosition = 0;
                        }

                        if (fragment instanceof PizzaFragment) {
                            currentPosition = 1;
                        }

                        if (fragment instanceof PastaFragment) {
                            currentPosition = 2;
                        }

                        if (fragment instanceof StoresFragment) {
                            currentPosition = 3;
                        }

                        setActionBarTitle(currentPosition);
//                Display text on action panel
                        drawerList.setItemChecked(currentPosition, true);

                    }
                });
    }

    private void selectItem(int position) {
        currentPosition = position;
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
        ft.replace(R.id.content_frame, fragment, "visible_fragment");
        ft.addToBackStack(null);
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
        ft.commit();

        setActionBarTitle(position);
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
//        if draw panel is opened -> hide all elements which in content
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerList);
        menu.findItem(R.id.action_share).setVisible(!drawerOpen);

        return super.onPrepareOptionsMenu(menu);
    }

    /**
     * Synchronize state ActionBarDrawerToggle
     * with state of drawable panel
     * */
    @Override
    public void onPostCreate(Bundle onSavedInstance) {
        super.onPostCreate(onSavedInstance);

        drawerToggle.syncState();

    }

    /**
     * For transfer information about any changing in configuration ActionBarDrawerToggle()
     * */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", currentPosition);
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

    /*
    * This method add menu on layout
    * */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
        getMenuInflater().inflate(R.menu.menu_main, menu);

        /*
        * Realisation for resend for getting information from menu
        *
        * get lin on provider actions sending information
        * and set is as private variable
        * then, call method setIntent
        * */
//        MenuItem menuItem = menu.findItem(R.id.action_share);
//        shareActionProvider =
//                (ShareActionProvider) MenuItem.getActionProvider(menu);
//        setIntent("This is example text");

        MenuItem menuItem = menu.findItem(R.id.action_share);
//        shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();


        /*
        * This text will be send on shareAction intent
        * */
        setShareActionIntent("Want to join me for pizza?");

        return super.onCreateOptionsMenu(menu);
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
//        for reactions on taps, we should add this condition
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_create_order:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
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


}