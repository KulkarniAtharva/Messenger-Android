package dev.atharvakulkarni.messenger.view.ui;

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
import androidx.lifecycle.ViewModelProvider;

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

import dev.atharvakulkarni.messenger.R;
import dev.atharvakulkarni.messenger.UserGetData;
import dev.atharvakulkarni.messenger.databinding.SigninBinding;
import dev.atharvakulkarni.messenger.service.model.UserModel;
import dev.atharvakulkarni.messenger.view.ViewModelFactory.SignInViewModelFactory;
import dev.atharvakulkarni.messenger.viewmodel.SignInViewModel;

public class signin_signup extends AppCompatActivity
{
    ConstraintLayout signin_page,signup_page;
    Button continue_btn, signup_button;
    TextView signin,signup;
    TextInputEditText signin_username_edittext,signin_password_edittext,name_edittext,signup_username_edittext,signup_password_edittext,signup_reenterpassword_edittext;
    SigninBinding signinBinding;
    private FirebaseAuth mAuth;
    String username,password,name,reenterpassword, saveCurrentTime, saveCurrentDate;
    FirebaseFirestore db;
    SignInViewModel signInViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        signinBinding = DataBindingUtil.setContentView(this, R.layout.signin);

        signinBinding.setLifecycleOwner(this);

        Toast.makeText(this, "hellloooo", Toast.LENGTH_SHORT).show();

       // signinSignupBinding.setLoginViewModel(loginViewModel);


      /*  SignIn_SignUpViewModel.getUser().observe(this, new Observer<LoginModel>()
        {
            @Override
            public void onChanged(@Nullable LoginModel loginUser)
            {
                if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrEmailAddress()))
                {
                    signinSignupBinding.txtEmailAddress.setError("Enter an E-Mail Address");
                    signinSignupBinding.txtEmailAddress.requestFocus();
                }
                else if (!loginUser.isEmailValid())
                {
                    signinSignupBinding.txtEmailAddress.setError("Enter a Valid E-mail Address");
                    signinSignupBinding.txtEmailAddress.requestFocus();
                }
                else if (TextUtils.isEmpty(Objects.requireNonNull(loginUser).getStrPassword())) {
                    signinSignupBinding.txtPassword.setError("Enter a Password");
                    signinSignupBinding.txtPassword.requestFocus();
                }
                else if (!loginUser.isPasswordLengthGreaterThan5()) {
                    signinSignupBinding.txtPassword.setError("Enter at least 6 Digit password");
                    signinSignupBinding.txtPassword.requestFocus();
                }
                else {
                    signinSignupBinding.lblEmailAnswer.setText(loginUser.getStrEmailAddress());
                    signinSignupBinding.lblPasswordAnswer.setText(loginUser.getStrPassword());
                }

            }
        });*/




        init();

      //  messageSenderId = mAuth.getCurrentUser().getUid();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white,getTheme()));

        continue_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = signin_username_edittext.getText().toString()+"@gmail.com";
                password = signin_password_edittext.getText().toString();

              //  UserModel.setUsername(signin_username_edittext.getText().toString());

                UserGetData.user(signin_username_edittext.getText().toString());

                Toast.makeText(signin_signup.this, "nhhj", Toast.LENGTH_SHORT).show();

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

              //  UserModel.setUsername(signup_username_edittext.getText().toString());

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

    private void init()
    {
        signin_page = signinBinding.signinPage;
        signup_page = signinBinding.signupPage;
        continue_btn = signinBinding.continueButton;
        signup_button = signinBinding.signupbutton;
        signin = signinBinding.signin;
        signup = signinBinding.signup;
        signin_username_edittext = signinBinding.username;
        signin_password_edittext = signinBinding.password;
        signup_username_edittext = signinBinding.username2;
        signup_password_edittext = signinBinding.password2;
        name_edittext = signinBinding.name;
        signup_reenterpassword_edittext = signinBinding.reenterPassword;

        db = FirebaseFirestore.getInstance();

        mAuth = FirebaseAuth.getInstance();

       // signIn_signUpViewModel = ViewModelProvider.of(this).get(SignIn_SignUpViewModel.class);
        signInViewModel = new ViewModelProvider(this,new SignInViewModelFactory(this,new UserModel())).get(SignInViewModel.class);
        signinBinding.setUserModel(signInViewModel);
        //signIn_signUpViewModel.init();
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