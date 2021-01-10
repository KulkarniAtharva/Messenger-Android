package dev.atharvakulkarni.messenger.viewmodel;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dev.atharvakulkarni.messenger.service.model.UserModel;

public class SignInViewModel extends ViewModel
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

    public SignInViewModel(Context context, UserModel userModel)
    {
        this.userModel = userModel;
        this.context = context;
    }

    public void onClick(View view)
    {
      //  UserModel loginUser = new UserModel(username.getValue(), password.getValue());
        //userMutableLiveData.setValue(loginUser);

        UserModel.setUsername(username.getValue());
        UserModel.setPassword(password.getValue());

        Toast.makeText(context, UserModel.getUsername()+" "+UserModel.getPassword(), Toast.LENGTH_SHORT).show();
    }
}