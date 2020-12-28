package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
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
import java.util.List;

import dev.atharvakulkarni.messenger.databinding.ChatsBinding;
import dev.atharvakulkarni.messenger.databinding.FriendRequestBinding;
import dev.atharvakulkarni.messenger.databinding.FriendsBinding;

public class FriendRequest extends AppCompatActivity
{
    FriendRequestBinding friendRequestBinding;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    String messageSenderId;
    ArrayList<Users_FriendRequest_Model> list= new ArrayList();
    FriendRequestAdapter myAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        friendRequestBinding = DataBindingUtil.setContentView(this,R.layout.friend_request);

       // friendRequestBinding = DataBindingUtil.inflate(inflater, R.layout.friend_request, container, false);
       // View view = friendRequestBinding.getRoot();

        recyclerView = friendRequestBinding.recyclerView;

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        messageSenderId = mAuth.getCurrentUser().getUid();

        recyclerView.setVisibility(View.VISIBLE);


        Toast.makeText(this, UserModel.getUsername(), Toast.LENGTH_SHORT).show();

        // recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));

        // custom adapters always
        // populate the recycler view with items
        linearLayoutManager = new LinearLayoutManager(this);
       // linearLayoutManager.setReverseLayout(true);
       // linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        myAdapter = new FriendRequestAdapter(recyclerView,FriendRequest.this,list);
        recyclerView.setAdapter(myAdapter);

        // recyclerView.setLayoutManager(new LinearLayoutManager(download.this));

        List<String> temp = new ArrayList<>();


        db.collection(UserModel.getUsername()).document("friend_requests").collection("friend_requests")
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
                                Log.d("TAG", document.getId() + " => " + document.getData());

                                temp.add(document.getId());

                                String names = document.getString("name");
                                String photo = document.getString("photo");

                                Users_FriendRequest_Model model = new Users_FriendRequest_Model(Users_FriendRequest_Model.FRIENDREQUEST_TYPE,names,photo,document.getId());
                                list.add(model);

                                myAdapter.notifyDataSetChanged();
                                // ((FriendRequestAdapter) recyclerView.getAdapter()).update(names,photo,0);

                              //  FriendRequestAdapter myAdapter = new FriendRequestAdapter(recyclerView,FriendRequest.this,list);
                            }

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
                                                    Log.d("TAG1", document.getId() + " => " + document.getData());

                                                    String names = document.getString("name");
                                                    String photo = document.getString("photo");

                                                    Users_FriendRequest_Model model = new Users_FriendRequest_Model(Users_FriendRequest_Model.USERS_TYPE,names,photo,document.getId());

                                                    if(temp.isEmpty())
                                                    {
                                                        if (!(UserModel.getUsername().equals(document.getId())))
                                                            list.add(model);
                                                    }
                                                    else
                                                    {
                                                        for (String temp : temp)
                                                        {
                                                            if (!(UserModel.getUsername().equals(document.getId())) && !(temp.equals(document.getId())))
                                                                list.add(model);
                                                        }
                                                    }




                                                    myAdapter.notifyDataSetChanged();
                                                   // ((FriendRequestAdapter) recyclerView.getAdapter()).update(list);

                                                   // FriendRequestAdapter myAdapter = new FriendRequestAdapter(recyclerView,FriendRequest.this,list);
                                                }
                                            }
                                            else
                                            {
                                                Log.d("TAG", "Error getting documents: ", task.getException());
                                            }
                                        }
                                    });
                        }
                        else
                        {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });






    }

    /*@Override
    protected void onStart()
    {
        super.onStart();
        myAdapter.sta;
    }
    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }*/
}