package co.com.ceiba.mobile.pruebadeingreso;

import android.content.Context;
import android.support.test.InstrumentationRegistry;


import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.core.IsEqual;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import co.com.ceiba.mobile.pruebadeingreso.data.ApplicationDB;
import co.com.ceiba.mobile.pruebadeingreso.data.Post;
import co.com.ceiba.mobile.pruebadeingreso.data.PostDao;
import co.com.ceiba.mobile.pruebadeingreso.data.User;
import co.com.ceiba.mobile.pruebadeingreso.data.UserDao;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;



@RunWith(AndroidJUnit4.class)
public class DataBaseTest {

    private ApplicationDB db;
    UserDao userDao;
    PostDao postDao;



    @Before

    public void createDb() {
        try
        {
            Context context = InstrumentationRegistry.getInstrumentation().getContext();

            db = Room.inMemoryDatabaseBuilder(context, ApplicationDB.class).build();
            userDao = db.userDao();
            postDao = db.postDao();
        }
        catch (Exception ex)
        {
            Assert.fail(ex.getMessage());
        }

    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeReadUser() throws Exception {
        User user = new User(1,"diego","diego","dyegote@hotmail.com","315001002");
        userDao.insert(user);
        User userById = userDao.getById(1);
        Assert.assertEquals(user.getId(), userById.getId());
        Assert.assertEquals(user.getName(), userById.getName());
        Assert.assertEquals(user.getUsername(), userById.getUsername());
        Assert.assertEquals(user.getEmail(), userById.getEmail());
        Assert.assertEquals(user.getPhone(), userById.getPhone());

    }

    @Test
    public void writeReadPost() throws Exception {
        User user = new User(1,"diego","diego","dyegote@hotmail.com","315001002");
        Post post = new Post(1,user.getId(),"body","tittle");
        userDao.insert(user);
        postDao.insert(post);
        Post postById = postDao.getById(1);
        Assert.assertEquals(post.getId(), postById.getId());
        Assert.assertEquals(post.getUserId(), postById.getUserId());
        Assert.assertEquals(post.getBody(), postById.getBody());
        Assert.assertEquals(post.getTitle(), postById.getTitle());


    }
}
