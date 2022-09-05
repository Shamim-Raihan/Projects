package com.example.newspaperapp.services.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ArticlesObjectClass {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("excerpt")
    @Expose
    private String excerpt;
    @SerializedName("content")
    @Expose
    private String content;
    @SerializedName("tag")
    @Expose
    private String tag;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("videoUrl")
    @Expose
    private String videoUrl;
    @SerializedName("newsUrl")
    @Expose
    private String newsUrl;
    @SerializedName("author")
    @Expose
    private String author;
    @SerializedName("authorUrl")
    @Expose
    private String authorUrl;
    @SerializedName("source")
    @Expose
    private String source;
    @SerializedName("sourceUrl")
    @Expose
    private String sourceUrl;
    @SerializedName("publishedAt")
    @Expose
    private String publishedAt;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public void setExcerpt(String excerpt) {
        this.excerpt = excerpt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthorUrl() {
        return authorUrl;
    }

    public void setAuthorUrl(String authorUrl) {
        this.authorUrl = authorUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }
}
