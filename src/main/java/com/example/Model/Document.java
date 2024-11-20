package com.example.Model;

public class Document {
    private int documentId;
    private String title;
    private int year;
    private String genre;
    private String ISBN;
    private String content;
    private String author;
    private String urlImage;  // Thêm thuộc tính urlImage
    private int numberCopy;
    private String description;
    public Document() {

    }
    public Document(int documentId, String title, String ISBN, String author, String urlImage, String genre, int numberCopy, String description) {
        this.documentId = documentId;
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;
        this.numberCopy = numberCopy;
        this.description = description;
    }
    public Document(String title, String author, String genre,int numberCopy ,  String ISBN,  String urlImage) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;
        this.numberCopy = numberCopy;
    }
    public Document(String title, String author, String genre,String description,  String urlImage) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;

    }


    public Document(int documentId, String title) {
        this.documentId = documentId;
        this.title = title;
    }

    public Document(int documentId,String title,String author, String genre) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }
    public Document(String title,String author,String genre,String urlImage) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.urlImage = urlImage;
    }




    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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


    public void setDocumentId(int documentId) {
        this.documentId = documentId;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public int getNumberCopy() {
        return numberCopy;
    }

    public void setNumberCopy(int numberCopy) {
        this.numberCopy = numberCopy;
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
