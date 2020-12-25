package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;
import dev.atharvakulkarni.messenger.databinding.FriendRequestBinding;
import dev.atharvakulkarni.messenger.databinding.FriendsBinding;

public class FriendRequest extends AppCompatActivity
{
    FriendRequestBinding friendRequestBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        friendRequestBinding = DataBindingUtil.setContentView(this,R.layout.friend_request);

       // friendRequestBinding = DataBindingUtil.inflate(inflater, R.layout.friend_request, container, false);
       // View view = friendRequestBinding.getRoot();

        recyclerView = friendRequestBinding.recyclerView;
        recyclerView.setVisibility(View.INVISIBLE);

        db = FirebaseFirestore.getInstance();


        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always
        // populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // recyclerView.setLayoutManager(new LinearLayoutManager(download.this));
        FriendRequestAdapter myAdapter = new FriendRequestAdapter(recyclerView,this,new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

        ((FriendRequestAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");
        ((FriendRequestAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");


    }
}