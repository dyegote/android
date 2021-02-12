package co.com.ceiba.mobile.pruebadeingreso.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.data.Post;
import co.com.ceiba.mobile.pruebadeingreso.data.PostRepository;
import co.com.ceiba.mobile.pruebadeingreso.model.PostModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PostViewModel extends ViewModel
{



    private MutableLiveData<List<PostModel>> postsLiveData;
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();
    PostRepository postRepository = new PostRepository();

    public PostViewModel() {

    }

    /**
     * Obtiene lista de publicaciones por id de usuario
     * @param idUser
     * @return
     */
    public LiveData<List<PostModel>> getPostByUserId(int idUser)
    {
        postsLiveData = new MutableLiveData<>();
        postsLiveData.setValue(postRepository.getPostsByUserId(idUser));

        if(postsLiveData.getValue().size()  == 0)
        {
            loadPostsByUserId(idUser);
        }

        return  postsLiveData;
    }

    /**
     * Guarda publicacines en base de datos local
     * @param postsModel
     */
    private void savePost(List<PostModel> postsModel)
    {
        List<Post> posts = new ArrayList<>();
        for(PostModel item : postsModel)
            posts.add(new Post(item.getId(),item.getUserId(),item.getBody(),item.getTitle()));

        postRepository.savePost(posts);

    }

    /**
     * Carga lista de publicaciones desde REST
     * @param idUser
     */
    public void loadPostsByUserId(int idUser)
    {
        try
        {
            isLoading.setValue(true);

            Callback<List<PostModel>> callback = new Callback<List<PostModel>>() {
                @Override
                public void onResponse(Call<List<PostModel>> call, Response<List<PostModel>> response) {

                    try {
                        List<PostModel> posts = response.body();
                        savePost(posts);
                        postsLiveData.setValue(posts);
                        Thread.sleep(2000);//solo para alcanzar a visualizar ProgressDialog
                        isLoading.setValue(false);
                    }
                    catch (Exception ex)
                    {
                        error.setValue(ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<PostModel>> call, Throwable t) {
                    isLoading.setValue(false);
                    error.setValue(t.getMessage());
                }
            };

            postRepository.loadPostByUserId(callback,idUser);
        }
        catch (Exception ex)
        {
            isLoading.setValue(false);
            error.setValue(ex.getMessage());
        }


    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public MutableLiveData<String> getError() {
        return error;
    }

}
