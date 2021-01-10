package dev.atharvakulkarni.messenger.view.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import dev.atharvakulkarni.messenger.service.model.MessagesModel;
import dev.atharvakulkarni.messenger.R;
import dev.atharvakulkarni.messenger.service.model.UserModel;

public class ChatPersonAdapter extends RecyclerView.Adapter<ChatPersonAdapter.MessageViewHolder>
{
    private List<MessagesModel> userMessagesList;
    private FirebaseAuth mAuth;
   // private DatabaseReference usersRef;
    Context context;

    FirebaseFirestore db;

    public ChatPersonAdapter(List<MessagesModel> userMessagesList, Context context)
    {
        this.userMessagesList = userMessagesList;
        this.context = context;
    }

    public class MessageViewHolder extends RecyclerView.ViewHolder
    {
        public TextView senderMessageText, receiverMessageText;
       // public CircleImageView receiverProfileImage;
       // public ImageView messageSenderPicture, messageReceiverPicture;

        public MessageViewHolder(@NonNull View itemView)
        {
            super(itemView);

            senderMessageText = (TextView) itemView.findViewById(R.id.sender_message_text);
            receiverMessageText = (TextView) itemView.findViewById(R.id.receiver_message_text);
         //   receiverProfileImage = (CircleImageView) itemView.findViewById(R.id.message_profile_image);
         //   messageReceiverPicture = itemView.findViewById(R.id.message_receiver_image_view);
         //   messageSenderPicture = itemView.findViewById(R.id.message_sender_image_view);
        }
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.custom_messages_layout, viewGroup, false);
        mAuth = FirebaseAuth.getInstance();

        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageViewHolder messageViewHolder, final int position)
    {
        String messageSenderId = mAuth.getCurrentUser().getUid();
        MessagesModel messages = userMessagesList.get(position);

      //  messageViewHolder.senderMessageText.setText(messages.getText());

       // Toast.makeText(context, messages.getText(), Toast.LENGTH_SHORT).show();

        String fromUsername = messages.getFrom();
       // String fromMessageType = messages.getType();

       // usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(fromUserID);

        db = FirebaseFirestore.getInstance();

       /* usersRef.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                if (dataSnapshot.hasChild("image"))
                {
                    String receiverImage = dataSnapshot.child("image").getValue().toString();

                  //  Picasso.get().load(receiverImage).placeholder(R.drawable.profile_image).into(messageViewHolder.receiverProfileImage);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {

            }
        });*/

        messageViewHolder.receiverMessageText.setVisibility(View.GONE);
      //  messageViewHolder.receiverProfileImage.setVisibility(View.GONE);
        messageViewHolder.senderMessageText.setVisibility(View.GONE);
      //  messageViewHolder.messageSenderPicture.setVisibility(View.GONE);
      //  messageViewHolder.messageReceiverPicture.setVisibility(View.GONE);

       // if (fromMessageType.equals("text"))
        {
            if (fromUsername.equals(UserModel.getUsername()))
            {
                messageViewHolder.senderMessageText.setVisibility(View.VISIBLE);

            //    messageViewHolder.senderMessageText.setBackgroundResource(R.drawable.sender_messages_layout);
                messageViewHolder.senderMessageText.setTextColor(Color.BLACK);
                messageViewHolder.senderMessageText.setText(messages.getText() + "\n \n" + messages.getTime());// + " - " + messages.getDate());
            }
            else
            {
              //  messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.receiverMessageText.setVisibility(View.VISIBLE);

             //   messageViewHolder.receiverMessageText.setBackgroundResource(R.drawable.receiver_messages_layout);
                messageViewHolder.receiverMessageText.setTextColor(Color.BLACK);
                messageViewHolder.receiverMessageText.setText(messages.getText() + "\n \n" + messages.getTime());// + " - " + messages.getDate());
            }
        }
        /*else if (fromMessageType.equals("image"))
        {
            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.messageSenderPicture.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageSenderPicture);
            }
            else
            {
                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setVisibility(View.VISIBLE);
                Picasso.get().load(messages.getMessage()).into(messageViewHolder.messageReceiverPicture);
            }
        }
        else if (fromMessageType.equals("pdf") || fromMessageType.equals("docx"))
        {
            if (fromUserID.equals(messageSenderId))
            {
                messageViewHolder.messageSenderPicture.setVisibility(View.VISIBLE);
                messageViewHolder.messageSenderPicture.setBackground(R.drawable.file);

                messageViewHolder.itemView.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessagesList.get(position).getMessage()));
                        messageViewHolder.itemView.getContext().startActivity(intent);
                    }
                });
            }
            else
            {
                messageViewHolder.receiverProfileImage.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setVisibility(View.VISIBLE);
                messageViewHolder.messageReceiverPicture.setBackground(R.drawable.file);
            }
        }*/
       /* if (fromUserID.equals(messageSenderId))
        {
            messageViewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (userMessagesList.get(position).getType().equals("pdf") || userMessagesList.get(position).getType().equals("docx")) {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "Download and View this Document",
                                        "Cancel",
                                        "Delete For EveryOne"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                   deleteSentMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);

                                }
                                else if (position == 1)
                                {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessagesList.get(position).getMessage()));
                                    messageViewHolder.itemView.getContext().startActivity(intent);
                                }
                                else if (position == 3)
                                {
                                     deleteMessageForEveryOne(position,messageViewHolder);
                                }
                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("text"))
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "Cancel",
                                        "Delete For EveryOne"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                    deleteSentMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);

                                }
                                else if (position == 2)
                                {
                                    deleteMessageForEveryOne(position,messageViewHolder);
                                }
                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("image"))
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "View This Image",
                                        "Cancel",
                                        "Delete For EveryOne"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                  deleteSentMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);

                                }
                                else if (position == 1)
                                {
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),ImageViewerActivity.class);
                                    intent.putExtra("url",userMessagesList.get(position).getMessage());
                                    messageViewHolder.itemView.getContext().startActivity(intent);
                                }
                                else if (position == 3)
                                {
                                    deleteMessageForEveryOne(position,messageViewHolder);
                                }
                            }
                        });
                        builder.show();
                    }
                }
            });
        }
        else
        {
            messageViewHolder.itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if (userMessagesList.get(position).getType().equals("pdf") || userMessagesList.get(position).getType().equals("docx"))
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "Download and View this Document",
                                        "Cancel"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                    deleteReceiverMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);

                                }
                                else if (position == 1)
                                {
                                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(userMessagesList.get(position).getMessage()));
                                    messageViewHolder.itemView.getContext().startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("text"))
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "Cancel"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                    deleteReceiverMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                    else if (userMessagesList.get(position).getType().equals("image"))
                    {
                        CharSequence options[] = new CharSequence[]
                                {
                                        "Delete For me",
                                        "View This Image",
                                        "Cancel"
                                };
                        AlertDialog.Builder builder = new AlertDialog.Builder(messageViewHolder.itemView.getContext());
                        builder.setTitle("Delete Message");

                        builder.setItems(options, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int position)
                            {
                                if (position == 0)
                                {
                                    deleteReceiverMessage(position,messageViewHolder);
                                    Intent intent = new Intent(messageViewHolder.itemView.getContext(),MainActivity.class);
                                    messageViewHolder.itemView.getContext().startActivity(intent);

                                }
                                else if (position == 1)
                                {
                                   Intent intent = new Intent(messageViewHolder.itemView.getContext(),ImageViewerActivity.class);
                                    intent.putExtra("url",userMessagesList.get(position).getMessage());
                                    messageViewHolder.itemView.getContext().startActivity(intent);
                                }
                            }
                        });
                        builder.show();
                    }
                }
            });
        }*/
    }

    @Override
    public int getItemCount()
    {
        return userMessagesList.size();
    }

    /*private void deleteSentMessage(final int position, final MessageViewHolder holder)
    {
      DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
      rootRef.child("Messages")
              .child(userMessagesList.get(position).getFrom())
              .child(userMessagesList.get(position).getTo())
              .child(userMessagesList.get(position).getMessageID())
              .removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
      {
          @Override
          public void onComplete(@NonNull Task<Void> task)
          {
               if(task.isSuccessful())
               {
                   Toast.makeText(holder.itemView.getContext(),"Deleted Successfully.", Toast.LENGTH_SHORT).show();
               }
               else
               {
                   Toast.makeText(holder.itemView.getContext(),"Error Occurred.", Toast.LENGTH_SHORT).show();
               }
          }
      });
    }

    private void deleteReceiverMessage(final int position, final MessageViewHolder holder)
    {
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Messages")
                .child(userMessagesList.get(position).getTo())
                .child(userMessagesList.get(position).getFrom())
                .child(userMessagesList.get(position).getMessageID())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    Toast.makeText(holder.itemView.getContext(),"Deleted Successfully.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(holder.itemView.getContext(),"Error Occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteMessageForEveryOne(final int position, final MessageViewHolder holder)
    {
        final DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("Messages")
                .child(userMessagesList.get(position).getFrom())
                .child(userMessagesList.get(position).getTo())
                .child(userMessagesList.get(position).getMessageID())
                .removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
        {
            @Override
            public void onComplete(@NonNull Task<Void> task)
            {
                if(task.isSuccessful())
                {
                    rootRef.child("Messages")
                            .child(userMessagesList.get(position).getFrom())
                            .child(userMessagesList.get(position).getTo())
                            .child(userMessagesList.get(position).getMessageID())
                            .removeValue().addOnCompleteListener(new OnCompleteListener<Void>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<Void> task)
                        {
                           if(task.isSuccessful())
                           {
                               Toast.makeText(holder.itemView.getContext(),"Error Occurred.", Toast.LENGTH_SHORT).show();
                           }
                        }
                    });
                }
                else
                {
                    Toast.makeText(holder.itemView.getContext(),"Error Occurred.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/
}