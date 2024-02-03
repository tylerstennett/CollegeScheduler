package com.example.collegescheduler.ui.examlist;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.collegescheduler.R;

public class ExamListFragment extends Fragment {

    public static ExamListFragment newInstance() {
        return new ExamListFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exam_list, container, false);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // mViewModel = new ViewModelProvider(this).get(ExamListViewModel.class);
        // TODO: Use the ViewModel
    }

}
