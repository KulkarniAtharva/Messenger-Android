package dev.atharvakulkarni.messenger.viewmodel;

import android.content.Context;
import android.view.View;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import dev.atharvakulkarni.messenger.service.model.LoginModel;
import dev.atharvakulkarni.messenger.service.model.UserModel;

public class SignIn_SignUpViewModel extends ViewModel
{
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<String> password = new MutableLiveData<>();

   /* static private MutableLiveData<LoginModel> userMutableLiveData;

    public static MutableLiveData<LoginModel> getUser()
    {
        if (userMutableLiveData == null)
        {
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }*/

    public UserModel userModel;
    private Context context;

    public SignIn_SignUpViewModel(Context context, UserModel userModel)
    {
        this.userModel = userModel;
        this.context = context;
    }

    public void onClick(View view)
    {
        LoginModel loginUser = new LoginModel(username.getValue(), password.getValue());
        //userMutableLiveData.setValue(loginUser);
    }
}