package dev.atharvakulkarni.messenger;

import android.content.Intent;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;
import dev.atharvakulkarni.messenger.databinding.FriendsBinding;

public class Friends extends Fragment
{
    FriendsBinding friendsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;
    FloatingActionButton floatingActionButton;
    String names;
    String photo;
    FriendsAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        friendsBinding = DataBindingUtil.inflate(inflater, R.layout.friends, container, false);
        View view = friendsBinding.getRoot();

        recyclerView = friendsBinding.recyclerView;
        floatingActionButton = friendsBinding.requests;
        recyclerView.setVisibility(View.INVISIBLE);

        db = FirebaseFirestore.getInstance();

        recyclerView.setVisibility(View.VISIBLE);

        floatingActionButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getContext(), FriendRequest.class);
                startActivity(intent);
            }
        });

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always
        // populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(getContext());
       // linearLayoutManager.setReverseLayout(true);
       // linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new FriendsAdapter(recyclerView, getContext(), new ArrayList<String>(), new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

                db.collection("users")
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

                                names = document.getString("name");
                                photo = document.getString("photo");

                               // Toast.makeText(getContext(), names.get(0), Toast.LENGTH_SHORT).show();

                                //myAdapter.notifyDataSetChanged();

                                ((FriendsAdapter) recyclerView.getAdapter()).update(names,photo);


                            }
                        }
                        else
                        {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });



        return view;
    }
}