package com.alle.san.sendia.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alle.san.sendia.R;
import com.alle.san.sendia.models.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class UserDisplayAdapter extends RecyclerView.Adapter<UserDisplayAdapter.UsersViewHolder> {
    private static final String TAG = "UserDisplayAdapter";

    ArrayList<User> users = new ArrayList<>();
    Context context;

    public UserDisplayAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.rv_users_display, parent, false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        holder.Bind(position);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView profileImage;
        TextView name;
        TextView status;
        TextView interestedIn;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            profileImage = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.name);
            status = itemView.findViewById(R.id.status);
            interestedIn = itemView.findViewById(R.id.interested_in);
        }
        public  void Bind(int position){
            User user = users.get(position);
            RequestOptions requestOptions = new RequestOptions()
                                                .placeholder(R.drawable.ic_person);
            Glide.with(context)
                    .load(user.getProfile_image())
                    .apply(requestOptions)
                    .into(profileImage);
            name.setText(user.getName());
            status.setText(user.getStatus());
            interestedIn.setText(user.getInterested_in());
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Log.d(TAG, "in VH class onClick: user" + users.get(position).getName() + "has been clicked" );
        }
    }
}
