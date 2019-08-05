package akshay.kapase.tvsautomobile.barchart;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.content.Context;
import android.support.annotation.NonNull;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import java.util.ArrayList;
import java.util.List;
import akshay.kapase.tvsautomobile.data.AppDatabase;
import akshay.kapase.tvsautomobile.data.TvsDatabase;
import akshay.kapase.tvsautomobile.models.Employee;

public class BarChartViewModel extends AndroidViewModel {

    TvsDatabase tvsDatabase;
    Context context;

    public BarChartViewModel(@NonNull Application application) {
        super(application);
        context = getApplication().getApplicationContext();
        tvsDatabase = AppDatabase.getAppDatabaseInstance(context);
    }

    public BarData getTopTenEmployees(){
        List<BarEntry> entries = new ArrayList<>();
        float empId = 0f;
        for (Employee employee : tvsDatabase.tvsDao().getFirstTenEmployees()) {
            try{
                String salary = employee.getEmpSalary().replaceAll("[^\\d.]", "");
                Float empSalary = Float.parseFloat(salary);
                entries.add(new BarEntry(empId,empSalary));
                empId++;
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        BarDataSet set = new BarDataSet(entries, "BarDataSet");
        BarData data = new BarData(set);
        data.setBarWidth(0.9f); // set custom bar width
        return data;
    }

}
