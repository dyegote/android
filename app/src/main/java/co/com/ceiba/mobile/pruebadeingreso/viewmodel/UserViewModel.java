 package co.com.ceiba.mobile.pruebadeingreso.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.data.User;
import co.com.ceiba.mobile.pruebadeingreso.data.UserRepository;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserViewModel extends ViewModel
{

    public MutableLiveData<List<UserModel>> usersLiveData = new MutableLiveData<>();
    MutableLiveData<Boolean> isLoading = new MutableLiveData<>();
    MutableLiveData<String> error = new MutableLiveData<>();
    public MutableLiveData<String> filterName = new MutableLiveData<>();
    UserRepository userRepository = new UserRepository();

    public UserViewModel() {

    }

    /**
     * Busca lista de usuarios en base de datos local, si la lista esta vacia ls carga desde REST
     * @param filterName
     * @return
     */
    public LiveData<List<UserModel>> getUsers(String filterName)
    {

        if(TextUtils.isEmpty(filterName))
        {

            usersLiveData.setValue(userRepository.getUsers());

            if(usersLiveData.getValue().size()  == 0)
            {
                loadUsers();
            }

            return  usersLiveData;
        }
        else
        {
            usersLiveData.setValue(userRepository.searchByName(filterName));
            return  usersLiveData;
        }

    }


    /**
     * Guarda usuarios en base de datos local
     * @param usersModel
     */
    private void saveUser(List<UserModel> usersModel)
    {
        List<User> users = new ArrayList<>();
        for(UserModel item : usersModel)
            users.add(new User(item.getId(),item.getName(),item.getUsername(),item.getEmail(),item.getPhone()));

        userRepository.saveUser(users);

    }

    /**
     * Carga lista de usuarios desde REST
     */
    public void loadUsers()
    {
        try
        {
            isLoading.setValue(true);

            Callback<List<UserModel>> callback = new Callback<List<UserModel>>() {
                @Override
                public void onResponse(Call<List<UserModel>> call, Response<List<UserModel>> response) {

                    try {
                        List<UserModel> userModelList = response.body();
                        saveUser(userModelList);
                        usersLiveData.setValue(userModelList);
                        Thread.sleep(2000);//solo para alcanzar a visualizar ProgressDialog
                        isLoading.setValue(false);
                    }
                    catch (Exception ex)
                    {
                        isLoading.setValue(false);
                        error.setValue(ex.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<List<UserModel>> call, Throwable t) {
                    isLoading.setValue(false);
                    error.setValue(t.getMessage());
                }
            };

            userRepository.loadUsers(callback);
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
