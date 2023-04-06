package com.example.ClientSocket;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;


@JsonIgnoreProperties(ignoreUnknown = true)
public class LibraryItem {

    int id;
    String itemType;
    String title;
    String author;
    Integer pages;
    String summaryDescription;

    String checkedOutBy;

    @JsonProperty("isCheckedOut")
    boolean isCheckedOut;
    public LibraryItem() {
    }

    public LibraryItem(int id, String itemType, String title, String author, Integer pages, String summaryDescription, Boolean isCheckedOut, String checkedOutBy) {
        this.id = id;
        this.itemType = itemType;
        this.title = title;
        this.author = author;
        this.pages = pages;
        this.summaryDescription = summaryDescription;
        this.isCheckedOut = isCheckedOut;
        this.checkedOutBy = checkedOutBy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getSummaryDescription() {
        return summaryDescription;
    }

    public void setSummaryDescription(String summaryDescription) {
        this.summaryDescription = summaryDescription;
    }
    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public String getCheckedOutBy() {
        if (checkedOutBy == null) {
            return "AVAILABLE";
        }
        return checkedOutBy;
    }

    public void setCheckedOutBy(String checkedOutBy) {
        this.checkedOutBy = checkedOutBy;
    }
}
