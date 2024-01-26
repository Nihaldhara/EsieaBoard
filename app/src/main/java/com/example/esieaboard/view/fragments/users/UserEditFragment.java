package com.example.esieaboard.view.fragments.users;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.example.esieaboard.R;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserEditFragment extends Fragment {

    Button cancelButton, confirmButton;
    EditText nameInput, emailInput, descriptionInput, passwordInput;

    UserViewModel userViewModel;

    private static final String ARG_USER = "user";

    private User u;

    public UserEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param u Parameter 1.
     * @return A new instance of fragment UserEditFragment.
     */
    public static UserEditFragment newInstance(User u) {
        UserEditFragment fragment = new UserEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, u);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            u = (User) getArguments().getSerializable(ARG_USER);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelButton = view.findViewById(R.id.button_cancel);
        confirmButton = view.findViewById(R.id.button_confirm);

        nameInput = view.findViewById(R.id.name_input);
        emailInput = view.findViewById(R.id.email_address_input);
        descriptionInput = view.findViewById(R.id.description_input);
        passwordInput = view.findViewById(R.id.password_input);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);
        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            nameInput.setText(user.getFirstName());
            emailInput.setText(user.getEmailAddress());
            passwordInput.setText(user.getPassword());
            descriptionInput.setText(user.getDescription());

            confirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(emailInput.getText().toString().isEmpty()) {
                            emailInput.setError("Email address is required");
                            return;
                        }

                        if(passwordInput.getText().toString().isEmpty()) {
                            passwordInput.setError("Password is required");
                            return;
                        }

                        user.setFirstName(nameInput.getText().toString().trim());
                        user.setEmailAddress(emailInput.getText().toString().trim());
                        user.setPassword(passwordInput.getText().toString().trim());
                        user.setDescription(descriptionInput.getText().toString().trim());
                        userViewModel.update(user);

                        Intent resultIntent = new Intent();
                        resultIntent.putExtra("user", user);

                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.popBackStack();
                    } catch (Exception e) {
                        e.printStackTrace();
                        nameInput.setText(user.getFirstName());
                        emailInput.setText(user.getEmailAddress());
                        passwordInput.setText(user.getPassword());
                        descriptionInput.setText(user.getDescription());
                    }
                }
            });
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }
}