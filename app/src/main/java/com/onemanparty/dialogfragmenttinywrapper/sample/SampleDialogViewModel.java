package com.onemanparty.dialogfragmenttinywrapper.sample;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Model for dialog used in {@link SampleDialogFragment}
 */
public class SampleDialogViewModel implements Parcelable {

    // state to be persisted during configuration change / low-memory
    private int state;

    public SampleDialogViewModel(int state) {
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.state);
    }

    private SampleDialogViewModel(Parcel in) {
        this.state = in.readInt();
    }

    public static final Creator<SampleDialogViewModel> CREATOR = new Creator<SampleDialogViewModel>() {
        public SampleDialogViewModel createFromParcel(Parcel source) {
            return new SampleDialogViewModel(source);
        }

        public SampleDialogViewModel[] newArray(int size) {
            return new SampleDialogViewModel[size];
        }
    };
}
