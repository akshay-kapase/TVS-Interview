package akshay.kapase.tvsautomobile.EmpDetail;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.support.annotation.NonNull;

import akshay.kapase.tvsautomobile.data.AppDatabase;
import akshay.kapase.tvsautomobile.data.TvsDatabase;
import akshay.kapase.tvsautomobile.models.Employee;

public class EmpDetailViewModel extends AndroidViewModel {

    MutableLiveData<Employee> employeeDetail;
    TvsDatabase tvsDatabase;
    Context context;

    public EmpDetailViewModel(@NonNull Application application, String empId) {
        super(application);
        context = getApplication().getApplicationContext();
        tvsDatabase = AppDatabase.getAppDatabaseInstance(context);
        employeeDetail = new MutableLiveData<>();
        String EID = empId;
        Employee employee = tvsDatabase.tvsDao().getEmpDetail(empId);
        employeeDetail.postValue(employee);
    }

    public MutableLiveData<Employee> getEmployeeDetail(){
        return employeeDetail;
    }

}
