package com.alle.san.sendia;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Constants;


public class LogInFragment extends Fragment {

    User user = new User();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!= null){
            user = bundle.getParcelable(Constants.INTENT_USER);
            Toast.makeText(getActivity(),  user.getName() + "'s Profile", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_log_in, container, false);
        return view;
    }
}