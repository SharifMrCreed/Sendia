package com.alle.san.sendia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alle.san.sendia.adapters.UserDisplayAdapter;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Globals;
import com.alle.san.sendia.utils.Users;

import java.util.ArrayList;
import java.util.Arrays;

public class HomeFragment extends Fragment {

    RecyclerView usersRecyclerView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    ArrayList<User> matches = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        usersRecyclerView = view.findViewById(R.id.rv_home_fragment);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        data();
        refreshRecyclerView();
        return view;
    }

    private void refreshRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data();
                usersRecyclerView.getAdapter().notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    private void data() {
        Users users = new Users();
        if (matches != null){
            matches.clear();
            matches.addAll(Arrays.asList(users.USERS));
        }
        usersRecyclerView.setAdapter(new UserDisplayAdapter(matches, getActivity()));
        usersRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), Globals.COLUMNS));
    }

    public void scrollToTop(){
        usersRecyclerView.smoothScrollToPosition(0);
    }
}