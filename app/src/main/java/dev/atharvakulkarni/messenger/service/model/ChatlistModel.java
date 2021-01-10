package dev.atharvakulkarni.messenger.service.model;

public class ChatlistModel
{
    public String username, name,photo, lastmessage, count, last_time;

    public ChatlistModel(String username, String name, String photo, String lastmessage, String count, String last_time)
    {
        this.name = name;
        this.photo = photo;
        this.username = username;
        this.lastmessage = lastmessage;
        this.count = count;
        this.last_time = last_time;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getLastmessage() {
        return lastmessage;
    }

    public void setLastmessage(String lastmessage) {
        this.lastmessage = lastmessage;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getLast_time() {
        return last_time;
    }

    public void setLast_time(String last_time) {
        this.last_time = last_time;
    }
}