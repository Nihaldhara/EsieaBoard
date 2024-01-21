package com.example.esieaboard.view.fragments.users;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.example.esieaboard.view.fragments.administrators.AdministratorsManageFragment;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

import static android.view.View.GONE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link UserProfileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserProfileFragment extends Fragment {

    ImageButton backButton;
    Button logOutButton, modifyButton, manageRightsButton;
    ImageView imageViewProfile;
    TextView nameText, emailText, descriptionText;

    UserViewModel userViewModel;

    private static final String ARG_USER = "user";

    private User u;

    public UserProfileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param u Parameter 1.
     * @return A new instance of fragment UserProfileFragment.
     */
    public static UserProfileFragment newInstance(User u) {
        UserProfileFragment fragment = new UserProfileFragment();
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
        return inflater.inflate(R.layout.fragment_user_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.back_button);
        logOutButton = view.findViewById(R.id.log_out_button);
        modifyButton = view.findViewById(R.id.modify_button);
        manageRightsButton = view.findViewById(R.id.manage_rights_button);
        imageViewProfile = view.findViewById(R.id.logo_image);
        nameText = view.findViewById(R.id.user_name);
        emailText = view.findViewById(R.id.user_email_address);
        descriptionText = view.findViewById(R.id.user_description);

        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);
        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            nameText.setText(user.getFirstName() + " " + user.getLastName());
            emailText.setText(user.getEmailAddress());
            descriptionText.setText(user.getDescription());

            if(user.getRights() < 2) { manageRightsButton.setVisibility(GONE); }
        });

        manageRightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, AdministratorsManageFragment.newInstance(user));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });
            }
        });

        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, LogInFragment.newInstance());
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        modifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, UserEditFragment.newInstance(user));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });
            }
        });
    }
}