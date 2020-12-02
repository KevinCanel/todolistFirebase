package com.kcanel.tareas.ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.kcanel.tareas.R;
import com.kcanel.tareas.adapters.ListItemsAdapter;
import com.kcanel.tareas.databinding.FragmentListBinding;
import com.kcanel.tareas.models.TaskModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListFragment extends BaseFragment {

    private FragmentListBinding binding;
    private List<TaskModel> tasks = new ArrayList<>();
    private List<String> keys = new ArrayList<String>();
    private ListItemsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new ListItemsAdapter(getContext(), tasks, keys);
    }

    @Override
    public void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentListBinding.inflate(inflater, container, false);

        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.recyclerView.setAdapter(adapter);

        adapter.setListener((position, item, key) -> {
            updateTask(position, item, key);
        });

        binding.button.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_listFragment_to_addTaskFragment);
        });

        binding.switch2.setOnCheckedChangeListener((compoundButton, b) -> {
            filter();
        });
        return binding.getRoot();
    }

    private void updateTask(int position, TaskModel item, String key) {
        String finish = (item.getDone().equalsIgnoreCase("0") ? "1" : "0");

        Map<String, Object> postValues = new HashMap<String, Object>();

        postValues.put("done", finish);
        database.getReference("tareas").child(key).updateChildren(postValues);

        getData();
    }

    private void filter() {
        String selected = (binding.switch2.isChecked() ? "1" : "0");
        List<TaskModel> tasksTemp = new ArrayList<>();
        List<String> keysTemp = new ArrayList<String>();
        int i = 0;
        for (TaskModel task : tasks) {
            if (task.getDone().equalsIgnoreCase(selected)) {
                tasksTemp.add(task);
                keysTemp.add(keys.get(i));
                i++;
            }
        }
        adapter.updateList(tasksTemp, keysTemp);
    }

    private void getData() {
        if (isConnected()) {

            database.getReference("tareas").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            Map<String, TaskModel> td = new HashMap<String, TaskModel>();
                            for (DataSnapshot jobSnapshot : dataSnapshot.getChildren()) {
                                TaskModel task = jobSnapshot.getValue(TaskModel.class);
                                td.put(jobSnapshot.getKey(), task);
                            }

                            tasks = new ArrayList<>(td.values());
                            keys = new ArrayList<String>(td.keySet());

                            filter();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

        }
    }
}