package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;

public class Chats extends Fragment
{
    ChatsBinding chatsBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        chatsBinding = DataBindingUtil.inflate(inflater, R.layout.chats, container, false);
        View view = chatsBinding.getRoot();

        recyclerView = chatsBinding.recyclerView;

        db = FirebaseFirestore.getInstance();

        recyclerView.setVisibility(View.VISIBLE);

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        db.collection(UserModel.getUsername()).document("friend_requests").collection("friend_requests")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                                       {
                                           @Override
                                           public void onComplete(@NonNull Task<QuerySnapshot> task)
                                           {
                                               if (task.isSuccessful())
                                               {
                                                   for (QueryDocumentSnapshot document : task.getResult())
                                                   {
                                                       Log.d("TAG", document.getId() + " => " + document.getData());

                                                       temp.add(document.getId());

                                                       String names = document.getString("name");
                                                       String photo = document.getString("photo");

                                                       Users_FriendRequest_Model model = new Users_FriendRequest_Model(Users_FriendRequest_Model.FRIENDREQUEST_TYPE, names, photo, document.getId());
                                                       list.add(model);

                                                       myAdapter.notifyDataSetChanged();
                                                       // ((FriendRequestAdapter) recyclerView.getAdapter()).update(names,photo,0);

                                                       //  FriendRequestAdapter myAdapter = new FriendRequestAdapter(recyclerView,FriendRequest.this,list);
                                                   }
                                               }
                                           }
                                       });



        // recyclerView.setLayoutManager(new LinearLayoutManager(download.this));
        ChatsAdapter myAdapter = new ChatsAdapter(recyclerView,getContext(),new ArrayList<String>(),new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);

        ((ChatsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");
        ((ChatsAdapter) recyclerView.getAdapter()).update("Atharva","Hi hello","2");

        return view;
    }
}
