package dev.atharvakulkarni.messenger.view.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dev.atharvakulkarni.messenger.service.model.ChatlistModel;
import dev.atharvakulkarni.messenger.view.adapter.ChatsAdapter;
import dev.atharvakulkarni.messenger.R;
import dev.atharvakulkarni.messenger.service.model.UserModel;
import dev.atharvakulkarni.messenger.databinding.ChatsBinding;

public class Chats extends Fragment
{
    ChatsBinding chatsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;
    ChatsAdapter chatsAdapter;
    ArrayList<ChatlistModel> list = new ArrayList();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        chatsBinding = DataBindingUtil.inflate(inflater, R.layout.chats, container, false);
        View view = chatsBinding.getRoot();

        recyclerView = chatsBinding.recyclerView;

        db = FirebaseFirestore.getInstance();

       /* FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build();
        db.setFirestoreSettings(settings);*/

        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always populate the recycler view with items

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        chatsAdapter = new ChatsAdapter(getContext(),recyclerView,list);
        recyclerView.setAdapter(chatsAdapter);


       // getData();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

        list.clear();
        chatsAdapter.notifyDataSetChanged();

        getData();
    }

    void getData()
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
                                    list.add(model);
                                else
                                    Toast.makeText(getContext(), "No Chats", Toast.LENGTH_SHORT).show();

                                chatsAdapter.notifyDataSetChanged();
                            }
                        }
                    }
                });
    }
}