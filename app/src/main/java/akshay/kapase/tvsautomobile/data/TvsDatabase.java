package akshay.kapase.tvsautomobile.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import akshay.kapase.tvsautomobile.models.Employee;

@Database(entities = {Employee.class},version = 1)
public abstract class TvsDatabase extends RoomDatabase {
    public abstract TvsDao tvsDao();
}


