package com.example.collegescheduler.ui.courselist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.entities.Course;
import com.example.collegescheduler.interfaces.CourseDatabase;

import java.util.List;

public class CoursesAdapter extends RecyclerView.Adapter<CoursesAdapter.CourseListViewHolder> implements CourseConfirmDialog.ConfirmationDialogListener {
    private CourseDatabase courseDatabase;
    private List<Course> list;
    private EditListener editListener;
    private FragmentManager fragmentManager;


    public CoursesAdapter(List<Course> list, EditListener editListener, CourseDatabase courseDatabase, FragmentManager fragmentManager) {
        this.list = list;
        this.editListener = editListener;
        this.courseDatabase = courseDatabase;
        this.fragmentManager = fragmentManager;
    }

    // creates new course views
    @Override
    public CourseListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new course view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.course_entry, viewGroup, false);

        // return new ViewHolder with course View
        return new CourseListViewHolder(view);
    }

    // replace contents of a view
    @Override
    public void onBindViewHolder(CourseListViewHolder viewHolder, final int position) {
        viewHolder.courseName.setText(list.get(position).courseName);
        viewHolder.courseDays.setText(list.get(position).daysOfWeek);
        viewHolder.courseTime.setText(list.get(position).courseTime);
        viewHolder.courseLocation.setText(list.get(position).location);
        viewHolder.courseInstructor.setText(list.get(position).professor);
        viewHolder.courseSection.setText(list.get(position).section);

        // set listener for delete button
        viewHolder.courseDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseConfirmDialog dialog = new CourseConfirmDialog();
                dialog.setListener(CoursesAdapter.this);
                dialog.setViewHolder(viewHolder);
                dialog.setAction("delete");
                dialog.show(fragmentManager, "ConfirmationDialog");
            }
        });

        // set listener for edit button
        viewHolder.courseEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseConfirmDialog dialog = new CourseConfirmDialog();
                dialog.setListener(CoursesAdapter.this);
                dialog.setViewHolder(viewHolder);
                dialog.setAction("edit");
                dialog.show(fragmentManager, "ConfirmationDialog");
            }
        });
    }

    public void onConfirmDelete(CourseListViewHolder viewHolder) {
        // delete
        int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            Course courseToDelete = list.get(adapterPosition);
            courseDatabase.deleteCourseFromDatabase(courseToDelete);
            // list.remove(adapterPosition); automatically remakes list
            // notifyItemRemoved(adapterPosition);
        }
    }

    public void onConfirmEdit(CourseListViewHolder viewHolder) {
        // edit
        int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
        if (adapterPosition != RecyclerView.NO_POSITION) {
            Course courseToEdit = list.get(adapterPosition);

            courseDatabase.updateCourseWithText(courseToEdit);
        }
    }

    public void onCancel() {
        // cancel
        return;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addItem(Course newCourse) {
        list.add(newCourse);
        notifyItemInserted(list.size());
    }

    public void addCourseList(List<Course> courses) {
        this.list.clear();
        this.list.addAll(courses);
        notifyDataSetChanged();
    }

    // discontinued
    public void editItem(Course newCourse, int position) {
        list.set(position, newCourse);
        notifyItemChanged(position);
    }

    /*
    ViewHolder that caches references to entries in RecyclerView,
    improving performance over looking up ID every time
     */
    public static class CourseListViewHolder extends RecyclerView.ViewHolder {

        private final TextView courseName;
        private final TextView courseDays;
        private final TextView courseTime;
        private final TextView courseLocation;
        private final TextView courseInstructor;
        private final TextView courseSection;
        private final ImageButton courseDelete;
        private final ImageButton courseEdit;
        private final View view;

        public CourseListViewHolder(View itemView) {
            super(itemView);

            courseName = (TextView) itemView.findViewById(R.id.courseName);
            courseDays = (TextView) itemView.findViewById(R.id.courseDays);
            courseTime = (TextView) itemView.findViewById(R.id.courseTime);
            courseLocation = (TextView) itemView.findViewById(R.id.courseLocation);
            courseInstructor = (TextView) itemView.findViewById(R.id.courseInstructor);
            courseSection = (TextView) itemView.findViewById(R.id.courseSection);

            courseDelete = itemView.findViewById(R.id.buttonCourseDelete);
            courseEdit = itemView.findViewById(R.id.buttonCourseEdit);

            view = itemView;
        }
    }

    // discontinued
    public interface EditListener {
        void onEditButtonClick(int position);
    }
}
