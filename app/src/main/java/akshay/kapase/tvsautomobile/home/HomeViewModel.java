package akshay.kapase.tvsautomobile.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;

import java.util.ArrayList;

import akshay.kapase.tvsautomobile.EmpDetail.EmpDetailActivity;
import akshay.kapase.tvsautomobile.barchart.BarChartActivity;
import akshay.kapase.tvsautomobile.data.AppDatabase;
import akshay.kapase.tvsautomobile.data.TvsDatabase;
import akshay.kapase.tvsautomobile.models.Employee;

public class HomeViewModel extends AndroidViewModel {

    MutableLiveData<ArrayList<Employee>> employeeList;
    TvsDatabase tvsDatabase;
    Context context;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        tvsDatabase = AppDatabase.getAppDatabaseInstance(context);
        employeeList = new MutableLiveData<>();
        employeeList.postValue((ArrayList)tvsDatabase.tvsDao().getAllEmployees());
    }

    public MutableLiveData<ArrayList<Employee>> getAllEmployees() {
       return employeeList;
    }

    public void onRowItemClicked(Employee employee) {
        Intent i = new Intent(context, EmpDetailActivity.class);
        i.putExtra("empId",employee.getEmpId());
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public void filterEmployees(String searchQuery) {
        employeeList.postValue((ArrayList)tvsDatabase.tvsDao().getEmpListBySearchQuery("%"+searchQuery+"%"));
    }

    public void showBarChart() {
        Intent i = new Intent(context, BarChartActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

}
