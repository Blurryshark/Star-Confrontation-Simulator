package com.example.project2.DialogJunk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class ShipDeleteConfirmationDialog extends AppCompatDialogFragment {
    private ShipDeleteConfirmationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete Ship?")
                .setMessage("Are you sure want to delete this ship?\nAll fleets with this ship" +
                        " will likewise be deleted")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("confirm, decomission!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onYesClicked();
                    }
                });
        return builder.create();
    }

    public interface ShipDeleteConfirmationDialogListener {
        void onYesClicked();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (ShipDeleteConfirmationDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
            + "must implement DialogListener");
        }
    }
}