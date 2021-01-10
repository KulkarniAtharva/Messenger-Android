package dev.atharvakulkarni.messenger.service.model;

import android.util.Patterns;

public class LoginMode
{
    private String strEmailAddress;
    private String strPassword;

    public LoginMode(String EmailAddress, String Password)
    {
        strEmailAddress = EmailAddress;
        strPassword = Password;
    }

    public String getStrEmailAddress()
    {
        return strEmailAddress;
    }

    public String getStrPassword()
    {
        return strPassword;
    }

    public boolean isEmailValid()
    {
        return Patterns.EMAIL_ADDRESS.matcher(getStrEmailAddress()).matches();
    }

    public boolean isPasswordLengthGreaterThan5()
    {
        return getStrPassword().length() > 5;
    }
}