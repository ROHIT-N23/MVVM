package com.learning.mvvm.ViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewModelFactory extends ViewModelProvider.NewInstanceFactory{

    private Application application;

    // Pushing Application To ViewModel (Because Further We will Need It To Pass It To a object)//
    public NoteViewModelFactory(Application application) {
        this.application = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == NoteViewModel.class){
            return (T) new NoteViewModel(application);
        }
        return null;
    }
}
