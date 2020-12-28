package dev.atharvakulkarni.messenger;

public class Messages
{
    private String date, from, text, time;

    public Messages()
    {

    }

    public Messages(String date, String from, String text, String time)
    {
        this.date = date;
        this.from = from;
        this.text = text;
        this.time = time;
    }

   public String getTime()
   {
        return time;
    }

    public void setTime(String time)
    {
        this.time = time;
    }

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }

    public String getFrom()
    {
        return from;
    }

    public void setFrom(String from)
    {
        this.from = from;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}