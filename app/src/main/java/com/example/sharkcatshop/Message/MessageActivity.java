package com.example.sharkcatshop.Message;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.sharkcatshop.Login_And_Register.User;
import com.example.sharkcatshop.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MessageActivity extends AppCompatActivity {

    private ImageButton btnSend;
    private EditText etSend;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private MessageAdapter messageAdapter;
    private List<Message> allMessageList;
    private RecyclerView recyclerView;
    private User user;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initializeVariables();

        intent = getIntent();
        final String userid = intent.getStringExtra("username");
        Toast.makeText(this, userid, Toast.LENGTH_SHORT).show();

        database = FirebaseDatabase.getInstance();
        reference = database.getReference();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                reference.child("users").child("furkid").child("username").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String msg = etSend.getText().toString();
                            String sender = snapshot.getValue().toString();
                            String receiver = "administrator";
                            if(!msg.equals("")){
                                Message message = new Message(sender, receiver, msg);
                                sendMessage(message);
                                Toast.makeText(MessageActivity.this, sender + msg + receiver, Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(MessageActivity.this, "You can't send empty message", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

//                reference. = FirebaseDatabase.getInstance().getReference("users").child(userid);
                reference.child("users").child(userid).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        readMessages(userid);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


            }
        });

    }

    public void initializeVariables(){
        btnSend = findViewById(R.id.btn_send);
        etSend = findViewById(R.id.et_send);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.setAdapter(messageAdapter);
    }

    private void sendMessage(Message message){
        reference.child("Chats").push().setValue(message);
        etSend.setText("");
    }

    private void readMessages(final String userid){
        allMessageList = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allMessageList.clear();
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    Message message = snapshot1.getValue(Message.class);
                    if(message.getSender().equals(userid)){
                        allMessageList.add(message);
                    }
                    messageAdapter = new MessageAdapter(MessageActivity.this);
                    messageAdapter.setAllMessageList(allMessageList);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

//    private void readMessages(final String myid, final String userid){
//        allMessageList = new ArrayList<>();
//
//        reference = FirebaseDatabase.getInstance().getReference("Chats");
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                allMessageList.clear();
//                for(DataSnapshot snapshot1 : snapshot.getChildren()){
//                    Message message = snapshot1.getValue(Message.class);
//                    if(message.getReceiver().equals(myid) && message.getSender().equals(userid) ||
//                            message.getReceiver().equals(userid) && message.getSender().equals(myid)){
//                        allMessageList.add(message);
//                    }
//
//                    messageAdapter = new MessageAdapter(MessageActivity.this);
//                    messageAdapter.setAllMessageList(allMessageList);
//                    recyclerView.setAdapter(messageAdapter);
//
//
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

}