package co.com.ceiba.mobile.pruebadeingreso.data;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.App;
import co.com.ceiba.mobile.pruebadeingreso.model.PostModel;
import co.com.ceiba.mobile.pruebadeingreso.rest.Api;
import co.com.ceiba.mobile.pruebadeingreso.rest.Endpoints;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PostRepository {

    PostDao postDao;

    public PostRepository() {
        postDao = App.dataBase.postDao();
    }


    public List<PostModel> getPostsByUserId(int userId)
    {
        List<PostModel> posts = new ArrayList<>();

        for(Post item : postDao.getByUserId(userId))
            posts.add(new PostModel(item.id,item.userId,item.body,item.title));

        return  posts;

    }

    public void savePost(List<Post> posts)
    {
        postDao.insertAll(posts);
    }

    public void loadPostByUserId(Callback<List<PostModel>> callback, int userId)
    {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Endpoints.URL_BASE)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Call<List<PostModel>> call = api.getPostFromUserId(userId);
        call.enqueue(callback);

    }


}
