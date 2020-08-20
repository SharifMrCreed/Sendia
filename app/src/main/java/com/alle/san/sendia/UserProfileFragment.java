package com.alle.san.sendia;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alle.san.sendia.models.User;
import com.alle.san.sendia.utils.Constants;
import com.alle.san.sendia.utils.PreferenceKeys;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.like.LikeButton;
import com.like.OnLikeListener;

import java.util.HashSet;
import java.util.Set;


public class UserProfileFragment extends Fragment {

    User user = new User();
    RelativeLayout backArrow;
    ImageView photo;
    TextView gender;
    TextView heading;
    TextView name;
    TextView interestedIn;
    TextView status;
    LikeButton mLikeButton;

    private static final String TAG = "UserProfileFragment";
    private SharedPreferences preferences;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle!= null){
            user = bundle.getParcelable(Constants.INTENT_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_profile, container, false);

        backArrow = view.findViewById(R.id.back_arrow);
        photo = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.name);
        gender = view.findViewById(R.id.gender);
        interestedIn = view.findViewById(R.id.interested_in);
        status = view.findViewById(R.id.status);
        mLikeButton = view.findViewById(R.id.heart_button);
        heading = view.findViewById(R.id.fragment_heading);

        preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        heading.setText(user.getName() + "'s " + getString(R.string.fragment_profile));
        bind();
        connectionCheck();
        liking();
        pressingBack();

        return view;
    }

    private void pressingBack() {
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().onBackPressed();
            }
        });
    }

    private void liking() {
        mLikeButton.setOnLikeListener(new OnLikeListener() {
            @Override
            public void liked(LikeButton likeButton) {
                Log.d(TAG, "liked: liked");
                SharedPreferences.Editor editor = preferences.edit();

                Set<String> savedNames = preferences.getStringSet(PreferenceKeys.CONNECTIONS, new HashSet<String>());
                assert savedNames != null;
                savedNames.add(user.getName());
                editor.putStringSet(PreferenceKeys.CONNECTIONS, savedNames);
                editor.apply();
            }

            @Override
            public void unLiked(LikeButton likeButton) {
                SharedPreferences.Editor editor = preferences.edit();

                Set<String> savedNames = preferences.getStringSet(PreferenceKeys.CONNECTIONS, new HashSet<String>());
                savedNames.remove(user.getName());
                editor.remove(PreferenceKeys.CONNECTIONS);
                editor.apply();
                editor.putStringSet(PreferenceKeys.CONNECTIONS, savedNames);
                editor.commit();

            }
        });
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

    private void connectionCheck(){
        Set<String> savedNames = preferences.getStringSet(PreferenceKeys.CONNECTIONS, new HashSet<String>());
        if (savedNames != null){
            if(savedNames.contains(user.getName())) {
                mLikeButton.setLiked(true);
            }
        }
        else{
            mLikeButton.setLiked(false);
        }
    }


}