package com.alle.san.sendia;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.alle.san.sendia.adapters.UserRvClicks;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Constants;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

public class MainActivity extends AppCompatActivity implements UserRvClicks {
    private static final String TAG = "MainScreen";
    BottomNavigationViewEx viewEx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: the activity is started");

        viewEx = findViewById(R.id.main_nav_bar);

        loginDialog();
        initHomeFragment();
        bottomNavigation();
        }

    private void bottomNavigation() {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case (R.id.action_home):
                        Toast.makeText(MainActivity.this, "Home", Toast.LENGTH_LONG).show();
                        initHomeFragment();
                        return true;

                    case (R.id.action_connnections):
                        Toast.makeText(MainActivity.this, "Connections", Toast.LENGTH_LONG).show();
                        initConnectionsFragment();
                        return true;

                    case (R.id.action_profile):
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
                        initMyProfileFragment();
                        return true;

                    case (R.id.action_chats):
                        Toast.makeText(MainActivity.this, "Profile", Toast.LENGTH_LONG).show();
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
    public void whenUserClicks(User user) {
        UserProfileFragment userProfileFragment = new UserProfileFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.INTENT_USER, user);
        userProfileFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, userProfileFragment, getString(R.string.Login_fragment));
        transaction.addToBackStack(getString(R.string.Login_fragment));
        transaction.commit();

    }
}