package com.kcanel.tareas.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.navigation.Navigation;

import com.google.firebase.database.DatabaseReference;
import com.kcanel.tareas.databinding.FragmentAddTaskBinding;
import com.kcanel.tareas.models.TaskModel;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AddTaskFragment extends BaseFragment {

    private FragmentAddTaskBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);

        binding.btnSave.setOnClickListener(view -> {
            addNewElement(view);
        });

        return binding.getRoot();
    }

    private void addNewElement(View view) {

        if (isConnected()) {
            if (!binding.etName.getText().toString().isEmpty() &&
                    !binding.etDescription.getText().toString().isEmpty()) {

                Date date = new Date();
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                String dateString = format.format(date);

                TaskModel todo = new TaskModel(binding.etName.getText().toString(), binding.etDescription.getText().toString(), dateString);

                DatabaseReference myRef = database.getReference("tareas").push();
                myRef.setValue(todo);

                binding.etName.setText("");
                binding.etDescription.setText("");

                Navigation.findNavController(view).popBackStack();
            }else{
                Toast.makeText(getContext(),"Ingrese todos los campos",Toast.LENGTH_SHORT).show();
            }

        }

    }


}