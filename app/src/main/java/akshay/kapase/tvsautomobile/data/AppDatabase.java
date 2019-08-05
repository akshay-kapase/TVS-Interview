package akshay.kapase.tvsautomobile.data;

import android.arch.persistence.room.Room;
import android.content.Context;

public class AppDatabase {

    static TvsDatabase INSTANCE;

    public static TvsDatabase getAppDatabaseInstance(Context context){
        if(INSTANCE == null){
            return Room.databaseBuilder(context,TvsDatabase.class,"tvs_database").allowMainThreadQueries().build();
        } else{
            return INSTANCE;
        }
    }

}
