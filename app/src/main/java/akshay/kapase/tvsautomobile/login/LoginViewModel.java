package akshay.kapase.tvsautomobile.login;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import android.util.Log;
import android.widget.Toast;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;

import akshay.kapase.tvsautomobile.Services.EmployeeService;
import akshay.kapase.tvsautomobile.api.TvsApi;
import akshay.kapase.tvsautomobile.data.AppDatabase;
import akshay.kapase.tvsautomobile.data.TvsDatabase;
import akshay.kapase.tvsautomobile.home.HomeActivity;
import akshay.kapase.tvsautomobile.models.Admin;
import akshay.kapase.tvsautomobile.utils.RetrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static akshay.kapase.tvsautomobile.utils.Constants.ACTION_STORE_EMPLOYEES;
import static akshay.kapase.tvsautomobile.utils.Constants.EMPLOYEE_DATA;
import static akshay.kapase.tvsautomobile.utils.Constants.EMPLOYEE_SERVICE_JOB_ID;

public class LoginViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoginSuccessful;
    TvsDatabase tvsDatabase;
    Context context;

    public LoginViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        tvsDatabase = AppDatabase.getAppDatabaseInstance(getApplication().getApplicationContext());
        this.isLoginSuccessful = new MutableLiveData<>();
    }

    public MutableLiveData<Boolean> isLoginSuccessful() {
        return isLoginSuccessful;
    }

    public void loginUser(String userName, String userPassword) {
        Log.d("loginstart - ",userName);
        TvsApi tvsApi = RetrofitInstance.getRetrofitInstance().create(TvsApi.class);
        Admin admin = new Admin();
        admin.setUsername(userName);
        admin.setPassword(userPassword);
        Call<JsonObject> loginCall = tvsApi.loginUser(admin);
        loginCall.enqueue(new LoginCallback());
    }

    public void navigateToNextScreen() {
        Intent i = new Intent(getApplication().getApplicationContext(), HomeActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    //handle login response
    private class LoginCallback implements Callback<JsonObject>{
        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            if(response.body() != null){
                Log.d("remote-result-1 - ",response.body().toString());
                if(tvsDatabase.tvsDao().doesEmployeesAlreadyInserted() < 1){
                    Intent intent = new Intent();
                    intent.setAction(ACTION_STORE_EMPLOYEES);
                    intent.putExtra(EMPLOYEE_DATA,response.body().toString());
                    JobIntentService.enqueueWork(context, EmployeeService.class, EMPLOYEE_SERVICE_JOB_ID, intent);
                    isLoginSuccessful.postValue(true);
                } else{
                    isLoginSuccessful.postValue(true);
                }
            } else{
                isLoginSuccessful.postValue(false);
                Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show();
            }
        }
        @Override
        public void onFailure(Call<JsonObject> call, Throwable t) {
            Log.d("remoteresult - ",t.getMessage());
        }
    }

}
