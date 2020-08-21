package com.alle.san.sendia.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alle.san.sendia.R;
import com.alle.san.sendia.models.Message;
import com.alle.san.sendia.models.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

import static com.alle.san.sendia.utils.Messages.MESSAGES;

public class MessagesDisplayAdapter extends RecyclerView.Adapter<MessagesDisplayAdapter.MessagesViewHolder>
                                    implements Filterable {

    Context context;
    ArrayList<User> connections = new ArrayList<>();
    ArrayList<User> mFilteredUsers;
    UserRvClicks clicks;

    public MessagesDisplayAdapter(ArrayList<User> connections, Context context) {
        this.connections = connections;
        this.context = context;
        mFilteredUsers = connections;
    }

    @NonNull
    @Override
    public MessagesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_messages_item, parent, false);
        return new MessagesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessagesViewHolder holder, final int position) {
        holder.Bind(position);
        holder.enterMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks.whenMessageIsClicked(new Message(mFilteredUsers.get(position), MESSAGES[position]));
            }
        });
        holder.dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clicks.whenUserIsClicked(mFilteredUsers.get(position));

            }
        });
    }

    @Override
    public int getItemCount() {
        return mFilteredUsers.size();
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        clicks = (UserRvClicks) context;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    mFilteredUsers = connections;

                } else {

                    ArrayList<User> filteredList = new ArrayList<>();
                    for (User user : connections) {
                        if (user.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(user);
                        }
                    }
                    mFilteredUsers = filteredList;

                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilteredUsers;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilteredUsers = (ArrayList<User>) filterResults.values;
                notifyDataSetChanged();
            }


        };

    }



    public class MessagesViewHolder extends RecyclerView.ViewHolder{

        TextView name, messageSent, reply, noMessages;
        ImageView dp;
        RelativeLayout enterMessage;

        public MessagesViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            messageSent = itemView.findViewById(R.id.message);
            reply = itemView.findViewById(R.id.reply);
            dp = itemView.findViewById(R.id.image);
            enterMessage = itemView.findViewById(R.id.enter_message);
            noMessages = itemView.findViewById(R.id.nothing);
        }

        public void Bind(int position){

            if (mFilteredUsers.isEmpty()){

                noMessages.setVisibility(View.VISIBLE);
                enterMessage.setVisibility(View.INVISIBLE);
                dp.setVisibility(View.INVISIBLE);

            }else{

                noMessages.setVisibility(View.INVISIBLE);
                enterMessage.setVisibility(View.VISIBLE);
                dp.setVisibility(View.VISIBLE);
                User user = mFilteredUsers.get(position);
                name.setText(user.getName());
                messageSent.setText(MESSAGES[position]);
                reply.setText(MESSAGES[position + 1]);
                RequestOptions requestOptions = new RequestOptions().placeholder(R.drawable.allecon);
                Glide.with(context)
                        .load(user.getProfile_image())
                        .apply(requestOptions)
                        .into(dp);
            }



        }
    }

}
