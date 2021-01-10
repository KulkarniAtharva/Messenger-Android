package dev.atharvakulkarni.messenger.viewmodel;

import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.atharvakulkarni.messenger.service.model.LoginModel;

public class SignIn_SignUpViewModel extends ViewModel
{
    public MutableLiveData<String> EmailAddress = new MutableLiveData<>();
    public MutableLiveData<String> Password = new MutableLiveData<>();

    static private MutableLiveData<LoginModel> userMutableLiveData;

    public static MutableLiveData<LoginModel> getUser()
    {
        if (userMutableLiveData == null)
        {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public void onClick(View view)
    {
        LoginModel loginUser = new LoginModel(EmailAddress.getValue(), Password.getValue());
        userMutableLiveData.setValue(loginUser);
    }
}