package dev.atharvakulkarni.messenger;

public class Messages1 {
    private String sent_by, sent_date, sent_time, text;

    public Messages1()
    {

    }

    public Messages1(String sent_by, String sent_date, String sent_time, String text)
    {
        this.sent_by = sent_by;
        this.sent_date = sent_date;
        this.sent_time = sent_time;
        this.text = text;
    }

    public String getSent_by() {
        return sent_by;
    }

    public void setSent_date(String sent_date) {
        this.sent_date = sent_date;
    }

    public String getSent_time() {
        return sent_time;
    }

    public void setSent_time(String sent_time) {
        this.sent_time = sent_time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}