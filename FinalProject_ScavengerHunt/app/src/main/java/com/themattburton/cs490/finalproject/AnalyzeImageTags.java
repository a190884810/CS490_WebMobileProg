package com.themattburton.cs490.finalproject;

public class AnalyzeImageTags {
    private String name;
    private String confidence;

    public AnalyzeImageTags(String name, String confidence) {
        this.name = name;
        this.confidence = confidence;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfidence() {
        return confidence;
    }

    public void setConfidence(String confidence) {
        this.confidence = confidence;
    }
}
