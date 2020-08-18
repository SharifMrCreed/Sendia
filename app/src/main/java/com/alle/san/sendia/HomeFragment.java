package com.alle.san.sendia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alle.san.sendia.adapters.UserDisplayAdapter;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    RecyclerView usersRecyclerView;
    ArrayList<User> matches = new ArrayList<>();
    private static final int COLUMNS = 2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        usersRecyclerView = view.findViewById(R.id.rv_home_fragment);

        data();
        return view;
    }

    private void data() {
        Users users = new Users();
        if (matches != null){
            matches.clear();
            matches.addAll(Arrays.asList(users.USERS));
        }
        usersRecyclerView.setAdapter(new UserDisplayAdapter(matches, getActivity()));
        usersRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(COLUMNS, LinearLayoutManager.VERTICAL));
    }
}