package co.com.ceiba.mobile.pruebadeingreso.rest;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.model.PostModel;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET(Endpoints.GET_USERS)
    Call<List<UserModel>> getUsers();

    @GET(Endpoints.GET_POST_USER)
    Call<List<PostModel>> getPostFromUserId(@Query("userId") int userId);
}
