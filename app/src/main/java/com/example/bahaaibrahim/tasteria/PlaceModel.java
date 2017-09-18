package com.example.bahaaibrahim.tasteria;

/**
 * Created by Bahaa Ibrahim on 9/7/2017.
 */

public class PlaceModel {
    private String mCardImageURL;
    private String mPrimaryText, mSubText, mRateValue;

    public PlaceModel() {
    }

    public PlaceModel(String mCardImageURL, String mPrimaryText, String mSubText, String mRateValue) {
        this.mCardImageURL = mCardImageURL;
        this.mPrimaryText = mPrimaryText;
        this.mSubText = mSubText;
        this.mRateValue = mRateValue;
    }

    public void setmCardImageURL(String mCardImageURL) {
        this.mCardImageURL = mCardImageURL;
    }


    public void setmPrimaryText(String mPrimaryText) {
        this.mPrimaryText = mPrimaryText;
    }

    public void setmSubText(String mSubText) {
        this.mSubText = mSubText;
    }

    public void setmRateValue(String mRateValue) {
        this.mRateValue = mRateValue;
    }


    public String getmCardImageURL() {

        return mCardImageURL;
    }


    public String getmPrimaryText() {
        return mPrimaryText;
    }

    public String getmSubText() {
        return mSubText;
    }

    public String getmRateValue() {
        return mRateValue;
    }


}
