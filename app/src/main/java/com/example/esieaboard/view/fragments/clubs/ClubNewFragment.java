package com.example.esieaboard.view.fragments.clubs;

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
import com.example.esieaboard.model.entities.Club;
import com.example.esieaboard.viewmodel.ClubViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubNewFragment extends Fragment {

    EditText inputName, inputEmail, inputDescription;
    Button buttonCancel, buttonConfirm;

    Club club;
    ClubViewModel clubViewModel;


    public ClubNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ClubNewFragment.
     */
    public static ClubNewFragment newInstance() {
        ClubNewFragment fragment = new ClubNewFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputName = view.findViewById(R.id.name_input);
        inputEmail = view.findViewById(R.id.email_address_input);
        inputDescription = view.findViewById(R.id.description_input);

        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonConfirm = view.findViewById(R.id.button_confirm);

        clubViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(ClubViewModel.class);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(inputName.getText().toString().isEmpty()){
                    inputName.setError("Name is required");
                    return;
                }

                if(inputEmail.getText().toString().isEmpty()){
                    inputEmail.setError("Email is required");
                    return;
                }

                if(inputDescription.getText().toString().isEmpty()){
                    inputDescription.setError("Description is required");
                    return;
                }

                club = new Club(inputName.getText().toString(), inputEmail.getText().toString().trim(),
                        inputDescription.getText().toString());
                clubViewModel.insert(club);

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }
}