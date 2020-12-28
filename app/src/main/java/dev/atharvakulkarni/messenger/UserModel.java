package dev.atharvakulkarni.messenger;

import android.widget.Toast;

public class UserModel
{
    private static String Username;

    public UserModel()
    {
    }

    public String getUsername()
    {
        return Username;
    }

    public void setUsername(String username)
    {
        Username = username;
    }
}