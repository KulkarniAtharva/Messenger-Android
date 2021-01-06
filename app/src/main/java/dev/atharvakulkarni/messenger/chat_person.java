package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class chat_person extends AppCompatActivity
{
    EditText message_edittext;
    FrameLayout send;
    String message, chatwith_username;
    TextView chat_with, on_off_status;
    FirebaseFirestore db;
    private String saveCurrentTime, saveCurrentDate;
    private ChatPersonAdapter chatPersonAdapter;
    private RecyclerView userMessagesList;
    private LinearLayoutManager linearLayoutManager;
    private final List<MessagesModel> messagesList = new ArrayList<>();
    private FirebaseAuth mAuth;
   // String messageSenderId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_person);

        message_edittext = findViewById(R.id.editText_message);
        chat_with = findViewById(R.id.chat_with);
        send = findViewById(R.id.send);
        on_off_status = findViewById(R.id.on_off_status);


        mAuth = FirebaseAuth.getInstance();

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        IntializeControllers();

       // messageSenderId = mAuth.getCurrentUser().getUid();

        chatwith_username = getIntent().getExtras().getString("username");

       /* int x = ChatWithGetData.user(username);

        if(x == 1)
        {
            chat_with.setText(ChatWithModel.getName());
        }*/

        DocumentReference docRef = db.collection("users").document(chatwith_username);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
        {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                        String name = document.getString("name");
                        String last_seen = document.getString("last_seen");
                        String on_off_stat = document.getString("on_off_status");
                        String photo = document.getString("photo");
                        String status = document.getString("status");

                        ChatWithModel.setLast_seen(last_seen);
                        ChatWithModel.setOn_off_status(on_off_stat);
                        ChatWithModel.setStatus(status);
                        ChatWithModel.setName(name);
                        ChatWithModel.setPhoto(photo);
                        ChatWithModel.setUsername(chatwith_username);

                        // Toast.makeText(context, ChatWithModel.getUsername(), Toast.LENGTH_SHORT).show();


                        chat_with.setText(name);
                        on_off_status.setText(on_off_stat);


                        Log.d("TAG", "DocumentSnapshot data1111: " + document.getData());
                    }
                    else
                    {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Log.d("TAG", "get failed with ", task.getException());
                }
            }
        });

       // Toast.makeText(this,UserModel.getUsername()+"   "+ chatwith_username, Toast.LENGTH_SHORT).show();

        //ChatWithModel.setUsername(username);

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendMessage();
            }
        });

        DisplayLastSeen();

       db.collection(UserModel.getUsername()).document("person").collection(chatwith_username)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for (QueryDocumentSnapshot document : task.getResult())
                            {
                                // Log.d(TAG, document.getId() + " => " + document.getData());

                                MessagesModel messages = document.toObject(MessagesModel.class);
                                messagesList.add(messages);
                                chatPersonAdapter.notifyDataSetChanged();
                                userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());
                            }
                        }
                        else
                        {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }

    private void IntializeControllers()
    {
        chatPersonAdapter = new ChatPersonAdapter(messagesList,chat_person.this);
        userMessagesList = (RecyclerView) findViewById(R.id.private_messages_list_of_users);
        linearLayoutManager = new LinearLayoutManager(this);
       // linearLayoutManager.setReverseLayout(true);
       // linearLayoutManager.setStackFromEnd(true);
        userMessagesList.setLayoutManager(linearLayoutManager);
        userMessagesList.setAdapter(chatPersonAdapter);


        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());
    }

    private void DisplayLastSeen()
    {

    }

    private void SendMessage()
    {
        message = message_edittext.getText().toString();
       // message_textview.setText(message);

        if (TextUtils.isEmpty(message))
        {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Create a new user with a first and last name
            Map<String, Object> msg = new HashMap<>();
            msg.put("from", UserModel.getUsername());
            msg.put("time", saveCurrentTime);
            msg.put("date", saveCurrentDate);
            msg.put("text", message);

            // Add a new document with a generated ID
            db.collection(UserModel.getUsername()).document("person").collection(chatwith_username).document(String.valueOf(System.currentTimeMillis())).set(msg).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        private static final String TAG = "a";

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(chat_person.this, "success", Toast.LENGTH_SHORT).show();
                            message_edittext.setText("");
                            chatPersonAdapter.notifyDataSetChanged();

                            //onStart();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        private static final String TAG = "b";

                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });


            Map<String, Object> msg1 = new HashMap<>();
            msg1.put("from", UserModel.getUsername());
            msg1.put("time", saveCurrentTime);
            msg1.put("date", saveCurrentDate);
            msg1.put("text", message);

            // Add a new document with a generated ID
            db.collection(chatwith_username).document("person").collection(UserModel.getUsername()).document(String.valueOf(System.currentTimeMillis())).set(msg1).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                private static final String TAG = "a";

                @Override
                public void onSuccess(Void aVoid)
                {
                    //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                    Toast.makeText(chat_person.this, "success", Toast.LENGTH_SHORT).show();
                    message_edittext.setText("");
                    chatPersonAdapter.notifyDataSetChanged();

                    //onStart();
                }
            })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        private static final String TAG = "b";

                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });


            Map<String, Object> msg2 = new HashMap<>();
            msg2.put("name",ChatWithModel.getName());
            msg2.put("photo",ChatWithModel.getPhoto());
            msg2.put("last_message", message);
            msg2.put("last_time", saveCurrentTime);

            // Add a new document with a generated ID
            db.collection(UserModel.getUsername()).document("chatlist").collection("chatlist").document(chatwith_username).set(msg2).addOnSuccessListener(new OnSuccessListener<Void>()
            {
                private static final String TAG = "a";

                @Override
                public void onSuccess(Void aVoid)
                {
                    //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                 //   Toast.makeText(chat_person.this, "success", Toast.LENGTH_SHORT).show();
                  //  message_edittext.setText("");
                  //  chatPersonAdapter.notifyDataSetChanged();

                   // onStart();
                }
            })
                    .addOnFailureListener(new OnFailureListener()
                    {
                        private static final String TAG = "b";

                        @Override
                        public void onFailure(@NonNull Exception e)
                        {
                            Log.w(TAG, "Error adding document", e);
                        }
                    });
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
       /* DocumentReference docRef = db.collection(messageSenderId).document("person").collection("Adwait");
        docRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
        {
            private static final String TAG = "s";

            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task)
            {
                if (task.isSuccessful())
                {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists())
                    {
                       // message_textview.setText();

                        Messages1 messages = document.toObject(Messages1.class);
                        messagesList.add(messages);
                        messageAdapter.notifyDataSetChanged();
                        userMessagesList.smoothScrollToPosition(userMessagesList.getAdapter().getItemCount());

                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    }
                    else
                    {
                        Log.d(TAG, "No such document");
                    }
                }
                else
                {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });*/
    }
}