package co.com.ceiba.mobile.pruebadeingreso;

import android.app.Application;

import androidx.room.Room;

import co.com.ceiba.mobile.pruebadeingreso.data.ApplicationDB;

public class App extends Application {

    public static ApplicationDB dataBase;
    @Override
    public void onCreate() {
        super.onCreate();
        dataBase = Room.databaseBuilder(this, ApplicationDB.class,"ApplicationDB")
                .allowMainThreadQueries()
                .build();


    }
}
