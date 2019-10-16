package com.faisaljaved.myapp;

class Cars {

    private String mImageId;
    private String mTitle;
    private String mSubtitle;

    public Cars(String title, String subtitle, String imageResourceId){

        mTitle = title;
        mSubtitle = subtitle;
        mImageId = imageResourceId;
    }

    public String getmImageId(){
        return mImageId;
    }

    public String getmTitle(){
        return mTitle;
    }

    public String getmSubtitle(){
        return mSubtitle;
    }

}
