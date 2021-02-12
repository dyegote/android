package co.com.ceiba.mobile.pruebadeingreso.data;


import android.arch.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.App;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;
import co.com.ceiba.mobile.pruebadeingreso.rest.Api;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserRepository {

    UserDao userDao;
    private final MutableLiveData<List<UserModel>> userList = new MutableLiveData<>();

    public UserRepository() {
        userDao = App.dataBase.userDao();
    }

    public List<UserModel> getUsers()
    {
        List<UserModel> users = new ArrayList<>();

        for(User item : userDao.getUsers())
            users.add(new UserModel(item.id,item.name,item.username,item.email, item.phone));

        return  users;
    }

    public void saveUser(List<User> users)
    {
        userDao.insertAll(users);
    }

    public List<UserModel> searchByName(String name)
    {
        List<UserModel> users = new ArrayList<>();

        for(User item : userDao.searchByName(name))
            users.add(new UserModel(item.id,item.name,item.username,item.email, item.phone));

        return  users;
    }

    public void loadUsers(Callback<List<UserModel>> callback)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoints.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<UserModel>> call = api.getUsers();
        call.enqueue(callback);

    }


}
