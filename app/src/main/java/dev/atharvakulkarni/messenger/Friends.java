package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;
import dev.atharvakulkarni.messenger.databinding.FriendsBinding;

public class Friends extends Fragment
{
    FriendsBinding friendsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        friendsBinding = DataBindingUtil.inflate(inflater, R.layout.friends, container, false);
        View view = friendsBinding.getRoot();

        recyclerView = friendsBinding.recyclerView;
        recyclerView.setVisibility(View.INVISIBLE);

        db = FirebaseFirestore.getInstance();


        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always
        // populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        // recyclerView.setLayoutManager(new LinearLayoutManager(download.this));
        FriendsAdapter myAdapter = new FriendsAdapter(recyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

        ((FriendsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");
        ((FriendsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");

        return view;
    }
}