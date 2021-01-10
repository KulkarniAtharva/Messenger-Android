package dev.atharvakulkarni.messenger;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import dev.atharvakulkarni.messenger.service.model.ChatWithModel;

public class ChatWithGetData
{
    static FirebaseFirestore db;

    static int user(String username)
    {
        db = FirebaseFirestore.getInstance();

        DocumentReference docRef = db.collection("users").document(username);
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
                        String on_off_status = document.getString("on_off_status");
                        String photo = document.getString("photo");
                        String status = document.getString("status");

                        ChatWithModel.setLast_seen(last_seen);
                        ChatWithModel.setOn_off_status(on_off_status);
                        ChatWithModel.setStatus(status);
                        ChatWithModel.setName(name);
                        ChatWithModel.setPhoto(photo);
                        ChatWithModel.setUsername(username);

                       // Toast.makeText(context, ChatWithModel.getUsername(), Toast.LENGTH_SHORT).show();

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

        return 1;
    }
}