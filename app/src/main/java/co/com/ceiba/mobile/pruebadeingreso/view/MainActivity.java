package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;


import org.parceler.Parcels;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.UserViewModel;

public class MainActivity extends AppCompatActivity {

    public static final String USER_ID = "USER_ID";

    EditText personNameEditTex;
    ProgressDialog progressDialog;
    RecyclerView userRecyclerView;
    UserAdapter userAdapter;
    UserViewModel userViewModel;
    View emptyLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        configureView();
        loadUsers();
    }

    private void configureView()
    {
        LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
        emptyLayout = inflater.inflate(R.layout.empty_view, null);
        RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams( RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
        layoutparams.addRule(RelativeLayout.CENTER_IN_PARENT);
        emptyLayout.setLayoutParams(layoutparams);

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        personNameEditTex = findViewById(R.id.editTextSearch);
        userRecyclerView = findViewById(R.id.recyclerViewSearchResults);
        userRecyclerView.setHasFixedSize(true);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userAdapter = new UserAdapter(MainActivity.this);
        userAdapter.setOnItemClickListenerPostButton(new UserAdapter.ClickListenerButton() {
            @Override
            public void onItemClick(int position, View v) {

                UserModel user = userAdapter.getUser(position);
                Intent intent = new Intent(getApplicationContext(), PostActivity.class);
                intent.putExtra(USER_ID, Parcels.wrap(user));
                startActivity(intent);
            }
        });

        personNameEditTex.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {
                userViewModel.getUsers(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });

        userViewModel.usersLiveData.observe(this, new Observer<List<UserModel>>() {
            @Override
            public void onChanged(@Nullable List<UserModel> userList) {
                if(userList.size() == 0)
                {
                    Utils.replaceView(userRecyclerView,emptyLayout);
                }
                else
                {
                    Utils.replaceView(emptyLayout,userRecyclerView);
                    userAdapter.setUserModelList(userList);
                    userRecyclerView.setAdapter(userAdapter);
                }

            }
        });

    }

    private void loadUsers(){

        progressDialog = Utils.builProgressDialog(this,
                getResources().getString(R.string.progress_users),
                getResources().getString(R.string.progress_title));

        userViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading)
                    progressDialog.show();
                else
                    progressDialog.dismiss();
            }
        });

        userViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (!TextUtils.isEmpty(error))
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

            }
        });

        userViewModel.getUsers("");

    }





    @Override
    protected void onStart() {
        super.onStart();
    }


}