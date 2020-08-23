package com.alle.san.sendia;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class MyProfileFragment extends Fragment {

    ImageView myDP;
    RelativeLayout backArrow;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_profile, container, false);
        myDP = view.findViewById(R.id.my_profile_image);
        backArrow = view.findViewById(R.id.back_arrow);

        initBackArrow();
        Bind();

        return view;
    }

    public void initBackArrow(){
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }
    private void Bind(){
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.allecon);
        Glide.with(getActivity())
                .load(R.drawable.me)
                .apply(requestOptions)
                .into(myDP);
    }
}