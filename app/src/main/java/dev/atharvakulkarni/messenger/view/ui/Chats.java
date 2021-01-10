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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import dev.atharvakulkarni.messenger.service.model.ChatlistModel;
import dev.atharvakulkarni.messenger.view.ViewModelFactory.SignInViewModelFactory;
import dev.atharvakulkarni.messenger.view.adapter.ChatsAdapter;
import dev.atharvakulkarni.messenger.R;
import dev.atharvakulkarni.messenger.service.model.UserModel;
import dev.atharvakulkarni.messenger.databinding.ChatsBinding;
import dev.atharvakulkarni.messenger.viewmodel.ChatsViewModel;
import dev.atharvakulkarni.messenger.viewmodel.SignInViewModel;

public class Chats extends Fragment
{
    ChatsBinding chatsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;
    ChatsAdapter chatsAdapter;
    ArrayList<ChatlistModel> list = new ArrayList();
    ChatsViewModel chatsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        chatsBinding = DataBindingUtil.inflate(inflater, R.layout.chats, container, false);
        View view = chatsBinding.getRoot();

        recyclerView = chatsBinding.recyclerView;

        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always populate the recycler view with items

        chatsViewModel = new ViewModelProvider(this).get(ChatsViewModel.class);
        chatsViewModel.init();
        chatsViewModel.getChatlistmodel().observe(this, new Observer<List<ChatlistModel>>()
        {
            @Override
            public void onChanged(List<ChatlistModel> chatlistModels)
            {
                Toast.makeText(getContext(), "yoooo", Toast.LENGTH_SHORT).show();
                chatsAdapter.notifyDataSetChanged();
            }
        });

        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        chatsAdapter = new ChatsAdapter(getContext(),recyclerView,chatsViewModel.getChatlistmodel().getValue());
        recyclerView.setAdapter(chatsAdapter);



       // getData();

        return view;
    }

    @Override
    public void onResume()
    {
        super.onResume();

      //  list.clear();
       // chatsAdapter.notifyDataSetChanged();

        getData();
    }

    void getData()
    {

    }
}