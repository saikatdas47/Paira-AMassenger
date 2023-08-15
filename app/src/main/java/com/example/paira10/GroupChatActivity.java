package com.example.paira10;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.paira10.Adapters.ChatAdapter;
import com.example.paira10.Models.MessageModel;
import com.example.paira10.databinding.ActivityGroupChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class GroupChatActivity extends AppCompatActivity {
ActivityGroupChatBinding binding;
FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityGroupChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        getSupportActionBar().hide();

        auth = FirebaseAuth.getInstance();
//        final String senderId = auth.getUid();
        String recieveId = getIntent().getStringExtra("userId");
        String username = getIntent().getStringExtra("userName");
        String profilePic = getIntent().getStringExtra("profilePic");
        binding.groupName.setText(username);
        Picasso.get().load(profilePic).placeholder(R.drawable.man).into(binding.groupImage);


        binding.backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GroupChatActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessageModel> messageModels = new ArrayList<>();
        final String senderId = FirebaseAuth.getInstance().getUid();
        binding.groupName.setText("Friends Group");
        final ChatAdapter adapter = new ChatAdapter(messageModels, this);
        binding.chatRecyclarView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager ( this);
        binding.chatRecyclarView.setLayoutManager (layoutManager);

        database.getReference () . child("Group Chat")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        messageModels.clear();
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            MessageModel model = dataSnapshot.getValue(MessageModel.class);
                            messageModels.add(model);
                            adapter.notifyDataSetChanged();
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });





        binding.send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (binding.etMessage.getText () .toString() .isEmpty ()) {
                    binding.etMessage.setError("Type your message");
                    return;
                }

                String message = binding.etMessage.getText().toString();
                final MessageModel model = new MessageModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                binding.etMessage.setText("");

                database. getReference () . child("Group Chat")
                        .push ()
                        .setValue (model) .addOnSuccessListener (new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                            }
                        });


            }
        });

    }
}