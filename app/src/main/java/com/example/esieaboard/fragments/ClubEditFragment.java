package com.example.esieaboard.fragments;

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
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.ClubModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ClubEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ClubEditFragment extends Fragment {

    Button cancelButton, confirmButton;
    EditText nameInput, emailInput, descriptionInput;

    private static final String ARG_CLUB = "club";

    private ClubModel club;

    public ClubEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param club Parameter 1.
     * @return A new instance of fragment ClubEditFragment.
     */
    public static ClubEditFragment newInstance(ClubModel club) {
        ClubEditFragment fragment = new ClubEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_CLUB, club);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            club = (ClubModel) getArguments().getSerializable(ARG_CLUB);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_club_edit, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cancelButton = view.findViewById(R.id.button_cancel);
        confirmButton = view.findViewById(R.id.button_confirm);

        nameInput = view.findViewById(R.id.name_input);
        emailInput = view.findViewById(R.id.email_address_input);
        descriptionInput = view.findViewById(R.id.description_input);

        nameInput.setText(club.getName());
        emailInput.setText(club.getEmail());
        descriptionInput.setText(club.getDescription());

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DataBaseHelper dataBase = new DataBaseHelper(getContext());
                club.setName(nameInput.getText().toString().trim());
                club.setEmail(emailInput.getText().toString().trim());
                club.setDescription(descriptionInput.getText().toString().trim());
                dataBase.updateClub(club);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("club", club);

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStack();
            }
        });
    }
}