package dev.atharvakulkarni.messenger;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import dev.atharvakulkarni.messenger.databinding.SigninSignupBinding;

public class signin_signup extends AppCompatActivity
{
    ConstraintLayout signin_page,signup_page;
    Button continue_btn, signup_button;
    TextView signin,signup;
    EditText username_edittext,password_edittext;
    SigninSignupBinding signinSignupBinding;
    // SignInSignUpViewModel signInSignUpViewModel;
    private FirebaseAuth mAuth;
    String username,password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_signup);
        signinSignupBinding = DataBindingUtil.setContentView(this, R.layout.signin_signup);

        signin_page = signinSignupBinding.signinPage;
        signup_page = signinSignupBinding.signupPage;
        continue_btn = signinSignupBinding.continueButton;
        signup_button = signinSignupBinding.signupButton;
        signin = signinSignupBinding.signin;
        signup = signinSignupBinding.signup;

        mAuth = FirebaseAuth.getInstance();

        getWindow().setStatusBarColor(getResources().getColor(R.color.white,getTheme()));

       // signInSignUpViewModel = new ViewModelProvider(this).get(SignInSignUpViewModel.class);

        continue_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(signin_signup.this, MainActivity.class);
                startActivity(intent);
            }
        });

        signup_button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                username = username_edittext.getText().toString()+"@gmail.com";
                password = password_edittext.getText().toString();

                loginUserAccount(username,password);

                Intent intent = new Intent(signin_signup.this,MainActivity.class);
                startActivity(intent);
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
                    FirebaseUser user = mAuth.getCurrentUser();
                    // updateUI(user);

                    Toast.makeText(signin_signup.this, "Success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(signin_signup.this,MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    // If sign in fails, display a message to the user.
                   // Log.w(TAG, "signInWithEmail:failure", task.getException());
                    Toast.makeText(signin_signup.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    //updateUI(null);
                }
            }
        });
    }
}