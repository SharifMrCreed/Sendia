package com.alle.san.sendia;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;

import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Constants;
import com.alle.san.sendia.utils.PreferenceKeys;

public class MainActivity extends AppCompatActivity implements UserRvClicks{
    private static final String TAG = "MainScreen";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, "onCreate: the activity is started");

        loginDialog();
        init();
        }

    private void init() {
        Log.d(TAG, "init: adding home fragment to container");
        HomeFragment homeFragment = new HomeFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, homeFragment, getString(R.string.Home_fragment));
        transaction.addToBackStack(getString(R.string.Home_fragment));
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
        LogInFragment logInFragment = new LogInFragment();

        Bundle args = new Bundle();
        args.putParcelable(Constants.INTENT_USER, user);
        logInFragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.main_content_container, logInFragment, getString(R.string.Login_fragment));
        transaction.addToBackStack(getString(R.string.Login_fragment));
        transaction.commit();

    }
}