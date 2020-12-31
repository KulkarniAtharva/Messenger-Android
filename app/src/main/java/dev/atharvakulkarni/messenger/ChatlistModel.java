package dev.atharvakulkarni.messenger;

public class ChatlistModel
{
    String username, name,photo, lastmessage, count, last_time;

    public ChatlistModel(String username, String name, String photo, String lastmessage, String count, String last_time)
    {
        this.name = name;
        this.photo = photo;
        this.username = username;
        this.lastmessage = lastmessage;
        this.count = count;
        this.last_time = last_time;
    }
}