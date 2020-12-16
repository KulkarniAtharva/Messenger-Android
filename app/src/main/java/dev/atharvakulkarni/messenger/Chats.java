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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;

public class Chats extends Fragment
{
    ChatsBinding chatsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        chatsBinding = DataBindingUtil.inflate(inflater, R.layout.chats, container, false);
        View view = chatsBinding.getRoot();

        recyclerView = chatsBinding.recyclerView;
        recyclerView.setVisibility(View.INVISIBLE);


        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always
        // populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);





        // recyclerView.setLayoutManager(new LinearLayoutManager(download.this));
        ChatsAdapter myAdapter = new ChatsAdapter(recyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

        ((ChatsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");
        ((ChatsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");

        return view;
    }
}
