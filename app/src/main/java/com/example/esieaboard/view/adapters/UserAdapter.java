package com.example.esieaboard.view.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.R;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    UserViewModel userViewModel;

    private final List<User> users = new ArrayList<>();

    public UserAdapter(UserViewModel userViewModel) {
        this.userViewModel = userViewModel;
    }

    public void setUsers(List<User> users) {
        UserDiffCallback diffCallback = new UserDiffCallback(this.users, users);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);
        this.users.clear();
        this.users.addAll(users);
        diffResult.dispatchUpdatesTo(this);
    }

    @NonNull
    @NotNull
    @Override
    public UserAdapter.UserViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_row, parent, false);
        return new UserAdapter.UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull UserAdapter.UserViewHolder holder, int position) {
        User user = users.get(position);
        holder.bind(user, userViewModel);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        Button adminButton, memberButton, userButton;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            adminButton = itemView.findViewById(R.id.button_admin);
            memberButton = itemView.findViewById(R.id.button_member);
            userButton = itemView.findViewById(R.id.button_user);
        }

        public void bind(User user, UserViewModel userViewModel) {
            userName.setText(user.getFirstName());

            if (user.getRights() == 2) {
                adminButton.setBackgroundColor(Color.parseColor("#2DAAE1"));
                memberButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
                userButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
            } else if (user.getRights() == 1) {
                adminButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
                memberButton.setBackgroundColor(Color.parseColor("#2DAAE1"));
                userButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
            } else {
                adminButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
                memberButton.setBackgroundColor(Color.parseColor("#7BC9EB"));
                userButton.setBackgroundColor(Color.parseColor("#2DAAE1"));
            }

            adminButton.setOnClickListener(view -> userViewModel.updateUserRights(user, 2));
            memberButton.setOnClickListener(view -> userViewModel.updateUserRights(user, 1));
            userButton.setOnClickListener(view -> userViewModel.updateUserRights(user, 0));
        }
    }

    public class UserDiffCallback extends DiffUtil.Callback {

        private final List<User> oldList;
        private final List<User> newList;

        public UserDiffCallback(List<User> oldList, List<User> newList) {
            this.oldList = oldList;
            this.newList = newList;
        }

        @Override
        public int getOldListSize() {
            return oldList.size();
        }

        @Override
        public int getNewListSize() {
            return newList.size();
        }

        @Override
        public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).getId() == newList.get(newItemPosition).getId();
        }

        @Override
        public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
            return oldList.get(oldItemPosition).equals(newList.get(newItemPosition));
        }
    }
}
