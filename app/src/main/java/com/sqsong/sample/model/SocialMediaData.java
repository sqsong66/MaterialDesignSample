package com.sqsong.sample.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 青松 on 2016/12/16.
 */

public class SocialMediaData implements Parcelable {

    private String socialName;
    private int socialIcon;

    public SocialMediaData() {}

    protected SocialMediaData(Parcel in) {
        socialName = in.readString();
        socialIcon = in.readInt();
    }

    public static final Creator<SocialMediaData> CREATOR = new Creator<SocialMediaData>() {
        @Override
        public SocialMediaData createFromParcel(Parcel in) {
            return new SocialMediaData(in);
        }

        @Override
        public SocialMediaData[] newArray(int size) {
            return new SocialMediaData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(socialName);
        parcel.writeInt(socialIcon);
    }

    public String getSocialName() {
        return socialName;
    }

    public void setSocialName(String socialName) {
        this.socialName = socialName;
    }

    public int getSocialIcon() {
        return socialIcon;
    }

    public void setSocialIcon(int socialIcon) {
        this.socialIcon = socialIcon;
    }
}
