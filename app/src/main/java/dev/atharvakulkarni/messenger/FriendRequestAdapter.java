package dev.atharvakulkarni.messenger;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FriendRequestAdapter extends RecyclerView.Adapter
{
    RecyclerView recyclerView;
    Context context;
   // ArrayList<String> name = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();
    // int flag;
    private ArrayList<Users_FriendRequest_Model>dataSet;
    FirebaseFirestore db;
   // String messageSenderId;

    private FirebaseAuth mAuth;

    public void update(ArrayList<Users_FriendRequest_Model> dataSet)
    {
        this.dataSet = dataSet;

       // this.flag = flag;

        //if(getItemCount() == 0)
        //    Toast.makeText(context, "No Results Found", Toast.LENGTH_SHORT).show();
        // else
        notifyDataSetChanged();  // refreshes the recycler view automatically
        // Toast.makeText(context, getItemCount()+"", Toast.LENGTH_SHORT).show();
    }

    public FriendRequestAdapter(RecyclerView recyclerView,Context context,ArrayList<Users_FriendRequest_Model>data)
    {
        this.recyclerView = recyclerView;
        this.context = context;
        this.dataSet = data;
    }

   /* @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   // to create view for recycler view item
    {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_request_item,parent,false);
        return new ViewHolder(view);
    }*/

   /* @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        /*Glide.with(context)
                .load(userphotouris.get(position))
                .into(holder.circleImageView);*/

      /*     holder.name.setText(name.get(position));
           holder.photo.setText(photo.get(position));
        //  holder.count.setText(count.get(position));
    }*/

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
      //  messageSenderId = mAuth.getCurrentUser().getUid();


        View view;
        switch (viewType)
        {
            case Users_FriendRequest_Model.USERS_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.users_item, parent, false);
                return new UsersViewHolder(view);
            case Users_FriendRequest_Model.FRIENDREQUEST_TYPE:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friend_request_item, parent, false);
                return new FriendRequestViewHolder(view);
        }
        return null;
    }

    @Override
    public int getItemViewType(int position)
    {
        switch (dataSet.get(position).type)
        {
            case 0:
                return Users_FriendRequest_Model.USERS_TYPE;
            case 1:
                return Users_FriendRequest_Model.FRIENDREQUEST_TYPE;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int listPosition)
    {
        Users_FriendRequest_Model object = dataSet.get(listPosition);
        if (object != null)
        {
            switch (object.type)
            {
                case Users_FriendRequest_Model.USERS_TYPE:
                    ((UsersViewHolder) holder).name.setText(object.names);

                    break;
                case Users_FriendRequest_Model.FRIENDREQUEST_TYPE:
                    ((FriendRequestViewHolder) holder).name.setText(object.names);
                    break;
            }
        }
    }

    @Override
    public int getItemCount()       // return the no. of items
    {
        return dataSet.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder
    {
        TextView name;

        public UsersViewHolder(View itemView)
        {
            super(itemView);
            this.name = (TextView) itemView.findViewById(R.id.name);

            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = recyclerView.getChildLayoutPosition(view);
                    Users_FriendRequest_Model object = dataSet.get(position);

                    Toast.makeText(context, object.username, Toast.LENGTH_SHORT).show();

                    Map<String, Object> user = new HashMap<>();
                    user.put("name", UserModel.getName());
                    user.put("photo","photo");

                    // Add a new document with a generated ID
                    db.collection(object.username).document("friend_requests").collection("friend_requests").document(UserModel.getUsername()).set(user).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        private static final String TAG = "a";

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                           // Toast.makeText(context, "Friend Request sent to "+object.names, Toast.LENGTH_SHORT).show();

                        }
                    })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                private static final String TAG = "b";

                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            });
        }
    }

    public class FriendRequestViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,photo;
        Button accept,cancel;

        public FriendRequestViewHolder(View itemView)        // represents indiv list items
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            photo = itemView.findViewById(R.id.photo);
            accept = itemView.findViewById(R.id.accept_request);
            cancel = itemView.findViewById(R.id.cancel_request);

            accept.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = recyclerView.getChildLayoutPosition(itemView);
                    Users_FriendRequest_Model object = dataSet.get(position);

                    Map<String, Object> user1 = new HashMap<>();
                    user1.put("name", object.names);
                    user1.put("photo",object.photo);

                    // Add a new document with a generated ID
                    db.collection(UserModel.getUsername()).document("friends").collection("friends").document(object.username).set(user1).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                             Toast.makeText(context, "Friend Request accepted of "+object.username, Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Log.w("b", "Error adding document", e);
                                }
                            });

                    Map<String, Object> user2 = new HashMap<>();
                    user2.put("name", UserModel.getName());
                    user2.put("photo",UserModel.getPhoto());


                    db.collection(object.username).document("friends").collection("friends").document(UserModel.getUsername()).set(user2).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(context, "Friend Request accepted of "+object.username, Toast.LENGTH_SHORT).show();
                        }
                    })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Log.w("b", "Error adding document", e);
                                }
                            });


                    db.collection(UserModel.getUsername()).document("friend_requests").collection("friend_requests").document(object.username)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Log.w("TAG", "Error deleting document", e);
                                }
                            });
                }
            });

            cancel.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = recyclerView.getChildLayoutPosition(itemView);
                    Users_FriendRequest_Model object = dataSet.get(position);

                    db.collection(UserModel.getUsername()).document("friend_requests").collection("friend_requests").document(object.username)
                            .delete()
                            .addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid)
                                {
                                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener()
                            {
                                @Override
                                public void onFailure(@NonNull Exception e)
                                {
                                    Log.w("TAG", "Error deleting document", e);
                                }
                            });
                }
            });
        }
    }
}