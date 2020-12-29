package dev.atharvakulkarni.messenger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FriendsAdapter extends RecyclerView.Adapter<FriendsAdapter.ViewHolder>
{
    RecyclerView recyclerView;
    Context context;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> photo = new ArrayList<>();
    ArrayList<String> username = new ArrayList<>();

   public void update(String name, String photo,String username)
    {
        this.name.add(name);
        this.photo.add(photo);
        this.username.add(username);

        if(getItemCount() == 0)
           Toast.makeText(context, "No Results Found", Toast.LENGTH_SHORT).show();
         else
           notifyDataSetChanged();  // refreshes the recycler view automatically
   }

    public FriendsAdapter(RecyclerView recyclerView,Context context,ArrayList<String> name,ArrayList<String> photo, ArrayList<String> username)
    {
        this.recyclerView = recyclerView;
        this.context = context;
        this.name = name;
        this.photo = photo;
        this.username = username;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   // to create view for recycler view item
    {
        View view = LayoutInflater.from(context).inflate(R.layout.friends_item,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        /*Glide.with(context)
                .load(userphotouris.get(position))
                .into(holder.circleImageView);*/

        holder.name.setText(name.get(position));
        holder.photo.setText(photo.get(position));
    }

    @Override
    public int getItemCount()       // return the no. of items
    {
        return name.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,photo;

        public ViewHolder(View itemView)        // represents indiv list items
        {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            photo = itemView.findViewById(R.id.photo);

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


                    

                    Intent intent = new Intent(context,chat_person.class);
                    context.startActivity(intent);


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