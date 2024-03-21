package com.example.pepperphoto_compagnon.ui.QR;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.inputmethod.InputMethodManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.pepperphoto_compagnon.R;
import com.example.pepperphoto_compagnon.databinding.FragmentQrBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class QRFragment extends Fragment {

    private FragmentQrBinding binding;
    private Bitmap bitmap;

    private String mail;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentQrBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onStart() {
        super.onStart();
        Button btnGenerer = getView().findViewById(R.id.btnGenerer);
        Button btnTelecharger = getView().findViewById(R.id.btnTelecharger);
        btnTelecharger.setVisibility(View.INVISIBLE);
        ImageView generatedQR = (ImageView) getView().findViewById(R.id.qrView);
        generatedQR.setVisibility(View.INVISIBLE);
        FloatingActionButton btnClose = getView().findViewById(R.id.buttonClose);
        btnClose.setVisibility(View.INVISIBLE);
        EditText inputMail = getView().findViewById(R.id.inputMail);
        inputMail.setVisibility(View.VISIBLE);

        String emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9._-]+\\.+[a-z]+";

        btnGenerer.setOnClickListener(v -> {
            mail = inputMail.getText().toString().trim();
            if (mail.matches(emailPattern)||!mail.matches(emailPattern)) {
                try {
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    bitmap = barcodeEncoder.encodeBitmap(mail, BarcodeFormat.QR_CODE, 600, 600);
                    generatedQR.setImageBitmap(bitmap);
                    generatedQR.setVisibility(View.VISIBLE);
                    inputMail.setText("");
                    inputMail.setVisibility(View.INVISIBLE);
                    InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    manager.hideSoftInputFromWindow(inputMail.getApplicationWindowToken(), 0);
                    btnTelecharger.setVisibility(View.VISIBLE);
                    btnClose.setVisibility(View.VISIBLE);
                } catch (WriterException e) {
                    e.printStackTrace();
                }

            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Adresse mail invalide", Toast.LENGTH_SHORT).show();
            }
        });

        btnTelecharger.setOnClickListener(v -> {
            MediaStore.Images.Media.insertImage(getActivity().getContentResolver(), bitmap, "QR_Code_" + mail, "");
            Toast.makeText(getActivity().getApplicationContext(), "L'image à bien été enregistrée !", Toast.LENGTH_SHORT).show();

        });

        btnClose.setOnClickListener(v-> {
            btnClose.setVisibility(View.INVISIBLE);
            btnTelecharger.setVisibility(View.INVISIBLE);
            generatedQR.setVisibility(View.INVISIBLE);
            inputMail.setVisibility(View.VISIBLE);
        });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}