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
    /**
     * Unique id for dialog
     * Initializes onCreate(...)
     */
    private String mTag;

    private static final String KEY_DATA = "KEY_DATA";

    public BaseDialogFragmentDelegate(String tag) {
        mTag = tag;
    }

    /**
     * initializes / restores instance state
     * call this in corresponding lifecycle method of activity / fragment
     * @param savedInstanceState savedInstanceState
     * @param activity activity instance
     */
    public void onCreate(Bundle savedInstanceState, Activity activity) {
        mActivity = activity;
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(getUniqueDataKey())) {
                mData = savedInstanceState.getParcelable(getUniqueDataKey());
            }
        }
        mRecreateDialog = LifecycleUtils.tryRemoveFragment(activity, mTag);
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
        if (mData != null) {
            savedInstanceState.putParcelable(getUniqueDataKey(), mData);
        }
    }

    /**
     * show dialog
     * @param data data, that is needed in dialog listener callbacks
     */
    public void showDialog(D data) {
        mData = data;
        DialogFragment dialogInstance = createDialogInstance();
        boolean isDialogShown = LifecycleUtils.hasFragment(mActivity, mTag);
        if (!isDialogShown) {
            dialogInstance.show(mActivity.getFragmentManager(), mTag);
            mRecreateDialog = false;
        }
    }

    protected D getData() {
        return mData;
    }

    /**
     * Provide dialog instance to show
     * @return dialog instance
     */
    protected abstract DialogFragment createDialogInstance();

    private String getUniqueDataKey() {return mTag + KEY_DATA; }
}
