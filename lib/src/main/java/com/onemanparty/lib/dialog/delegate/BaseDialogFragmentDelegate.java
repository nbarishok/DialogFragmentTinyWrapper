package com.onemanparty.lib.dialog.delegate;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.os.Parcelable;

import com.onemanparty.lib.dialog.LifecycleUtils;

import java.util.UUID;


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
    private String mId;

    private static final String KEY_DATA = "KEY_DATA";
    private static final String KEY_ID = "KEY_ID";

    /**
     * initializes / restores instance state
     * call this in corresponding lifecycle method of activity / fragment
     * @param savedInstanceState savedInstanceState
     * @param activity activity instance
     */
    public void onCreate(Bundle savedInstanceState, Activity activity) {
        mActivity = activity;
        if (savedInstanceState == null) {
            mId = UUID.randomUUID().toString();
        } else {
            mId = savedInstanceState.getString(KEY_ID);
            if (savedInstanceState.containsKey(KEY_DATA)) {
                mData = savedInstanceState.getParcelable(KEY_DATA);
            }
        }
        mRecreateDialog = LifecycleUtils.tryRemoveFragment(activity, mId);
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
        savedInstanceState.putParcelable(KEY_DATA, mData);
        savedInstanceState.putString(KEY_ID, mId);
    }

    /**
     * show dialog
     * @param data data, that is needed in dialog listener callbacks
     */
    public void showDialog(D data) {
        mData = data;
        DialogFragment dialogInstance = createDialogInstance();
        dialogInstance.show(mActivity.getFragmentManager(), mId);
    }

    protected D getData() {
        return mData;
    }

    /**
     * Provide dialog instance to show
     * @return dialog instance
     */
    protected abstract DialogFragment createDialogInstance();

}
