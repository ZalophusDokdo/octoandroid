package com.nairbspace.octoandroid.domain.model;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;
import com.google.gson.annotations.SerializedName;

@AutoValue
@AutoGson(autoValueClass = AutoValue_Event.class)
public abstract class Event {
    @Nullable @SerializedName("type") public abstract String type();
    @Nullable @SerializedName("payload") public abstract Object payload(); // No doc on payload so spit out as object
}
