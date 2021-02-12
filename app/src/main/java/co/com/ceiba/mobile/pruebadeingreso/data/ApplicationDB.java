package co.com.ceiba.mobile.pruebadeingreso.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(
        entities = {User.class, Post.class}, version = 2, exportSchema = false
)
public abstract class ApplicationDB extends RoomDatabase {

    private static ApplicationDB instance = null;

    public abstract UserDao userDao();
    public abstract PostDao postDao();

    public static ApplicationDB getInstance(Context context)
    {
        return instance != null ? instance : buildDataBase(context);
    }

    private static ApplicationDB buildDataBase(Context context)
    {
        return Room.databaseBuilder(context, ApplicationDB.class,"ApplicationDB")
                .allowMainThreadQueries()
                .build();
    }

}
