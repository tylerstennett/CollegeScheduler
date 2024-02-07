package com.example.collegescheduler.ui.assignments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Assignment;
import com.example.collegescheduler.interfaces.AssignmentDatabase;

import java.util.List;
public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private AssignmentDatabase assignmentDatabase;
    private List<Assignment> list;
    private AssignmentsFragment assignmentsFragment;
    private Assignment selectedItem; // Track the selected item
    private TextView infoAssignment;
    private Context fragmentContext;

    public AssignmentAdapter(List<Assignment> list, AssignmentDatabase assignmentDatabase, Context fragmentContext) {
        this.list = list;
        this.assignmentDatabase = assignmentDatabase;
        this.fragmentContext = fragmentContext;
    }

    // creates new exam views
    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_add_on, viewGroup, false);
        // return ViewHolder with new assignmentView

        return new AssignmentViewHolder(view);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(AssignmentViewHolder viewHolder, int position) {

        viewHolder.assignmentAssignment.setText("Assignment: " + list.get(position).assignmentName);
        viewHolder.assignmentClass.setText("Class: " + list.get(position).courseName);
        viewHolder.assignmentDueDate.setText("Due Date: " + list.get(position).dueDate);

        // make sure not to prompt CheckedChangeListener
        viewHolder.assignmentCompleted.setOnCheckedChangeListener(null);
        viewHolder.assignmentCompleted.setChecked(list.get(position).completed);

        viewHolder.assignmentCompleted.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Assignment assignmentToEdit = list.get(adapterPosition);
                    assignmentDatabase.updateAssignmentCompleted(isChecked, assignmentToEdit);
                }
            }
        });

        viewHolder.assignmentDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Assignment assignmentToDelete = list.get(adapterPosition);
                    assignmentDatabase.deleteAssignmentInDatabase(assignmentToDelete);
                    // list.remove(adapterPosition); automatically remakes list
                    // notifyItemRemoved(adapterPosition);
                }
            }
        });

        viewHolder.assignmentEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Assignment assignmentToEdit = list.get(adapterPosition);
                    assignmentDatabase.updateAssignmentWithText(assignmentToEdit);
                }
                Toast.makeText(fragmentContext, "Type edits in the input boxes above, then click edit again.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void editItem(Assignment newAssignment, int position) {
        list.set(position, newAssignment);
        notifyItemChanged(position);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Assignment newAssignment) {
        list.add(newAssignment);
        notifyItemInserted(list.size());
    }

    public void addAssignmentList(List<Assignment> assignments) {
        this.list.clear();
        this.list.addAll(assignments);
        notifyDataSetChanged();
    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView assignmentDueDate;
        private final TextView assignmentClass;
        private final TextView assignmentAssignment;
        private final Button assignmentDelete;
        private final Button assignmentEdit;
        private final CheckBox assignmentCompleted;


        private final View view;


        public AssignmentViewHolder (View itemView) {
            super(itemView);
            assignmentDueDate = itemView.findViewById(R.id.textViewDueDate);
            assignmentClass = itemView.findViewById(R.id.textViewAssociatedClass);
            assignmentAssignment = itemView.findViewById(R.id.textViewAssignment);
            assignmentCompleted = itemView.findViewById(R.id.checkBoxComplete);

            assignmentDelete = itemView.findViewById(R.id.buttonDeleteTask);
            assignmentEdit = itemView.findViewById(R.id.buttonEditTask);

            view = itemView;
        }

    }
}