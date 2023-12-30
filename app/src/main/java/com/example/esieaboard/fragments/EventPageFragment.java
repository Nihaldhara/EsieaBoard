package com.example.esieaboard.fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
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
import com.example.esieaboard.DataBaseHelper;
import com.example.esieaboard.R;
import com.example.esieaboard.models.AttendanceModel;
import com.example.esieaboard.models.EventModel;
import com.example.esieaboard.models.UserModel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventPageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventPageFragment extends Fragment {

    private static final int EDIT_EVENT_REQUEST_CODE = 1;

    ImageButton backButton;
    Button editButton, attendButton, cancelButton, deleteButton;
    TextView eventName, eventDate, eventLocation, eventDescription, eventCapacity, eventAttendees;
    AttendanceModel attendance;
    DataBaseHelper dataBaseHelper;

    ArrayList<AttendanceModel> attendances;

    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER = "user";
    private static final String ARG_EVENT = "event";

    private UserModel user;
    private EventModel event;

    public EventPageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param user  Parameter 1.
     * @param event Parameter 2.
     * @return A new instance of fragment EventPageFragment.
     */
    public static EventPageFragment newInstance(UserModel user, EventModel event) {
        EventPageFragment fragment = new EventPageFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_USER, user);
        args.putSerializable(ARG_EVENT, event);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            user = (UserModel) getArguments().getSerializable(ARG_USER);
            event = (EventModel) getArguments().getSerializable(ARG_EVENT);
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

        eventName.setText(event.getName());
        eventLocation.setText(event.getLocation());
        eventDate.setText(event.getDate());
        eventDescription.setText(event.getDescription());
        eventCapacity.setText(String.valueOf(event.getCapacity()));

        attendances = new ArrayList<>();

        dataBaseHelper = new DataBaseHelper(getContext());

        eventAttendees.setText(String.valueOf(countAttendance()));
        if(attendee()) {
            attendButton.setVisibility(GONE);
            cancelButton.setVisibility(VISIBLE);
        }

        if(countAttendance() >= event.getCapacity()) {
            attendButton.setVisibility(GONE);
        }

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

        if(user.getRights() < 1) { editButton.setVisibility(GONE); }
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_view, EventEditFragment.newInstance(event));
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        attendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attendance = new AttendanceModel(-1, user.getId(), event.getId(), 1);
                dataBaseHelper.addAttendance(attendance);
                attendButton.setVisibility(GONE);
                cancelButton.setVisibility(VISIBLE);
                eventAttendees.setText(String.valueOf(countAttendance()));
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = dataBaseHelper.readAttendanceData();

                if(cursor != null) {
                    while(cursor.moveToNext()) {
                        if(cursor.getInt(1) == user.getId()) {
                            attendance = dataBaseHelper.getAttendanceByEventId(event.getId());
                        }
                    }
                }
                dataBaseHelper.deleteAttendanceRow(String.valueOf(attendance.getId()));
                attendButton.setVisibility(VISIBLE);
                cancelButton.setVisibility(GONE);
                eventAttendees.setText(String.valueOf(countAttendance()));
            }
        });
    }

    int countAttendance() {
        attendances.clear();
        Cursor cursor = dataBaseHelper.readAttendanceData();
        int result = 0;

        if(cursor != null) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == event.getId()) {
                    result++;
                }
            }
        }
        return result;
    }

    boolean attendee() {
        Cursor cursor = dataBaseHelper.readAttendanceData();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                if(cursor.getInt(2) == event.getId() && cursor.getInt(1) == user.getId()) {
                    attendance = new AttendanceModel(cursor.getInt(0), cursor.getInt(1), cursor.getInt(2), cursor.getInt(3));
                    return true;
                }
            }

        }

        return false;
    }

    void confirmDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Delete " + event.getName() + " ?");
        builder.setMessage("Do you really want to delete " + event.getName() + " ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBaseHelper dataBase = new DataBaseHelper(getContext());
                dataBase.deleteEventRow(String.valueOf(event.getId()));
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.popBackStackImmediate();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}