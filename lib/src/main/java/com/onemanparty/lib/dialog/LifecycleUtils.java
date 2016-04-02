package com.onemanparty.lib.dialog;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;

/**
 * Helper class to overcome activity / fragment lifecycle constraints
 */
public class LifecycleUtils {

    /**
     * Remove fragment
     * @param activity acticity
     * @param tag fragment tag to remove
     * @return true if was found or else
     */
    public static boolean tryRemoveFragment(Activity activity, String tag) {
        if (tag != null) {
            FragmentManager fragmentManager = activity.getFragmentManager();
            Fragment fragment = fragmentManager.findFragmentByTag(tag);
            if (fragment != null) {
                fragmentManager.beginTransaction().remove(fragment).commit();
                return true;
            }
        }
        return false;
    }

    public static boolean hasFragment(Activity activity, String tag) {
        if (tag == null) {
            return false;
        }
        FragmentManager fragmentManager = activity.getFragmentManager();
        Fragment fragment = fragmentManager.findFragmentByTag(tag);
        return fragment != null;
    }
}

