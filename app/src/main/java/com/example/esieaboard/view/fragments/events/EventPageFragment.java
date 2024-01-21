package com.example.esieaboard.view.fragments.events;

import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import com.example.esieaboard.R;
import com.example.esieaboard.model.entities.Attendance;
import com.example.esieaboard.model.entities.Event;
import com.example.esieaboard.model.entities.User;
import com.example.esieaboard.viewmodel.AttendanceViewModel;
import com.example.esieaboard.viewmodel.EventViewModel;
import com.example.esieaboard.viewmodel.UserViewModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventPageFragment extends Fragment {

    ImageButton backButton;
    Button editButton, attendButton, cancelButton, deleteButton;
    TextView eventName, eventDate, eventLocation, eventDescription, eventCapacity, eventAttendees;

    Attendance attendance;

    AttendanceViewModel attendanceViewModel;
    EventViewModel eventViewModel;
    UserViewModel userViewModel;

    ArrayList<Attendance> attendances;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_EVENT = "event";

    private User u;
    private Event e;

    public EventPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param u Parameter 1.
     * @param e Parameter 2.
     * @return A new instance of fragment EventPageFragment.
     */
    public static EventPageFragment newInstance(User u, Event e) {
        EventPageFragment fragment = new EventPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, u);
        args.putSerializable(ARG_EVENT, e);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            u = (User) getArguments().getSerializable(ARG_USER);
            e = (Event) getArguments().getSerializable(ARG_EVENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_page, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        backButton = view.findViewById(R.id.back_button);
        editButton = view.findViewById(R.id.edit_button);
        attendButton = view.findViewById(R.id.attend_button);
        cancelButton = view.findViewById(R.id.cancel_button);
        deleteButton = view.findViewById(R.id.delete_button);
        eventName = view.findViewById(R.id.event_name);
        eventDate = view.findViewById(R.id.event_date);
        eventLocation = view.findViewById(R.id.event_location);
        eventDescription = view.findViewById(R.id.event_description);
        eventCapacity = view.findViewById(R.id.event_capacity);
        eventAttendees = view.findViewById(R.id.event_attendees);

        attendanceViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(AttendanceViewModel.class);
        eventViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(EventViewModel.class);
        userViewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication())).get(UserViewModel.class);

        eventViewModel.get(e.getId()).observe(getViewLifecycleOwner(), event -> {
            eventName.setText(event.getName());
            eventLocation.setText(event.getLocation());
            eventDate.setText(event.getDate());
            eventDescription.setText(event.getDescription());
            eventCapacity.setText(String.valueOf(event.getCapacity()));


        });

        attendances = new ArrayList<>();

        attendanceViewModel.getAllByEvent(e.getId()).observe(getViewLifecycleOwner(), attendances -> {
            int result = attendances.size();

            eventAttendees.setText(String.valueOf(result));
            if(result >= e.getCapacity()) {
                attendButton.setVisibility(GONE);
            }
        });

        attendanceViewModel.get(u.getId(), e.getId()).observe(getViewLifecycleOwner(), attendance -> {
            if(attendance != null) {
                attendButton.setVisibility(GONE);
                cancelButton.setVisibility(VISIBLE);
            }
            else {
                attendButton.setVisibility(VISIBLE);
                cancelButton.setVisibility(GONE);
            }
        });

        userViewModel.get(u.getId()).observe(getViewLifecycleOwner(), user -> {
            if(user.getRights() < 1) { editButton.setVisibility(GONE); }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventViewModel.get(e.getId()).observe(getViewLifecycleOwner(), event -> {
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.fragment_container_view, EventEditFragment.newInstance(event));
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                });
            }
        });

        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance = new Attendance(u.getId(), e.getId(), 1);
                attendanceViewModel.insert(attendance);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendanceViewModel.unattend(u.getId(), e.getId());
                attendButton.setVisibility(VISIBLE);
                cancelButton.setVisibility(GONE);
            }
        });
    }

    void confirmDialog() {
        eventViewModel.get(e.getId()).observe(getViewLifecycleOwner(), event -> {
            if(event != null) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
                builder.setTitle("Delete " + event.getName() + " ?");
                builder.setMessage("Do you really want to delete " + event.getName() + " ?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eventViewModel.delete(event);
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.popBackStackImmediate();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                builder.create().show();}
        });

    }
}