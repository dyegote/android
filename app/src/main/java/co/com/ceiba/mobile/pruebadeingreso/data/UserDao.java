package co.com.ceiba.mobile.pruebadeingreso.data;

import android.arch.lifecycle.LiveData;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {

    @Query("SELECT * FROM User")
    List<User> getUsers();

    @Query("SELECT * FROM User WHERE id = :id")
    User getById(int id);

    @Query("SELECT * FROM User WHERE name like :name || '%'")
    List<User> searchByName(String name);

    @Update
    void update(User user);

    @Insert
    void insert(User user);

    @Insert
    void insertAll(List<User> users);
}
