package dev.atharvakulkarni.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class FriendRequestAdapter extends RecyclerView.Adapter<FriendRequestAdapter.ViewHolder>
{
    RecyclerView recyclerView;
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();
    int flag;

    public void update(String name,String photo,int flag)
    {
        this.name.add(name);
        this.photo.add(photo);
        this.flag = flag;
        /*duedates.add(due_date);
        givendates.add(given_date);
        description.add(des);
        userphotouris.add(userphotoUri);*/

        //if(getItemCount() == 0)
        //    Toast.makeText(context, "No Results Found", Toast.LENGTH_SHORT).show();
        // else
        notifyDataSetChanged();  // refreshes the recycler view automatically
        // Toast.makeText(context, getItemCount()+"", Toast.LENGTH_SHORT).show();
    }

    public FriendRequestAdapter(RecyclerView recyclerView,Context context,ArrayList<String> name,ArrayList<String> photo)
    {
        this.recyclerView = recyclerView;
        this.context = context;
        this.name = name;
        this.photo = photo;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   // to create view for recycler view item
    {
        View view = LayoutInflater.from(context).inflate(R.layout.friend_request_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        /*Glide.with(context)
                .load(userphotouris.get(position))
                .into(holder.circleImageView);*/

        Toast.makeText(context, flag+"", Toast.LENGTH_SHORT).show();

        if(flag == 1)
        {
            holder.buttons2.setVisibility(View.GONE);
            holder.buttons1.setVisibility(View.VISIBLE);
        }
        else
        {
            holder.buttons1.setVisibility(View.GONE);
            holder.buttons2.setVisibility(View.VISIBLE);
        }

           holder.name.setText(name.get(position));
           holder.photo.setText(photo.get(position));
        //  holder.count.setText(count.get(position));
    }

    @Override
    public int getItemCount()       // return the no. of items
    {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,photo;
        RelativeLayout buttons1,buttons2;

        public ViewHolder(View itemView)        // represents indiv list items
        {
            super(itemView);
           /* filename = itemView.findViewById(R.id.nameofFile);
            givendate = itemView.findViewById(R.id.givend);
            //duedate = itemView.findViewById(R.id.dued);
            teachername = itemView.findViewById(R.id.initial);
            circleImageView = itemView.findViewById(R.id.user_photo);*/

            name = itemView.findViewById(R.id.name);
            photo = itemView.findViewById(R.id.photo);
            buttons1 = itemView.findViewById(R.id.buttons1);
            buttons2 = itemView.findViewById(R.id.buttons2);


            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    int position = recyclerView.getChildLayoutPosition(view);

                   /* Intent intent = new Intent(context, download_each.class);
                    intent.putExtra("title",title.get(position));
                    intent.putExtra("description",description.get(position));
                    intent.putExtra("duedate",duedates.get(position));
                    intent.putExtra("givendate",givendates.get(position));
                    intent.putExtra("teachername",usernames.get(position));
                    intent.putExtra("url",urls.get(position));
                    intent.putExtra("position",position);

                    context.startActivity(intent);*/

                  //  Intent intent = new Intent(context,chat_person.class);
                  //  context.startActivity(intent);


                    // denotes that we are going to view something
                    // intent.setData(Uri.parse(urls.get(position)));
                    //intent.setType(Intent.ACTION_VIEW);

                   /* intent.setDataAndType(Uri.parse((urls.get(position))),Intent.ACTION_VIEW);
                    context.startActivity(intent);*/
                }
            });
        }
    }
}