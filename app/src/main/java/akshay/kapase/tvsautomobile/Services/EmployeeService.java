package akshay.kapase.tvsautomobile.Services;

import android.arch.persistence.room.Update;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import java.util.ArrayList;
import java.util.List;
import akshay.kapase.tvsautomobile.data.AppDatabase;
import akshay.kapase.tvsautomobile.data.TvsDatabase;
import akshay.kapase.tvsautomobile.models.Employee;

import static akshay.kapase.tvsautomobile.utils.Constants.ACTION_STORE_EMPLOYEES;
import static akshay.kapase.tvsautomobile.utils.Constants.EMPLOYEE_DATA;
import static akshay.kapase.tvsautomobile.utils.Constants.EMPLOYEE_SERVICE_JOB_ID;

public class EmployeeService extends JobIntentService {

    static TvsDatabase tvsDatabase;
    Context context;

    public EmployeeService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = EmployeeService.this;
        tvsDatabase = AppDatabase.getAppDatabaseInstance(context);
    }

    static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, Update.class, EMPLOYEE_SERVICE_JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        if(intent.getAction() != null){
            if(intent.getAction().equals(ACTION_STORE_EMPLOYEES)){
                String data = intent.getStringExtra(EMPLOYEE_DATA);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject)jsonParser.parse(data);
                JsonPrimitive sample1 = jsonObject.getAsJsonPrimitive("TABLE_DATA");
                String sample2 = sample1.toString().replaceAll("^\"|\"$", "");
                String sample3 = sample2.replace("\\", "");
                JsonObject jsonObject1 = (JsonObject)jsonParser.parse(sample3);
                JsonArray jsonElements = jsonObject1.getAsJsonArray("data");
                Gson googleJson = new Gson();
                ArrayList jsonObjList = googleJson.fromJson(jsonElements, ArrayList.class);
                ArrayList<Employee> employees = new ArrayList<>();
                for (int i = 0, size = jsonObjList.size(); i < size; i++)
                {
                    List<String> empDetails = (ArrayList)jsonObjList.get(i);
                    Employee employee = new Employee();
                    employee.setEmpName(empDetails.get(0));
                    employee.setEmpRole(empDetails.get(1));
                    employee.setEmpAddress(empDetails.get(2));
                    employee.setEmpId(empDetails.get(3));
                    employee.setEmpJoinDate(empDetails.get(4));
                    employee.setEmpSalary(empDetails.get(5));
                    employees.add(employee);
                }
                tvsDatabase.tvsDao().insertAllEmployees(employees);
            }
        }
    }

}
