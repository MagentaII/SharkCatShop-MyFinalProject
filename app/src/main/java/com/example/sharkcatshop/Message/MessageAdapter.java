package com.example.sharkcatshop.Message;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sharkcatshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageViewHolder> {

//    public static final int MSG_TYPE_LEFT = 0;
//    public static final int MSG_TYPE_RIGHT = 1;

    public int msg_type;

    private Context context;
    private List<Message> allMessageList = new ArrayList<>();
    private DatabaseReference reference;

    public MessageAdapter(Context context) {
        this.context = context;
    }

    public void setAllMessageList(List<Message> allMessageList) {
        this.allMessageList = allMessageList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(viewType == 1){
            View view = layoutInflater.inflate(R.layout.cell_chat_right, parent, false);
            return new MessageViewHolder(view);
        }else{
            View view = layoutInflater.inflate(R.layout.cell_chat_left, parent, false);
            return new MessageViewHolder(view);
        }


//        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = allMessageList.get(position);
        holder.tvMessage.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return allMessageList.size();
    }

    static class MessageViewHolder extends RecyclerView.ViewHolder {

        public TextView tvMessage;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);

            tvMessage = itemView.findViewById(R.id.tv_message);

        }
    }

    @Override
    public int getItemViewType(int position) {
        reference = FirebaseDatabase.getInstance().getReference();
        reference.child("users").child("furkid").child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(allMessageList.get(position).getSender().equals(snapshot.getValue().toString())){
                    msg_type = 1;
                }else{
                    msg_type = 0;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return msg_type;
    }
}
