package com.example.Model;

public class Document {
    private int documentId;
    private String title;
    private int year;
    private String genre;

    public Document() {

    }

    public Document(int documentId, String title, int year, String genre) {
        this.documentId = documentId;
        this.title = title;
        this.year = year;
        this.genre = genre;
    }

    public Document(String title, int year, String gener) {
        this.title = title;
        this.year = year;
        this.genre = gener;
    }

    public int getDocumentId() {
        return documentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void getDetails() {
        System.out.println("DocumentId: " + documentId + " Title: " + title + " Year: " + year + " Genre: "+ genre);
    }
    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                '}';
    }

}
