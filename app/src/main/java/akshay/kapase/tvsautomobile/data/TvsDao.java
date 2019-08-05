package akshay.kapase.tvsautomobile.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import java.util.ArrayList;
import java.util.List;

import akshay.kapase.tvsautomobile.models.Employee;

@Dao
public interface TvsDao {

    @Query("SELECT * FROM EMPLOYEE")
    List<Employee> getAllEmployees();

    @Query("SELECT * FROM EMPLOYEE LIMIT 10")
    List<Employee> getFirstTenEmployees();

    @Query("SELECT COUNT(*) FROM EMPLOYEE LIMIT 1")
    int doesEmployeesAlreadyInserted();

    @Insert
    void insertAllEmployees(ArrayList<Employee> employee);

    @Query("SELECT * FROM EMPLOYEE WHERE empId = :empId ")
    Employee getEmpDetail(String empId);

    @Query("SELECT * FROM EMPLOYEE WHERE empName LIKE :searchQuery")
    List<Employee> getEmpListBySearchQuery(String searchQuery);
}
