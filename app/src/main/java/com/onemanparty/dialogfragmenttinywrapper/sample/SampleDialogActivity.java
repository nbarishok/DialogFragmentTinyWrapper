package com.onemanparty.dialogfragmenttinywrapper.sample;

import android.os.Bundle;

import com.onemanparty.dialogfragmenttinywrapper.sample.view.BaseActivity;


/**
 * Sample activity
 */
public class SampleDialogActivity extends BaseActivity {

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            setSingleFragment(SampleDialogFragment.newInstance(), SampleDialogFragment.TAG);
        }
    }
}
