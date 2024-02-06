package com.example.collegescheduler.ui.courselist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

public class CourseConfirmDialog extends DialogFragment {
    private ConfirmationDialogListener listener;
    private CoursesAdapter.CourseListViewHolder viewHolder;
    private String action;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Are you sure want to edit/delete this course?")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            if (action.equals("delete")) {
                                listener.onConfirmDelete(viewHolder);
                            } else if (action.equals("edit")) {
                                listener.onConfirmEdit(viewHolder);
                            }

                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (listener != null) {
                            listener.onCancel();
                        }
                    }
                });
        return builder.create();
    }

    public void setListener(ConfirmationDialogListener listener) {
        this.listener = listener;
    }

    public void setViewHolder(CoursesAdapter.CourseListViewHolder viewHolder) {
        this.viewHolder = viewHolder;
    }

    public void setAction(String deleteEdit) {
        if (deleteEdit.equals("delete") || deleteEdit.equals("edit")) {
            action = deleteEdit;
        }
    }

    public interface ConfirmationDialogListener {
        void onConfirmDelete(CoursesAdapter.CourseListViewHolder viewHolder);
        void onConfirmEdit(CoursesAdapter.CourseListViewHolder viewHolder);
        void onCancel();
    }
}