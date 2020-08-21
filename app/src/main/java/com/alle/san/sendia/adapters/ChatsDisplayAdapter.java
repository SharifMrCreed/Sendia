package com.alle.san.sendia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alle.san.sendia.R;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class ChatsDisplayAdapter extends RecyclerView.Adapter<ChatsDisplayAdapter.ChatBubbleViewHolder> {

    Context context;
    ArrayList<String> messages = new ArrayList<>();
    User user;

    public ChatsDisplayAdapter(Context context, ArrayList<String> messages, User user) {
        this.context = context;
        this.messages = messages;
        this.user = user;
    }

    @NonNull
    @Override
    public ChatBubbleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rv_chat_item, parent, false);
        return new ChatBubbleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatBubbleViewHolder holder, int position) {
        holder.Bind(position);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class ChatBubbleViewHolder extends RecyclerView.ViewHolder{

        ImageView dp;
        TextView message;

        public ChatBubbleViewHolder(@NonNull View itemView) {
            super(itemView);
            dp= itemView.findViewById(R.id.profile_image);
            message = itemView.findViewById(R.id.message);
        }
        public void Bind(int position){
            RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.allecon);
            Glide.with(context)
                    .load(user.getProfile_image())
                    .apply(requestOptions)
                    .into(dp);
            message.setText(messages.get(position));
        }
    }
}
