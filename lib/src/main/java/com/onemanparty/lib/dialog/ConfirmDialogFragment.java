package com.onemanparty.lib.dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.onemanparty.lib.R;

/**
 *
 */
public class ConfirmDialogFragment extends DialogFragment {

    private static final String ARGUMENT_TITLE = "ARGUMENT_TITLE";
    private static final String ARGUMENT_SUBTITLE = "ARGUMENT_SUBTITLE";
    private static final String ARGUMENT_OK = "ARGUMENT_OK";
    private static final String ARGUMENT_CANCEL = "ARGUMENT_CANCEL";

    Button mOk;
    Button mCancel;
    private TextView mTitle;
    private TextView mSubtitle;

    public static ConfirmDialogFragment newInstance(int title, int subtitle, int ok, int cancel) {
        // it is better to use <b>Builder</b> pattern to create a dialog instance
        ConfirmDialogFragment dialog = new ConfirmDialogFragment();
        Bundle arguments = new Bundle();
        arguments.putInt(ARGUMENT_TITLE, title);
        arguments.putInt(ARGUMENT_SUBTITLE, subtitle);
        arguments.putInt(ARGUMENT_OK, ok);
        arguments.putInt(ARGUMENT_CANCEL, cancel);

        dialog.setArguments(arguments);
        return dialog;
    }

    public void setListener(OnConfirmDialogListener mListener) {
        this.mListener = mListener;
    }

    OnConfirmDialogListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setCancelable(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.confirm_dialog, container, false);
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        mTitle = (TextView) rootView.findViewById(R.id.confirm_dialog_title);
        mSubtitle = (TextView) rootView.findViewById(R.id.confirm_dialog_subtitle);
        mCancel = (Button) rootView.findViewById(R.id.confirm_dialog_cancel);
        mOk = (Button) rootView.findViewById(R.id.confirm_dialog_ok);
        mCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCancelClick();
            }
        });
        mOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOkClick();
            }
        });
        return rootView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mOk.setText(getArguments().getInt(ARGUMENT_OK));
        mCancel.setText(getArguments().getInt(ARGUMENT_CANCEL));
        mTitle.setText(getArguments().getInt(ARGUMENT_TITLE));
        mSubtitle.setText(getArguments().getInt(ARGUMENT_SUBTITLE));
    }

    public void onCancelClick() {
        dismiss();
        if (mListener != null) {
            mListener.onCancelDialog(this);
        }
    }

    public void onOkClick() {
        dismiss();
        if (mListener != null) {
            mListener.onOkDialog(this);
        }
    }

    @Override
    public void onDestroyView(){
        super.onDestroyView();
    }

}
