package com.example.esieaboard.view.fragments.events;

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
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.viewmodel.EventViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventEditFragment extends Fragment {

    EditText inputName, inputDate, inputLocation, inputCapacity, inputDescription;
    Button buttonConfirm, buttonCancel;

    EventViewModel eventViewModel;

    private static final String ARG_EVENT = "event";

    private Event e;

    public EventEditFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param e Parameter 1.
     * @return A new instance of fragment EventEditFragment.
     */
    public static EventEditFragment newInstance(Event e) {
        EventEditFragment fragment = new EventEditFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_EVENT, e);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            e = (Event) getArguments().getSerializable(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_edit, container, false);
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
        eventViewModel.get(e.getId()).observe(getViewLifecycleOwner(), event -> {
            inputName.setText(event.getName());
            inputDate.setText(event.getDate());
            inputLocation.setText(event.getLocation());
            inputDescription.setText(event.getDescription());
            inputCapacity.setText(String.valueOf(event.getCapacity()));

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

                    event.setName(inputName.getText().toString().trim());
                    event.setDate(inputDate.getText().toString().trim());
                    event.setLocation(inputLocation.getText().toString().trim());
                    event.setDescription(inputDescription.getText().toString().trim());
                    event.setCapacity(Integer.parseInt(inputCapacity.getText().toString().trim()));
                    eventViewModel.update(event);
                    Intent resultIntent = new Intent();
                    resultIntent.putExtra("event", event);

                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.popBackStackImmediate();
                }
            });
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
    }
}