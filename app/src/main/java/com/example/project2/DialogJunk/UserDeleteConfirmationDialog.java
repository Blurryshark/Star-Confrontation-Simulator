package com.example.project2.DialogJunk;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatDialogFragment;

public class UserDeleteConfirmationDialog extends AppCompatDialogFragment {
    private UserDeleteConfirmationDialogListener listener;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Delete User?")
                .setMessage("Delete User and all of their Fleets?")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setPositiveButton("Yes, dishonorably discharged!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onYesCLicked();
                    }
                });
        return builder.create();
    }

    public interface UserDeleteConfirmationDialogListener {
        void onYesCLicked();
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            listener = (UserDeleteConfirmationDialogListener) context;
        } catch (ClassCastException e){
            throw new ClassCastException(context.toString() + "must implement " +
                    "UserDeleteConfirmationDialogListener");
        }
    }
}
