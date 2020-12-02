package com.kcanel.tareas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kcanel.tareas.databinding.ItemBinding;
import com.kcanel.tareas.models.TaskModel;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListItemsAdapter extends RecyclerView.Adapter<ListItemsAdapter.ViewHolder> {

    Context context;
    List<TaskModel> list;
    List<String> ids;
    String done;


    ListItemListener listener;
    public interface ListItemListener {
        void onItemClickListener(int position, TaskModel item, String key);
    }

    public void setListener(ListItemListener listener) {
        this.listener = listener;
    }

    public void updateList(List<TaskModel> list, List<String> ids) {
        this.list = list;
        this.ids = ids;
        notifyDataSetChanged();
    }

    public ListItemsAdapter(Context context, List<TaskModel> list, List<String> ids) {
        this.context = context;
        this.list = list;
        this.ids = ids;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding, context);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.setItem(list.get(position), ids.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemBinding binding;

        public ViewHolder(ItemBinding view, Context context) {
            super(view.getRoot());
            binding = view;
        }

        public void setItem(TaskModel taskModel, String key) {
            binding.tvTitle.setText(taskModel.getName());
            binding.tvSubtitle.setText(taskModel.getDescription());
            binding.tvDate.setText(taskModel.getDate());
            binding.tvDone.setOnClickListener(view -> {
                listener.onItemClickListener(getAdapterPosition(), taskModel,key);
            });

            if(!taskModel.getDone().equalsIgnoreCase("0")){
                Picasso.get()
                        .load("https://png.pngtree.com/png-vector/20191113/ourmid/pngtree-green-check-mark-icon-flat-style-png-image_1986021.jpg")
                        .into(binding.tvDone);
            }else{
                Picasso.get()
                        .load("https://cecyd.files.wordpress.com/2011/08/12074327311562940906milker_x_icon-svg-med.png")
                        .into(binding.tvDone);
            }

        }
    }
}
