package com.themattburton.cs490.finalproject;

public class SearchWordCardModel {
    private String searchWord;
    private boolean isFound;

    public SearchWordCardModel(String searchWord) {
        this.searchWord = searchWord;
    }

    public String getSearchWord() {
        return searchWord;
    }

    public void setSearchWord(String searchWord) {
        this.searchWord = searchWord;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }
}
