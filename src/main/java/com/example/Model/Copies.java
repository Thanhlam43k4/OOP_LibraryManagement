package com.example.Model;

public class Copies extends Document{
    private String copies_ISBN;
    private String status;

    public Copies() {

    }
    public Copies(int documentId,String title,String copies_ISBN,String status) {
        super(documentId,title);
        this.copies_ISBN = copies_ISBN;
        this.status = status;
    }
    public Copies(String copies_ISBN,String status) {
        this.copies_ISBN = copies_ISBN;
        this.status = status;
    }
    public Copies(String copies_ISBN,int documentId,String status) {
        super(documentId);
        this.copies_ISBN = copies_ISBN;
        this.status = status;
    }

    public void setCopies_ISBN(String copies_ISBN) {
        this.copies_ISBN = copies_ISBN;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Copies{" +
                "documentId=" + super.getDocumentId() +
                ", title='" + super.getTitle() + '\'' +
                ", copyISBN='" + copies_ISBN + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    public String getCopyISBN() {
        return copies_ISBN;
    }
}
