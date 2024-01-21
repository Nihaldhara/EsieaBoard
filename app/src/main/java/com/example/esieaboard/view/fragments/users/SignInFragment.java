package com.example.esieaboard.view.fragments.users;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.esieaboard.R;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.view.fragments.EsieaBoardFragment;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignInFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignInFragment extends Fragment {

    EditText inputEmail, inputPassword;
    Button buttonConfirm, buttonLogIn;

    UserViewModel userViewModel;

    public SignInFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SignInFragment.
     */
    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputEmail = view.findViewById(R.id.email_address_input);
        inputPassword = view.findViewById(R.id.password_input);

        buttonConfirm = view.findViewById(R.id.confirm_button);
        buttonLogIn = view.findViewById(R.id.log_in_button);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = inputEmail.getText().toString().trim();

                userViewModel.get(email).observe(getViewLifecycleOwner(), user -> {
                    if (user == null) {
                        User newUser = new User("User", "",
                                inputEmail.getText().toString().trim(), inputPassword.getText().toString().trim(), "", 0);
                        userViewModel.insert(newUser);
                        Toast.makeText(getActivity(), "Successfully created account!", Toast.LENGTH_SHORT).show();
                        inputEmail.setText("");
                        inputPassword.setText("");
                        FragmentManager fragmentManager = getParentFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.fragment_container_view, LogInFragment.newInstance());
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                    } else {
                        inputEmail.setText("");
                        inputPassword.setText("");
                        Toast.makeText(getContext(), "This user already exists", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, LogInFragment.newInstance());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
    }
}