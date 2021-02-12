package co.com.ceiba.mobile.pruebadeingreso.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import co.com.ceiba.mobile.pruebadeingreso.R;
import co.com.ceiba.mobile.pruebadeingreso.model.UserModel;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>
{

    Context context;
    List<UserModel> userModelList;
    private static ClickListenerButton postButtonClickListener;

    public UserAdapter(Context context) {
        this.context = context;
    }

    public UserAdapter(Context context, List<UserModel> userModelList) {
        this.context = context;
        this.userModelList = userModelList;
    }

    public void setUserModelList(List<UserModel> userModelList) {
        this.userModelList = userModelList;
    }

    public UserModel getUser(int position)
    {
        return this.userModelList.get(position);
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.user_list_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        UserModel userModel = userModelList.get(position);
        holder.nameTextView.setText(userModel.getName());
        holder.emailTextView.setText(userModel.getEmail());
        holder.phoneTextView.setText(userModel.getPhone());

    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }

    public void setOnItemClickListenerPostButton(ClickListenerButton clickListener) {
        this.postButtonClickListener = clickListener;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView nameTextView;
        TextView emailTextView;
        TextView phoneTextView;
        Button viewPostButton;

        public UserViewHolder(View itemView) {
            super(itemView);

            nameTextView = itemView.findViewById(R.id.name);
            emailTextView = itemView.findViewById(R.id.email);
            phoneTextView = itemView.findViewById(R.id.phone);
            viewPostButton = itemView.findViewById(R.id.btn_view_post);
            viewPostButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(v.getId() == viewPostButton.getId())
                postButtonClickListener.onItemClick(getAdapterPosition(), v);

        }


    }

    public interface ClickListenerButton {
        public void onItemClick(int position, View v);
    }
}
