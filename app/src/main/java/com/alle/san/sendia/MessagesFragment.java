package com.alle.san.sendia;

import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.alle.san.sendia.adapters.MessagesDisplayAdapter;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Messages;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.alle.san.sendia.utils.Users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import static com.alle.san.sendia.utils.Globals.random;

public class MessagesFragment extends Fragment {

    RecyclerView rvMessages;
    TextView noMessages;
    SearchView messageSearch;
    SwipeRefreshLayout mSwipeRefreshLayout;

    MessagesDisplayAdapter messagesDisplayAdapter;
    ArrayList<User> users = new ArrayList<>();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_messages, container, false);
        rvMessages = view.findViewById(R.id.rv_messages);
        noMessages = view.findViewById(R.id.no_messages);
        messageSearch= view.findViewById(R.id.action_search);
        mSwipeRefreshLayout = view.findViewById(R.id.swipe_refresh);

        getConnections();
        initMessagesRV();
        refreshRecyclerView();
        searching();

        return view;
    }

    private void searching() {
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        messageSearch.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
        messageSearch.setMaxWidth(Integer.MAX_VALUE);

        messageSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                messagesDisplayAdapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                messagesDisplayAdapter.getFilter().filter(query);
                return true;
            }
        });

    }

    private void refreshRecyclerView() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getConnections();
                messagesDisplayAdapter.notifyDataSetChanged();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
    }
    private void getConnections(){
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.CONNECTIONS, new HashSet<String>());
        Users people = new Users();

        for(User user: people.USERS){
            assert savedNames != null;
            if(savedNames.contains(user.getName())){
                users.add(user);
            }
        }

    }

    private void initMessagesRV() {
        if (users == null){
            rvMessages.setVisibility(View.INVISIBLE);
            noMessages.setVisibility(View.VISIBLE);

        }else{
            noMessages.setVisibility(View.INVISIBLE);
            rvMessages.setVisibility(View.VISIBLE);
            messagesDisplayAdapter = new MessagesDisplayAdapter(users, getContext());
            rvMessages.setAdapter(messagesDisplayAdapter);
            rvMessages.setLayoutManager(new LinearLayoutManager(getContext(),
                                            LinearLayoutManager.VERTICAL, false));
        }
    }
}