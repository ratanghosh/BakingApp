package com.example.android.bakingapp.model;


import com.example.android.bakingapp.Constant;
import com.google.gson.annotations.SerializedName;

public class RecipeStep {

    @SerializedName(Constant.JSON_ID)
    private int stepId;
    @SerializedName(Constant.SHORT_DESCRIPTION)
    private String shortDescription;
    @SerializedName(Constant.DESCRIPTION)
    private String description;
    @SerializedName(Constant.VIDEO_URL)
    private String videoUrl;
    @SerializedName(Constant.THUMBNAIL_URL)
    private String thumbnailUrl;

    public RecipeStep() {

    }

    public RecipeStep(int stepId, String shortDescription, String description, String videoUrl, String thumbnailUrl) {
        this.stepId = stepId;
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getStepId() {
        return stepId;
    }

    public void setStepId(int stepId) {
        this.stepId = stepId;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
