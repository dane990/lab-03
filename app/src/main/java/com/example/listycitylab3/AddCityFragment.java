package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {
    interface AddCityDialogListener {
        void addCity(City city);
    }
    interface EditCityDialogListener {
        void editCity(int index, City city);
    }
    private AddCityDialogListener addListener;
    private EditCityDialogListener editListener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddCityDialogListener) {
            addListener = (AddCityDialogListener) context;
        }
        if (context instanceof EditCityDialogListener) {
            editListener = (EditCityDialogListener) context;
        }
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view =
                getLayoutInflater().inflate(R.layout.fragment_add_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setView(view)
                .setNegativeButton("Cancel", null);

        Bundle args = getArguments();
        if (args == null) {
            builder.setTitle("Add a city")
                    .setPositiveButton("Add", (dialog, which) -> {
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        addListener.addCity(new City(cityName, provinceName));
                    });
        } else {
            String city = args.getString("city");
            String province = args.getString("province");
            editCityName.setText(city);
            editProvinceName.setText(province);
            builder.setTitle("Edit a city")
                    .setPositiveButton("Edit", (dialog, which) -> {
                        int index = args.getInt("index");
                        String cityName = editCityName.getText().toString();
                        String provinceName = editProvinceName.getText().toString();
                        editListener.editCity(index, new City(cityName, provinceName));
                    });
        }
        return builder.create();
    }
}
