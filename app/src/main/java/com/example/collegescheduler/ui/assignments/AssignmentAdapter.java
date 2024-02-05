package com.example.collegescheduler.ui.assignments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;
import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Assignment;
import java.util.List;

public class AssignmentAdapter extends RecyclerView.Adapter<AssignmentAdapter.AssignmentViewHolder> {
    private List<Assignment> list;
    private AssignmentsFragment assignmentsFragment;

    public AssignmentAdapter(List<Assignment> list) {
        this.list = list;
    }

    // creates new exam views
    @Override
    public AssignmentViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new Exam view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.assignment_add_on, viewGroup, false);
        // return ViewHolder with new assignmentView
        return new AssignmentViewHolder(view);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(AssignmentViewHolder viewHolder, final int position) {
        viewHolder.assignmentDueDate.setText("Due Date: " + list.get(position).dueDate);
        viewHolder.assignmentClass.setText("Class: " + list.get(position).courseName);
        viewHolder.assignmentAssignment.setText("Assignment: " + list.get(position).assignmentName);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Assignment newAssignment) {
        list.add(newAssignment);
        notifyItemInserted(list.size());
    }

    //sorting attempt
//    public void setSortField(String selectedSortField) {
//    }

    public static class AssignmentViewHolder extends RecyclerView.ViewHolder {

        private final TextView assignmentDueDate;
        private final TextView assignmentClass;
        private final TextView assignmentAssignment;
        private final View view;


        public AssignmentViewHolder (View itemView) {
            super(itemView);

            assignmentDueDate = itemView.findViewById(R.id.textViewDueDate);
            assignmentClass = itemView.findViewById(R.id.textViewAssociatedClass);
            assignmentAssignment = itemView.findViewById(R.id.textViewAssignment);
            view = itemView;
        }
    }
}
