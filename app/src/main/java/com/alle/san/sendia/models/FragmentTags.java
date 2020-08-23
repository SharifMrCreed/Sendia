package com.alle.san.sendia.models;

import androidx.fragment.app.Fragment;

public class FragmentTags {

    private Fragment fragment;
    private String theTag;

    public FragmentTags(Fragment fragment, String theTag) {
        this.fragment = fragment;
        this.theTag = theTag;
    }

    public FragmentTags() {
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    public String getTheTag() {
        return theTag;
    }

    public void setTheTag(String theTag) {
        this.theTag = theTag;
    }
}
