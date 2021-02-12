package co.com.ceiba.mobile.pruebadeingreso.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PostDao {

    @Query("SELECT * FROM Post")
    List<Post> getPosts();

    @Query("SELECT * FROM Post WHERE id = :id")
    Post getById(int id);

    @Query("SELECT * FROM Post WHERE userId = :userId")
    List<Post> getByUserId(int userId);

    @Update
    void update(Post user);

    @Insert
    void insert(Post user);

    @Insert
    void insertAll(List<Post> posts);
}
