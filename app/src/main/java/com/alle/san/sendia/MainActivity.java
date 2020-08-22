package com.alle.san.sendia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.alle.san.sendia.adapters.UserRvClicks;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Globals;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import static com.alle.san.sendia.utils.Globals.CHATS;
import static com.alle.san.sendia.utils.Globals.INTENT_MESSAGE;
import static com.alle.san.sendia.utils.Globals.SETTINGS;

public class MainActivity extends AppCompatActivity implements UserRvClicks {
    private static final String TAG = "MainScreen";
    BottomNavigationViewEx viewEx;
    NavigationView nDrawer;
    DrawerLayout nDrawerLayout;
    ImageView drawerImage;

    private int  container;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: the activity is started");

        viewEx = findViewById(R.id.main_nav_bar);
        container = R.id.main_content_container;
        nDrawer = findViewById(R.id.nav_drawer);
        nDrawerLayout = findViewById(R.id.drawer_layout);

        loginDialog();
        initHomeFragment();
        bottomNavigation();
        initNavigationDrawer();
        setDrawerImage();



        }

    private void setDrawerImage() {
        View headerView = nDrawer.getHeaderView(0);
        drawerImage = headerView.findViewById(R.id.dp);
        Glide.with(this)
                .load(R.drawable.allecon)
                .into(drawerImage);
    }

    private void initNavigationDrawer() {
        nDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case (R.id.action_profile):
                        initMyProfileFragment();
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case(R.id.action_settings):
                        initSettings();
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case (R.id.action_share):
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    default:
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        return false;
                }

            }
        });
    }


    private void loginDialog() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstLogin = preferences.getBoolean(PreferenceKeys.FIRST_TIME_LOGIN, true);

        if(isFirstLogin) {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(this);
            aBuilder.setMessage(getString(R.string.first_time_login))
                    .setIcon(R.drawable.star_on)
                    .setTitle("Welcome ")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Log.d(TAG, "onClick: Dialog is closed");
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putBoolean(PreferenceKeys.FIRST_TIME_LOGIN, false);
                            editor.apply();
                            dialogInterface.dismiss();
                        }
                    });
            AlertDialog alertDialog = aBuilder.create();
            alertDialog.show();
        }
    }

    @Override
    public void whenUserIsClicked(User user) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();

        Bundle args = new Bundle();
        args.putParcelable(Globals.INTENT_USER, user);
        userProfileFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, userProfileFragment, getString(R.string.Login_fragment));
        transaction.addToBackStack(getString(R.string.Login_fragment));
        transaction.commit();

    }

    @Override
    public void whenMessageIsClicked(Message message) {
        Bundle args = new Bundle();
        args.putParcelable(INTENT_MESSAGE, message);

        ChatsFragment chatsFragment = new ChatsFragment();
        chatsFragment.setArguments(args);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, chatsFragment, CHATS);
        transaction.addToBackStack(CHATS);
        transaction.commit();

    }

    private void bottomNavigation() {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case (R.id.action_home):
                        initHomeFragment();
                        return true;

                    case (R.id.action_connnections):
                        initConnectionsFragment();
                        return true;

                    case (R.id.action_chats):
                        initMessagesFragment();
                        return true;

                    default:
                        return false;
                }


            }
        });
    }

    private void initHomeFragment() {
        Log.d(TAG, "init: adding home fragment to container");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, homeFragment, getString(R.string.Home_fragment));
        transaction.addToBackStack(getString(R.string.Home_fragment));
        transaction.commit();

    }

    private void initConnectionsFragment() {
        Log.d(TAG, "init: adding connections fragment to container");
        ConnectionsFragment connectionsFragment = new ConnectionsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, connectionsFragment, getString(R.string.fragment_connections));
        transaction.addToBackStack(getString(R.string.fragment_connections));
        transaction.commit();

    }

    private void initMessagesFragment() {
        Log.d(TAG, "init: adding Messages fragment to container");
        MessagesFragment messagesFragment = new MessagesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, messagesFragment, getString(R.string.chats));
        transaction.addToBackStack(getString(R.string.chats));
        transaction.commit();

    }

    private void initMyProfileFragment() {
        Log.d(TAG, "init: adding My profile fragment to container");
        MyProfileFragment myProfileFragment = new MyProfileFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, myProfileFragment, getString(R.string.fragment_profile));
        transaction.addToBackStack(getString(R.string.fragment_profile));
        transaction.commit();

    }

    private void initSettings() {
        SettingsFragment settingsFragment = new SettingsFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(container, settingsFragment, SETTINGS);
        transaction.addToBackStack(SETTINGS);
        transaction.commit();
    }



}