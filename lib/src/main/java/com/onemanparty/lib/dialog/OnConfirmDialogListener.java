package com.onemanparty.lib.dialog;

import android.app.DialogFragment;

/**
 * Listener for {@link ConfirmDialogFragment} events
 */
public interface OnConfirmDialogListener {
    void onOkDialog(final DialogFragment dialogFragment);
    void onCancelDialog(final DialogFragment dialogFragment);
}
