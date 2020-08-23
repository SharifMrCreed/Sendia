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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.alle.san.sendia.adapters.UserRvClicks;
import com.alle.san.sendia.models.FragmentTags;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Globals;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

import static com.alle.san.sendia.utils.Globals.CHAT;
import static com.alle.san.sendia.utils.Globals.CHATS;
import static com.alle.san.sendia.utils.Globals.CONNECTIONS;
import static com.alle.san.sendia.utils.Globals.HOME;
import static com.alle.san.sendia.utils.Globals.INTENT_MESSAGE;
import static com.alle.san.sendia.utils.Globals.MY_PROFILE;
import static com.alle.san.sendia.utils.Globals.PROFILE;
import static com.alle.san.sendia.utils.Globals.SETTINGS;

public class MainActivity extends AppCompatActivity implements UserRvClicks {
    private static final String TAG = "MainScreen";
    BottomNavigationViewEx viewEx;
    NavigationView nDrawer;
    DrawerLayout nDrawerLayout;
    ImageView drawerImage;

    private HomeFragment homeFragment;
    private ConnectionsFragment connectionsFragment;
    private MessagesFragment messagesFragment;
    private MyProfileFragment myProfileFragment;
    private SettingsFragment settingsFragment;
    private UserProfileFragment userProfileFragment;
    private ChatsFragment chatsFragment;

    private int  container;
    private int exitCount = 0;
    private ArrayList<String> fragmentTags = new ArrayList<>();
    private ArrayList<FragmentTags> fragmentTagObjects = new ArrayList<>();


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

    private void fragmentVisibilities(String tagName) {
        for (int i = 0; i<fragmentTagObjects.size(); i++){
            if (tagName.equals(fragmentTagObjects.get(i).getTheTag())){
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.show(fragmentTagObjects.get(i).getFragment());
                transaction.commit();
            }
            else{
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                transaction.hide(fragmentTagObjects.get(i).getFragment());
                transaction.commit();
            }
            selectIcon(tagName);
        }
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
                    case (R.id.action_home):
                        fragmentTags.clear();
                        fragmentTags = new ArrayList<>();
                        initHomeFragment();
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        item.setChecked(true);
                        return true;
                    case (R.id.action_profile):
                        initMyProfileFragment();
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        item.setChecked(true);
                        return true;
                    case(R.id.action_settings):
                        initSettings();
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        item.setChecked(true);
                        return true;
                    case (R.id.action_share):
                        nDrawerLayout.closeDrawer(GravityCompat.START);
                        item.setChecked(true);
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


        initUserProfileFragment();
        Bundle args = new Bundle();
        args.putParcelable(Globals.INTENT_USER, user);
        userProfileFragment.setArguments(args);


    }

    @Override
    public void whenMessageIsClicked(Message message) {
        initChatsFragment();
        Bundle args = new Bundle();
        args.putParcelable(INTENT_MESSAGE, message);
        chatsFragment.setArguments(args);


    }

    @Override
    public void onBackPressed() {
        int fragmentCount = fragmentTags.size();
        if (fragmentCount == 1){
            homeFragment.scrollToTop();
            exitCount++;
            if (exitCount == 2){
                super.onBackPressed();
            }else{
                Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show();
            }

        } else if (fragmentCount == 2 && fragmentTags.get(1).equals(HOME)){
            exitCount++;
            homeFragment.scrollToTop();
            Toast.makeText(this, "click again to exit", Toast.LENGTH_SHORT).show();
            if (exitCount == 2){
                super.onBackPressed();
            }
        }else{
            exitCount = 0;
            fragmentVisibilities(fragmentTags.get(fragmentCount - 2));
            fragmentTags.remove(fragmentCount - 1);
        }
    }

    private void bottomNavigation() {
        viewEx.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch(item.getItemId()){
                    case (R.id.action_home):
                        fragmentTags.clear();
                        fragmentTags = new ArrayList<>();
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

    private void selectIcon(String tag){
        Menu menu = viewEx.getMenu();
        MenuItem item = null;
        switch (tag) {
            case HOME:
                viewEx.setVisibility(View.VISIBLE);
                item = menu.getItem(0);
                item.setChecked(true);
                break;
            case CONNECTIONS:
                item = menu.getItem(1);
                item.setChecked(true);
                viewEx.setVisibility(View.VISIBLE);
                break;
            case CHATS:
                item = menu.getItem(2);
                item.setChecked(true);
                viewEx.setVisibility(View.VISIBLE);
                break;
            case PROFILE:
            case MY_PROFILE:
            case SETTINGS:
            case CHAT:
                viewEx.setVisibility(View.GONE);
                break;
        }

    }

    public void initUserProfileFragment(){
        if (userProfileFragment != null){
            getSupportFragmentManager().beginTransaction().remove(userProfileFragment).commitAllowingStateLoss();
        }
        userProfileFragment = new UserProfileFragment();
        fragmentTags.add(PROFILE);
        fragmentTagObjects.add(new FragmentTags(userProfileFragment, PROFILE));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, userProfileFragment, PROFILE);
        fragmentVisibilities(PROFILE);
        transaction.commit();

    }

    public void initChatsFragment(){
        if (chatsFragment != null){
            getSupportFragmentManager().beginTransaction().remove(chatsFragment).commitAllowingStateLoss();
        }
        chatsFragment = new ChatsFragment();
        fragmentTags.add(CHAT);
        fragmentTagObjects.add(new FragmentTags(chatsFragment, CHAT));
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(container, chatsFragment, CHAT);
        fragmentVisibilities(CHAT);
        transaction.commit();


    }

    private void initHomeFragment() {
        Log.d(TAG, "init: adding home fragment to container");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (homeFragment == null){
            homeFragment = new HomeFragment();
            fragmentTags.add(HOME);
            fragmentTagObjects.add(new FragmentTags(homeFragment, HOME));
            transaction.add(container, homeFragment, HOME);
        }else{
            fragmentTags.remove(HOME);
            fragmentTags.add(HOME);
        }
        transaction.commit();
        fragmentVisibilities(HOME);

    }

    private void initConnectionsFragment() {
        Log.d(TAG, "init: adding connections fragment to container");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (connectionsFragment == null){
            connectionsFragment = new ConnectionsFragment();
            fragmentTags.add(CONNECTIONS);
            fragmentTagObjects.add(new FragmentTags(connectionsFragment, CONNECTIONS));
            transaction.add(container, connectionsFragment, CONNECTIONS);
        }else{
            fragmentTags.remove(CONNECTIONS);
            fragmentTags.add(CONNECTIONS);
            transaction.show(connectionsFragment);
        }
        transaction.commit();

        fragmentVisibilities(CONNECTIONS);

    }

    private void initMessagesFragment() {
        Log.d(TAG, "init: adding Messages fragment to container");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (messagesFragment == null){
            messagesFragment = new MessagesFragment();
            fragmentTags.add(CHATS);
            fragmentTagObjects.add(new FragmentTags(messagesFragment, CHATS));
            transaction.add(container, messagesFragment, CHATS);
        }else{
            fragmentTags.remove(CHATS);
            fragmentTags.add(CHATS);
            transaction.show(messagesFragment);
        }
        transaction.commit();
        fragmentVisibilities(CHATS);
    }

    private void initMyProfileFragment() {
        Log.d(TAG, "init: adding My profile fragment to container");

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (myProfileFragment == null){
            myProfileFragment = new MyProfileFragment();
            fragmentTags.add(MY_PROFILE);
            fragmentTagObjects.add(new FragmentTags(myProfileFragment, MY_PROFILE));
            transaction.add(container, myProfileFragment, MY_PROFILE);
        }else{
            fragmentTags.remove(MY_PROFILE);
            fragmentTags.add(MY_PROFILE);
            transaction.show(myProfileFragment);
        }
        transaction.commit();
        fragmentVisibilities(MY_PROFILE);


    }

    private void initSettings() {

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (settingsFragment == null){
            settingsFragment = new SettingsFragment();
            fragmentTags.add(SETTINGS);
            fragmentTagObjects.add(new FragmentTags(settingsFragment, SETTINGS));
            transaction.add(container, settingsFragment, SETTINGS);

        }else{
            fragmentTags.remove(SETTINGS);
            fragmentTags.add(SETTINGS);
            transaction.show(settingsFragment);
        }
        transaction.commit();
        fragmentVisibilities(SETTINGS);

    }


}