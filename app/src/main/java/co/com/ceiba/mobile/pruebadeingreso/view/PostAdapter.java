package co.com.ceiba.mobile.pruebadeingreso.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.PostModel;


public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>
{

    Context context;
    List<PostModel> postModelList;

    public PostAdapter(Context context) {
        this.context = context;
    }

    public PostAdapter(Context context, List<PostModel> postModelList) {
        this.context = context;
        this.postModelList = postModelList;
    }

    public void setPostModelList(List<PostModel> postModelList) {
        this.postModelList = postModelList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.post_list_item, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {

        PostModel userModel = postModelList.get(position);
        holder.titleTextView.setText(userModel.getTitle());
        holder.bodyTextView.setText(userModel.getBody());

    }

    @Override
    public int getItemCount() {
        return postModelList.size();
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        TextView titleTextView;
        TextView bodyTextView;


        public PostViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.title);
            bodyTextView = itemView.findViewById(R.id.body);

        }


    }


}
