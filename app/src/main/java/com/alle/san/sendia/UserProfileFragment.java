package com.alle.san.sendia;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Constants;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class UserProfileFragment extends Fragment {

    User user = new User();
    ImageView backArrow;
    ImageView photo;
    TextView gender;
    TextView name;
    TextView interestedIn;
    TextView status;


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
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        backArrow = view.findViewById(R.id.image_back_arrow);
        photo = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        interestedIn = view.findViewById(R.id.interested_in);
        status = view.findViewById(R.id.status);

        bind();
        return view;
    }

    private void bind() {
        RequestOptions requestOptions = new RequestOptions()
                                            .placeholder(R.drawable.allecon);
        Glide.with(getActivity())
                .load(user.getProfile_image())
                .apply(requestOptions)
                .into(photo);
        name.setText(user.getName());
        gender.setText(user.getGender());
        interestedIn.setText(user.getInterested_in());
        status.setText(user.getStatus());
    }


}