package com.example.esieaboard.view.fragments.events;

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
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.viewmodel.EventViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventNewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventNewFragment extends Fragment {

    EditText inputName, inputDate, inputLocation, inputCapacity, inputDescription;
    Button buttonConfirm, buttonCancel;
    Event event;

    EventViewModel eventViewModel;

    private static final String ARG_CLUB_ID = "clubId";

    private int clubId;

    public EventNewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param clubId Parameter 1.
     * @return A new instance of fragment EventNewFragment.
     */
    public static EventNewFragment newInstance(int clubId) {
        EventNewFragment fragment = new EventNewFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CLUB_ID, clubId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            clubId = getArguments().getInt(ARG_CLUB_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        inputName = view.findViewById(R.id.name_input);
        inputDate = view.findViewById(R.id.date_input);
        inputLocation = view.findViewById(R.id.location_input);
        inputCapacity = view.findViewById(R.id.capacity_input);
        inputDescription = view.findViewById(R.id.description_input);

        buttonCancel = view.findViewById(R.id.button_cancel);
        buttonConfirm = view.findViewById(R.id.button_confirm);

        eventViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EventViewModel.class);

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        buttonConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (inputName.getText().toString().isEmpty()) {
                    inputName.setError("Name is required");
                    return;
                }

                if (inputDate.getText().toString().isEmpty()) {
                    inputDate.setError("Date is required");
                    return;
                } else {
                    String[] date = inputDate.getText().toString().split("-");
                    if (date.length != 3) {
                        inputDate.setError("Date format is invalid");
                        return;
                    } else {
                        try {
                            int year = Integer.parseInt(date[0]);
                            int month = Integer.parseInt(date[1]);
                            int day = Integer.parseInt(date[2]);
                            if (day < 1 || day > 31) {
                                inputDate.setError("Day is invalid");
                                return;
                            }
                            if (month < 1 || month > 12) {
                                inputDate.setError("Month is invalid");
                                return;
                            }
                            if (year < 2024) {
                                inputDate.setError("Year is invalid");
                                return;
                            }
                        } catch (Exception e) {
                            inputDate.setError("Date format is invalid");
                            return;
                        }
                    }
                }

                if (inputLocation.getText().toString().isEmpty()) {
                    inputLocation.setError("Location is required");
                    return;
                }

                if (inputCapacity.getText().toString().isEmpty()) {
                    inputCapacity.setError("Capacity is required");
                    return;
                } else {
                    try {
                        int capacity = Integer.parseInt(inputCapacity.getText().toString());
                        if (capacity < 1) {
                            inputCapacity.setError("Capacity must be greater than 0");
                            return;
                        }
                    } catch (Exception e) {
                        inputCapacity.setError("Capacity must be a number");
                        return;
                    }
                }

                if (inputDescription.getText().toString().isEmpty()) {
                    inputDescription.setError("Description is required");
                    return;
                }

                event = new Event(clubId, inputName.getText().toString(),
                        inputDescription.getText().toString(), inputDate.getText().toString(),
                        inputLocation.getText().toString(), Integer.parseInt(inputCapacity.getText().toString()));
                eventViewModel.insert(event);

                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
    }
}