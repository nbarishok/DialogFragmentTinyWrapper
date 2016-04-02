package com.onemanparty.dialogfragmenttinywrapper.sample;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.Button;
import android.widget.Toast;

import com.onemanparty.dialogfragmenttinywrapper.R;
import com.onemanparty.dialogfragmenttinywrapper.sample.view.BaseActivity;
import com.onemanparty.dialogfragmenttinywrapper.sample.view.BaseFragment;
import com.onemanparty.lib.dialog.delegate.ConfirmDialogFragmentDelegate;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Sample fragment
 */
public class SampleDialogFragment extends BaseFragment {

    public static final String TAG = SampleDialogFragment.class.getSimpleName();

    @Bind(R.id.dialog_sample_fragment_btn_show_dialog)
    Button mShowDialog;

    /**
     * Dialog delegate
     * Abstracts away dialog state persistence, dialog recreation
     */
    ConfirmDialogFragmentDelegate<SampleDialogViewModel> mDelegate;

    /**
     * Dialog listener
     */
    private ConfirmDialogFragmentDelegate.OnConfirmWithDataDialogListener<SampleDialogViewModel> mListener = new ConfirmDialogFragmentDelegate.OnConfirmWithDataDialogListener<SampleDialogViewModel>() {
        @Override
        public void onOkDialog(DialogFragment dialog, SampleDialogViewModel data) {
            Toast.makeText(getContext(), String.valueOf(data.getState()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancelDialog(DialogFragment dialog, SampleDialogViewModel data) {

        }
    };

    public static Fragment newInstance() {
        return new SampleDialogFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        mDelegate = new ConfirmDialogFragmentDelegate<>("some_dialog", mListener, R.string.sample_dialog_title, R.string.sample_dialog_subtitle, R.string.sample_dialog_ok, R.string.sample_dialog_cancel);
        mDelegate.onCreate(savedInstanceState, (BaseActivity) getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        mDelegate.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle saveInstanceState) {
        super.onSaveInstanceState(saveInstanceState);
        mDelegate.onSaveInstanceState(saveInstanceState);
    }

    @OnClick(R.id.dialog_sample_fragment_btn_show_dialog)
    public void onShowDialogClick() {
        mDelegate.showDialog(new SampleDialogViewModel(1));
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sample_fragment;
    }

}
