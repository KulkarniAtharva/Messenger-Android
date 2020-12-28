package dev.atharvakulkarni.messenger;

import android.widget.Toast;

public class UserModel
{
    private static String Username, name,photo;

    public String getName()
    {
        return name;
    }

    public  void setName(String name)
    {
        this.name = name;
    }

    public String getPhoto()
    {
        return photo;
    }

    public void setPhoto(String photo)
    {
        this.photo = photo;
    }

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