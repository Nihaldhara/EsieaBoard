package com.example.esieaboard.fragments;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.MixedAdapter;
import com.example.esieaboard.R;
import com.example.esieaboard.models.UserModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AdministratorsManageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AdministratorsManageFragment extends Fragment {

    ImageButton backButton;
    RecyclerView usersRecyclerView;
    DataBaseHelper dataBaseHelper;
    ArrayList<UserModel> users;
    MixedAdapter usersAdapter;

    private static final String ARG_USER = "user";

    private UserModel user;

    public AdministratorsManageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user Parameter 1.
     * @return A new instance of fragment AdministratorsManageFragment.
     */
    public static AdministratorsManageFragment newInstance(UserModel user) {
        AdministratorsManageFragment fragment = new AdministratorsManageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserModel) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_administrators_manage, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.back_button1);

        usersRecyclerView = view.findViewById(R.id.users_list);
        dataBaseHelper = new DataBaseHelper(getContext());
        users = new ArrayList<>();
        displayUsers();

        usersAdapter = new MixedAdapter(getContext(), new ArrayList<>(), users, new ArrayList<>(), new ArrayList<>(), user);
        usersRecyclerView.setAdapter(usersAdapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }

    void displayUsers() {
        users.clear();
        Cursor cursor = dataBaseHelper.readUserData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                users.add(new UserModel(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getInt(6)));
            }
        }
    }
}