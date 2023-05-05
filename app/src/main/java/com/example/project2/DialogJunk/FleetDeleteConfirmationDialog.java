package com.example.project2.DialogJunk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class FleetDeleteConfirmationDialog extends AppCompatDialogFragment {
    private FleetDeleteConfirmationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Attention!")
                .setMessage("Are you sure you want to delete this fleet?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes, mothball the Starfleet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onYesClicked();
                    }
                });
        return builder.create();
    }

    public interface FleetDeleteConfirmationDialogListener {
        void onYesClicked();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try {
            listener = (FleetDeleteConfirmationDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString()
                    + "must implement FleetDeleteConfirmationDialogListener");
        }
    }
}
