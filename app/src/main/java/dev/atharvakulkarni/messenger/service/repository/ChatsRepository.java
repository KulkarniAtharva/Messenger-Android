package dev.atharvakulkarni.messenger.service.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.atharvakulkarni.messenger.service.model.ChatlistModel;
import dev.atharvakulkarni.messenger.service.model.UserModel;

public class ChatsRepository
{
    public static ChatsRepository instance;
    private ArrayList<ChatlistModel> dataset = new ArrayList<>();
    FirebaseFirestore db;

    public static ChatsRepository getInstance()
    {
        if(instance == null)
        {
            instance = new ChatsRepository();
        }

        return instance;
    }

    public MutableLiveData<List<ChatlistModel>> getChats()
    {
        dataset.clear();
        db = FirebaseFirestore.getInstance();
        setChats();

        MutableLiveData<List<ChatlistModel>> data = new MutableLiveData<>();
        data.setValue(dataset);
        return data;
    }

    private void setChats()
    {
        db.collection(UserModel.getUsername()).document("chatlist").collection("chatlist")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : task.getResult())
                            {
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                String names = document.getString("name");
                                String photo = document.getString("photo");
                                String last_message = document.getString("last_message");
                                String last_time = document.getString("last_time");

                                ChatlistModel model = new ChatlistModel(document.getId(),names,photo,last_message,"1",last_time);

                                if(names != null)
                                    dataset.add(model);
                                //else
                                //    Toast.makeText(getContext(), "No Chats", Toast.LENGTH_SHORT).show();

                               // chatsAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}