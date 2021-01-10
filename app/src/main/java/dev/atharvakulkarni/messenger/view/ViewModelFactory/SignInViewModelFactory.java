package dev.atharvakulkarni.messenger.view.ViewModelFactory;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import dev.atharvakulkarni.messenger.service.model.UserModel;
import dev.atharvakulkarni.messenger.viewmodel.SignIn_SignUpViewModel;

public class SignInViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
    UserModel userModel;
    Context context;

    public SignInViewModelFactory(Context context, UserModel userModel)
    {
        this.userModel = userModel;
        this.context = context;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass)
    {
        return (T)new SignIn_SignUpViewModel(context,userModel);
    }
}
