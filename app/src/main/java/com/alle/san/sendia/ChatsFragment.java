package com.alle.san.sendia;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alle.san.sendia.adapters.ChatsDisplayAdapter;
import com.alle.san.sendia.adapters.UserRvClicks;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Globals;
import com.bumptech.glide.Glide;

import java.util.ArrayList;


public class ChatsFragment extends Fragment {

    RelativeLayout backArrow, upperToolBar;
    ImageView dp;
    TextView name;
    EditText etSendMessage;
    Button send;
    RecyclerView rvChats;

    private Message message;
    private User user;
    private UserRvClicks clicks;
    ArrayList<String> usersMessages = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            message = bundle.getParcelable(Globals.INTENT_MESSAGE);
            user = message.getUser();
            String textM = message.getMessage();
            usersMessages.add(textM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_chats, container, false);

       backArrow = view.findViewById(R.id.back_arrow);
       dp = view.findViewById(R.id.profile_image);
       name = view.findViewById(R.id.fragment_heading);
       etSendMessage= view.findViewById(R.id.input_message);
       send = view.findViewById(R.id.post_message);
       rvChats =view.findViewById(R.id.rv_chats);
       upperToolBar = view.findViewById(R.id.relLayoutTop);

       initUserData();
       initRecyclerView();
       pressingBackArrow();
       pressingUpperToolBar();

       return view;
    }

    private void initRecyclerView() {
        rvChats.setLayoutManager(new LinearLayoutManager(getActivity(), RecyclerView.VERTICAL, false));
        rvChats.setAdapter(new ChatsDisplayAdapter(getActivity(), usersMessages, user));
    }

    private void pressingUpperToolBar() {
        upperToolBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks.whenUserIsClicked(user);
            }
        });
    }

    private void pressingBackArrow() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void initUserData() {
        Glide.with(getActivity())
                .load(user.getProfile_image())
                .into(dp);
        name.setText(user.getName());

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        clicks = (UserRvClicks) getActivity();
    }
}