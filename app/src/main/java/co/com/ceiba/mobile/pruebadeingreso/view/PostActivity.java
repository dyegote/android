package co.com.ceiba.mobile.pruebadeingreso.view;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import org.parceler.Parcels;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.PostModel;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;
import co.com.ceiba.mobile.pruebadeingreso.viewmodel.PostViewModel;

public class PostActivity extends AppCompatActivity {

    TextView nameTextView, phoneTextView, emailTextView;
    ProgressDialog progressDialog;
    PostAdapter postAdapter;
    RecyclerView postRecyclerView;
    UserModel user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);

        user = (UserModel) Parcels.unwrap(getIntent().getParcelableExtra(MainActivity.USER_ID));

        configureView();
        loadPost();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    private void configureView()
    {
        nameTextView = findViewById(R.id.name);
        phoneTextView = findViewById(R.id.phone);
        emailTextView = findViewById(R.id.email);

        nameTextView.setText(user.getName());
        phoneTextView.setText(user.getPhone());
        emailTextView.setText(user.getEmail());

        postRecyclerView = findViewById(R.id.recyclerViewPostsResults);
        postRecyclerView.setHasFixedSize(true);
        postRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        postAdapter = new PostAdapter(PostActivity.this);

    }

    private void loadPost(){
        PostViewModel postViewModel = ViewModelProviders.of(this).get(PostViewModel.class);
        progressDialog = Utils.builProgressDialog(this,
                getResources().getString(R.string.progress_posts),
                getResources().getString(R.string.progress_title));

        postViewModel.getIsLoading().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean isLoading) {
                if (isLoading)
                    progressDialog.show();
                else
                    progressDialog.dismiss();
            }
        });

        postViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String error) {
                if (!TextUtils.isEmpty(error))
                    Toast.makeText(getApplicationContext(),error,Toast.LENGTH_LONG).show();

            }
        });

        postViewModel.getPostByUserId(user.getId()).observe(this, new Observer<List<PostModel>>() {
            @Override
            public void onChanged(@Nullable List<PostModel> postList) {

                postAdapter.setPostModelList(postList);
                postRecyclerView.setAdapter(postAdapter);

            }
        });

    }


}
