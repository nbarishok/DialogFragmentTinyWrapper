package com.onemanparty.lib.dialog.delegate;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;

import com.onemanparty.lib.dialog.LifecycleUtils;


/**
 * Base class for all dialog delegate implementations
 * Fragment, that uses this delegate should call the lifecycle methods of dialog in its corresponding lifecycle methods:
 * onCreate(...), onResume(...), onSaveInstanceState(...)
 *
 * Abstracts away data transfer to listener callbacks (persistence during configuration change / low-memory) + dialog recreation
 */
public abstract class BaseDialogFragmentDelegate<D extends Parcelable> {

    /**
     * Flag indicating necessity of dialog recreation
     */
    private boolean mRecreateDialog;
    /**
     * Current activity
     */
    private Activity mActivity;
    /**
     * Data
     */
    private D mData;

    private static final String KEY_DATA_POSTFIX = ".KEY_DATA";

    /**
     * initializes / restores instance state
     * call this in corresponding lifecycle method of activity / fragment
     * @param savedInstanceState savedInstanceState
     * @param activity activity instance
     */
    public void onCreate(Bundle savedInstanceState, Activity activity) {
        mActivity = activity;
        mRecreateDialog = LifecycleUtils.tryRemoveFragment(activity, getDialogTag());
        if (savedInstanceState != null && savedInstanceState.containsKey(getSaveStateKey())) {
            mData = savedInstanceState.getParcelable(getSaveStateKey());
        }
    }

    /**
     * recreates dialog if needed
     * call this in corresponding lifecycle method of activity / fragment*
     */
    public void onResume() {
        if (mRecreateDialog) {
            showDialog(mData);
        }
    }

    /**
     * saves state for dialog
     * @param savedInstanceState savedInstanceState
     */
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putParcelable(getSaveStateKey(), mData);
    }

    /**
     * show dialog
     * @param data data, that is needed in dialog listener callbacks
     */
    public void showDialog(D data) {
        mData = data;
        DialogFragment dialogInstance = createDialogInstance();
        dialogInstance.show(mActivity.getFragmentManager(), getDialogTag());
    }

    protected D getData() {
        return mData;
    }

    private String getSaveStateKey() {
        return getDialogTag() + KEY_DATA_POSTFIX;
    }

    /**
     * Provide dialog instance to show
     * @return dialog instance
     */
    protected abstract DialogFragment createDialogInstance();

    /**
     * Get unique identifier for dialog instance
     * @return unique id
     */
    protected abstract String getDialogTag();

}
