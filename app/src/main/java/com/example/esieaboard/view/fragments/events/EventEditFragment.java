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
        });

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
                eventViewModel.get(e.getId()).observe(getViewLifecycleOwner(), event -> {
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
                });
            }
        });
    }
}