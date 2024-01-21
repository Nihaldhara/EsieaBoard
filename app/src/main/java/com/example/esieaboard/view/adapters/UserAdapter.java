package com.example.esieaboard.view.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.R;
import com.example.esieaboard.database.db.AppDatabase;
import com.example.esieaboard.model.entities.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private final List<User> users = new ArrayList<>();

    public UserAdapter() {
    }

    public void setUsers(List<User> users) {
        UserDiffCallback diffCallback = new UserDiffCallback(this.users, users);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new UserDiffCallback(this.users, users));
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
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {

        TextView userName;
        Button adminButton, memberButton, userButton;
        AppDatabase database;

        public UserViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_name);
            adminButton = itemView.findViewById(R.id.button_admin);
            memberButton = itemView.findViewById(R.id.button_member);
            userButton = itemView.findViewById(R.id.button_user);

            database = AppDatabase.getInstance(itemView.getContext());
        }

        public void bind(User user) {
            userName.setText(user.getFirstName());

            adminButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setRights(2);
                    database.userDAO().update(user);
                }
            });

            memberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setRights(1);
                    database.userDAO().update(user);
                }
            });

            userButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user.setRights(0);
                    database.userDAO().update(user);
                }
            });
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
