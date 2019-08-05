package akshay.kapase.tvsautomobile.EmpDetail;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

public class EmpDetailViewModelFactory implements ViewModelProvider.Factory {

    private Application mApplication;
    private String mParam;

    public EmpDetailViewModelFactory(Application application, String param) {
        mApplication = application;
        mParam = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EmpDetailViewModel(mApplication, mParam);
    }


}
