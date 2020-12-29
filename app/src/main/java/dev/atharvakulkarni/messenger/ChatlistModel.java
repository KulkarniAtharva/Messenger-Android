package dev.atharvakulkarni.messenger;

public class ChatlistModel
{
    String username, name,photo, lastmessage, count;

    public ChatlistModel(String username, String name, String photo, String lastmessage, String count)
    {
        this.name = name;
        this.photo = photo;
        this.username = username;
        this.lastmessage = lastmessage;
        this.count = count;
    }
}
