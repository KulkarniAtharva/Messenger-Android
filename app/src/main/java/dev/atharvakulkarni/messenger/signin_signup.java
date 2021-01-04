package dev.atharvakulkarni.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import dev.atharvakulkarni.messenger.databinding.SigninSignupBinding;

public class signin_signup extends AppCompatActivity
{
    ConstraintLayout signin_page,signup_page;
    Button continue_btn, signup_button;
    TextView signin,signup;
    TextInputEditText signin_username_edittext,signin_password_edittext,name_edittext,signup_username_edittext,signup_password_edittext,signup_reenterpassword_edittext;
    SigninSignupBinding signinSignupBinding;
    // SignInSignUpViewModel signInSignUpViewModel;
    private FirebaseAuth mAuth;
    String username,password,name,reenterpassword, saveCurrentTime, saveCurrentDate;
    FirebaseFirestore db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        signinSignupBinding = DataBindingUtil.setContentView(this, R.layout.signin_signup);

        signin_page = signinSignupBinding.signinPage;
        signup_page = signinSignupBinding.signupPage;
        continue_btn = signinSignupBinding.continueButton;
        signup_button = signinSignupBinding.signupbutton;
        signin = signinSignupBinding.signin;
        signup = signinSignupBinding.signup;
        signin_username_edittext = signinSignupBinding.username;
        signin_password_edittext = signinSignupBinding.password;
        signup_username_edittext = signinSignupBinding.username2;
        signup_password_edittext = signinSignupBinding.password2;
        name_edittext = signinSignupBinding.name;
        signup_reenterpassword_edittext = signinSignupBinding.reenterPassword;

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();
      //  messageSenderId = mAuth.getCurrentUser().getUid();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white,getTheme()));

       // signInSignUpViewModel = new ViewModelProvider(this).get(SignInSignUpViewModel.class);

        continue_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = signin_username_edittext.getText().toString()+"@gmail.com";
                password = signin_password_edittext.getText().toString();

              //  UserModel.setUsername(signin_username_edittext.getText().toString());

                UserGetData.user(signin_username_edittext.getText().toString());

                loginUserAccount(username,password);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                name = name_edittext.getText().toString();
                username = signup_username_edittext.getText().toString()+"@gmail.com";
                password = signup_password_edittext.getText().toString();
                reenterpassword = signup_reenterpassword_edittext.getText().toString();

               // UserModel.setUsername(signup_username_edittext.getText().toString());

                UserGetData.user(signup_username_edittext.getText().toString());

                if(password.equals(reenterpassword))
                    signupUserAccount(name,username,password);
                else
                    Toast.makeText(signin_signup.this, "Password did not match", Toast.LENGTH_SHORT).show();
            }
        });

        signin.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signup_page.setVisibility(View.GONE);
                signin_page.setVisibility(View.VISIBLE);
            }
        });

        signup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                signin_page.setVisibility(View.GONE);
                signup_page.setVisibility(View.VISIBLE);
            }
        });
    }

    private String getCurrentDateTime()
    {
        Calendar calendar = Calendar.getInstance();

        SimpleDateFormat currentDate = new SimpleDateFormat("MMM dd, yyyy");
        saveCurrentDate = currentDate.format(calendar.getTime());

        SimpleDateFormat currentTime = new SimpleDateFormat("hh:mm a");
        saveCurrentTime = currentTime.format(calendar.getTime());

        return saveCurrentDate+", "+saveCurrentTime;
    }

    private void loginUserAccount(String username,String password)
    {
        mAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
        {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task)
            {
                if (task.isSuccessful())
                {
                    // Sign in success, update UI with the signed-in user's information
                  //  Log.d(TAG, "signInWithEmail:success");
                   // FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);

                   // Toast.makeText(signin_signup.this, "Success"+, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(signin_signup.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                   // Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(signin_signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }

    private void signupUserAccount(String name,String username,String password)
    {
        mAuth.createUserWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>()
                {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task)
                    {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(signin_signup.this, "Success", Toast.LENGTH_SHORT).show();
                            createUser();

                            Intent intent = new Intent(signin_signup.this,MainActivity.class);
                            startActivity(intent);
                        }
                        else
                        {
                          //  Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(signin_signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void createUser()
    {
        // Create a new user with a first and last name
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("status","Good Morning");
        user.put("photo","photo");
        user.put("last_seen",getCurrentDateTime());
        user.put("on_off_status", "Online");

        // Add a new document with a generated ID
        db.collection("users").document(UserModel.getUsername()).set(user).addOnSuccessListener(new OnSuccessListener<Void>()
        {
            @Override
            public void onSuccess(Void aVoid)
            {
                //Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                Toast.makeText(signin_signup.this, "success", Toast.LENGTH_SHORT).show();
            }
        });

        Map<String, Object> city = new HashMap<>();

        db.collection(UserModel.getUsername()).document("person")
                .set(city)
                .addOnSuccessListener(new OnSuccessListener<Void>()
                {
                    @Override
                    public void onSuccess(Void aVoid)
                    {
                    }
                });

        db.collection(UserModel.getUsername()).document("friends").collection("friends").add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                        @Override
                        public void onSuccess(DocumentReference documentReference)
                        {
                        }
                });


        db.collection(UserModel.getUsername()).document("friend_requests").collection("friend_requests").add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {}
                });

        db.collection(UserModel.getUsername()).document("chatlist").collection("chatlist").add(city)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                    }
                });
    }
}