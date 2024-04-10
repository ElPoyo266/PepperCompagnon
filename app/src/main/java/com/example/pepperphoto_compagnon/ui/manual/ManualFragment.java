package com.example.pepperphoto_compagnon.ui.manual;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pepperphoto_compagnon.R;
import com.example.pepperphoto_compagnon.databinding.FragmentManualBinding;
import com.github.barteksc.pdfviewer.PDFView;

public class ManualFragment extends Fragment {

    private FragmentManualBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ManualViewModel manualViewModel =
                new ViewModelProvider(this).get(ManualViewModel.class);

        binding = FragmentManualBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        PDFView pdfView = getView().findViewById(R.id.pdfview);
        pdfView.fromAsset("Manuel_utilisation.pdf").load();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}