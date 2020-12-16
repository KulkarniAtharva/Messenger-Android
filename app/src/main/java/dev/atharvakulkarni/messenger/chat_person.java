package dev.atharvakulkarni.messenger;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class chat_person extends AppCompatActivity
{
    EditText message_edittext;
    FrameLayout send;
    String message;
    TextView message_textview;
    FirebaseFirestore db;
    private String saveCurrentTime, saveCurrentDate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_person);

        message_edittext = findViewById(R.id.editText_message);
        message_textview = findViewById(R.id.message_textview);
        send = findViewById(R.id.send);

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();

        IntializeControllers();

        send.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                SendMessage();

            }
        });

        DisplayLastSeen();




    }

    private void IntializeControllers()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());
    }

    private void DisplayLastSeen()
    {

    }

    private void SendMessage()
    {
        message = message_edittext.getText().toString();
        message_textview.setText(message);

        if (TextUtils.isEmpty(message))
        {
            Toast.makeText(this, "first write your message...", Toast.LENGTH_SHORT).show();
        }
        else
        {
            // Create a new user with a first and last name
            Map<String, Object> msg = new HashMap<>();
            msg.put("sent by", "Atharva");
            msg.put("sent time", saveCurrentTime);
            msg.put("sent date", saveCurrentDate);
            msg.put("text", message);

            // Add a new document with a generated ID
            db.collection("Atharva").document("person").collection("Adwait").document("message").set(msg).addOnSuccessListener(new OnSuccessListener<Void>()
                    {
                        private static final String TAG = "a";

                        @Override
                        public void onSuccess(Void aVoid)
                        {
                            //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                            Toast.makeText(chat_person.this, "success", Toast.LENGTH_SHORT).show();
                            message_edittext.setText("");
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
    }

}