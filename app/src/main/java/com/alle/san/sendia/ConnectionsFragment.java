package com.alle.san.sendia;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alle.san.sendia.adapters.UserDisplayAdapter;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Globals;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.alle.san.sendia.utils.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class ConnectionsFragment extends Fragment {

    RecyclerView rvSavedConnections;
    TextView noConnections;
    ArrayList<User> connections = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_connections, container, false);

       rvSavedConnections = view.findViewById(R.id.rv_saved_connections);
       noConnections= view.findViewById(R.id.tvNoConnections);

        connectionsRVData();

       return view;
    }

    private void connectionsRVData() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.CONNECTIONS, new HashSet<String>());

        Users users = new Users();
        if (connections != null){ connections.clear(); }
        for(User user: users.USERS){
            assert savedNames != null;
            if (savedNames.contains(user.getName()))
            { connections.add(user); }
        }
        if (connections == null){
            rvSavedConnections.setVisibility(View.INVISIBLE);
            noConnections.setVisibility(View.VISIBLE);
        }else{

            noConnections.setVisibility(View.INVISIBLE);
            rvSavedConnections.setLayoutManager(new GridLayoutManager(getActivity(), Globals.COLUMNS));
            rvSavedConnections.setAdapter(new UserDisplayAdapter(connections, getActivity()));
        }

    }

}