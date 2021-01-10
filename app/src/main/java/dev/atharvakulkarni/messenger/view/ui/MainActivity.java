package dev.atharvakulkarni.messenger.view.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import dev.atharvakulkarni.messenger.R;
import dev.atharvakulkarni.messenger.service.model.UserModel;

public class MainActivity extends AppCompatActivity
{
    Fragment fragment = null;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setStatusBarColor(getResources().getColor(R.color.blue,getTheme()));

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Messenger");
        userModel = new UserModel();

        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch (item.getItemId())
                {
                    case R.id.chats:

                        fragment = new Chats();
                        switchfragment(fragment);

                        return true;

                    case R.id.story:

                      //  fragment = new History();
                       // switchfragment(fragment);

                        break;
                    case R.id.friends:

                          fragment = new Friends();
                          switchfragment(fragment);

                        break;
                    case R.id.person:

                       // fragment = new Add_students();
                       // switchfragment(fragment);

                        break;
                }
                return true;
            }
        });


        if (savedInstanceState == null)
        {
            bottomNavigationView.setSelectedItemId(R.id.chats); // change to whichever id should be default
        }
    }

    void switchfragment(Fragment fragment)
    {
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.fragment, fragment).commit();
    }
}

/*

    app:defaultNavHost="true"
    app:navGraph="@navigation/mobile_navigation"

 */