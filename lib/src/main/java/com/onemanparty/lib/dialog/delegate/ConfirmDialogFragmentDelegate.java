package com.onemanparty.lib.dialog.delegate;

import android.app.DialogFragment;
import android.os.Parcelable;

import com.onemanparty.lib.dialog.ConfirmDialogFragment;
import com.onemanparty.lib.dialog.OnConfirmDialogListener;


/**
 * Dialog delegate for {@link ConfirmDialogFragment}
 */
public class ConfirmDialogFragmentDelegate<D extends Parcelable> extends BaseDialogFragmentDelegate<D> implements OnConfirmDialogListener {

    private static final String TAG = ConfirmDialogFragmentDelegate.class.getSimpleName();

    /**
     * Listener for {@link ConfirmDialogFragment} events enhanced with data
     *
     * One should use this interface instead of original one {@link OnConfirmDialogListener} to have data available in callbacks
     */
    public interface OnConfirmWithDataDialogListener<D> {
        void onOkDialog(final DialogFragment dialog, D data);
        void onCancelDialog(final DialogFragment dialog, D data);
    }

    private OnConfirmWithDataDialogListener<D> mListener;

    private int mTitleRes;
    private int mSubtitleRes;
    private int mOkRes;
    private int mCancelRes;

    public ConfirmDialogFragmentDelegate(String tag, OnConfirmWithDataDialogListener<D> listener, int titleRes, int subtitleRes, int okRes, int cancelRes) {
        // it is better to use <b>Builder</b> pattern to create a dialog instance
        super(tag);
        this.mListener = listener;
        this.mTitleRes = titleRes;
        this.mSubtitleRes = subtitleRes;
        this.mOkRes = okRes;
        this.mCancelRes = cancelRes;
    }

    @Override
    protected DialogFragment createDialogInstance() {
        ConfirmDialogFragment dialog = ConfirmDialogFragment.newInstance(mTitleRes, mSubtitleRes, mOkRes, mCancelRes);
        dialog.setListener(this);
        return dialog;
    }

    @Override
    public void onCancelDialog(DialogFragment dialogFragment) {
        if (mListener != null) {
            mListener.onCancelDialog(dialogFragment, getData());
        }
    }

    @Override
    public void onOkDialog(DialogFragment dialogFragment) {
        if (mListener != null) {
            mListener.onOkDialog(dialogFragment, getData());
        }
    }
}