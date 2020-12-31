package dev.atharvakulkarni.messenger;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/*public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>
{
    private final List<Contact> mValues;
   // private final OnListFragmentInteractionListener mListener;

    public ChatsAdapter(List<Contact> items)//, OnListFragmentInteractionListener listener)
    {
        mValues = items;
      //  mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chats_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position)
    {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).name);

        String lastMessage = holder.mItem.lastMessage;
        String[] lastMessages = lastMessage.split("\n");
        if (lastMessages.length > 1)
        {
            if (lastMessages[0].length() > 25)
                lastMessage = lastMessages[0].substring(0, 25).concat("...");
            else
                lastMessage = lastMessages[0].concat("...");
        }
        else if(lastMessage.length() > 25)
            lastMessage = lastMessage.substring(0, 25).concat("...");

        holder.mContentView.setText(lastMessage);
        String date = Common.getDateString(holder.mItem.lastUpdated);
        String todayDate = Common.getDateString(System.currentTimeMillis());

        if (todayDate.compareTo(date) == 0)
            holder.typeView.setText(Common.getUserTime(holder.mItem.lastUpdated));
        else
            holder.typeView.setText(Common.getUserFriendlyDate(holder.mView.getContext(), holder.mItem.lastUpdated));

        if(holder.mItem.unreadCount==0)
        {
            holder.typeView.setTextColor(holder.typeTextColor);
            holder.countLayout.setVisibility(View.GONE);
        }
        else
        {
            int color = holder.typeView.getResources().getColor(R.color.colorEmerland);
            holder.typeView.setTextColor(color);
            holder.countView.setText(String.valueOf(holder.mItem.unreadCount));
            holder.countLayout.setVisibility(View.VISIBLE);
            holder.countImageView.setColorFilter(color);
        }

        holder.mView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                /*if(null != mListener)
                {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }*/
      /*      }
        });
    }

    @Override
    public int getItemCount()
    {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView typeView;
        private final TextView countView;
        private final View countLayout;
        private final ImageView countImageView;
        public Contact mItem;
        public int typeTextColor;

        public ViewHolder(View view)
        {
            super(view);
            mView = view;
            mIdView = (TextView) view.findViewById(R.id.id);
            mContentView = (TextView) view.findViewById(R.id.content);
            typeView = (TextView) view.findViewById(R.id.type);
            countView = (TextView) view.findViewById(R.id.count);
            countImageView = (ImageView) view.findViewById(R.id.imageView_count);
            countLayout = view.findViewById(R.id.layout_count);

            typeTextColor = typeView.getCurrentTextColor();
        }

        @Override
        public String toString()
        {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}*/


public class ChatsAdapter extends RecyclerView.Adapter<ChatsAdapter.ViewHolder>
{
    Context context;
    private List<ChatlistModel> chatlist;
    RecyclerView recyclerView;
    ChatlistModel object;

   /* public void update(String name,String lastmessage,String count)
    {
        this.name.add(name);
        this.lastmessage.add(lastmessage);
        this.count.add(count);
        /*duedates.add(due_date);
        givendates.add(given_date);
        description.add(des);
        userphotouris.add(userphotoUri);*/

        //if(getItemCount() == 0)
        //    Toast.makeText(context, "No Results Found", Toast.LENGTH_SHORT).show();
        // else
        //notifyDataSetChanged();  // refreshes the recycler view automatically
        // Toast.makeText(context, getItemCount()+"", Toast.LENGTH_SHORT).show();
   // }*/

    public ChatsAdapter(Context context, RecyclerView recyclerView,List<ChatlistModel> chatlist)
    {
        this.context = context;
        this.chatlist = chatlist;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)   // to create view for recycler view item
    {
        View view = LayoutInflater.from(context).inflate(R.layout.chats_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position)
    {
        object = chatlist.get(position);

        /*Glide.with(context)
                .load(userphotouris.get(position))
                .into(holder.circleImageView);*/

        holder.name.setText(object.name);
        holder.lastmessage.setText(object.lastmessage);
        holder.count.setText(object.count);
    }

    @Override
    public int getItemCount()       // return the no. of items
    {
        return chatlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView name,lastmessage,count;

        public ViewHolder(View itemView)        // represents indiv list items
        {
            super(itemView);

            name = itemView.findViewById(R.id.id);
            lastmessage = itemView.findViewById(R.id.type);
            count = itemView.findViewById(R.id.count);

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
                    intent.putExtra("username",object.username);
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