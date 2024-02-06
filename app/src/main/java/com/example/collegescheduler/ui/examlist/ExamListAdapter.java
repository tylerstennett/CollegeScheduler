package com.example.collegescheduler.ui.examlist;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.collegescheduler.R;
import com.example.collegescheduler.db.SharedViewModel;
import com.example.collegescheduler.db.entities.Exam;
import com.example.collegescheduler.interfaces.ExamDatabase;

import java.util.List;

public class ExamListAdapter extends RecyclerView.Adapter<ExamListAdapter.ExamListViewHolder> {
    private ExamDatabase examDatabase;
    private List<Exam> list;
    // listener maybe
    public ExamListAdapter(List<Exam> list, ExamDatabase examDatabase) {
        this.list = list;
        this.examDatabase = examDatabase;
    }

    // creates new exam views
    @NonNull
    @Override
    public ExamListViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        // Create a new Exam view
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.exam_entry, viewGroup, false);
        // return ViewHolder with new ExamView
        return new ExamListViewHolder(view);
    }



    // replace contents of a view
    @Override
    public void onBindViewHolder(ExamListViewHolder viewHolder, final int position) {
        viewHolder.examName.setText(list.get(position).examName);
        viewHolder.examDate.setText(list.get(position).date);
        viewHolder.examTime.setText(list.get(position).time);
        viewHolder.examLocation.setText(list.get(position).location);
        viewHolder.examCourse.setText(list.get(position).courseName);

        viewHolder.examDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = viewHolder.getAbsoluteAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Exam examToDelete = list.get(adapterPosition);
                    examDatabase.deleteExamFromDatabase(examToDelete);
                    list.remove(adapterPosition);
                    notifyItemRemoved(adapterPosition);
                }
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void addItem(Exam newExam) {
        list.add(newExam);
        notifyItemInserted(list.size() - 1);
    }

    public void addExamList(List<Exam> exams) {
        this.list.clear();
        this.list.addAll(exams);
        notifyDataSetChanged();
    }

    /*
    ViewHolder that caches references to entries in RecyclerView,
    improving performance over looking up ID every time
     */
    public static class ExamListViewHolder extends RecyclerView.ViewHolder {
        private final TextView examName;
        private final TextView examDate;
        private final TextView examTime;
        private final TextView examLocation;
        private final TextView examCourse;
        private final ImageButton examDelete;
        private final View view;
        public ExamListViewHolder (View itemView) {
            super(itemView);

            examName = (TextView) itemView.findViewById(R.id.examName);
            examDate = (TextView) itemView.findViewById(R.id.examDate);
            examTime = (TextView) itemView.findViewById(R.id.examTime);
            examLocation = (TextView) itemView.findViewById(R.id.examLocation);
            examCourse = (TextView) itemView.findViewById(R.id.examCourse);
            examDelete = itemView.findViewById(R.id.buttonExamDelete);

            view = itemView;
        }
    }
}