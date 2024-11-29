package com.example.Model;

/**
 * The {@code Document} class represents a document in the system.
 * It contains various attributes such as the document ID, title, author, genre, ISBN,
 * URL of the image, number of copies, and a description.
 * Provides constructors to initialize a document in different ways.
 */
public class Document {
    private int documentId;
    private String title;
    private int year;
    private String genre;
    private String ISBN;
    private String author;
    private String urlImage;  // The URL of the image for the document
    private int numberCopy;
    private String description;

    /**
     * Default constructor for the {@code Document} class.
     */
    public Document() {
    }

    /**
     * Constructs a {@code Document} with the specified details.
     *
     * @param documentId the document ID
     * @param title      the title of the document
     * @param author     the author of the document
     * @param urlImage   the URL of the document's image
     * @param genre      the genre of the document
     * @param description the description of the document
     */
    public Document(int documentId, String title, String author, String urlImage, String genre, String description) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;
        this.description = description;
    }

    /**
     * Constructs a {@code Document} with the specified details.
     *
     * @param documentId the document ID
     * @param title      the title of the document
     * @param author     the author of the document
     * @param genre      the genre of the document
     * @param urlImage   the URL of the document's image
     */
    public Document(int documentId, String title, String author, String genre, String urlImage) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;
    }

    /**
     * Constructs a {@code Document} with the specified details, including ISBN, number of copies, and description.
     *
     * @param documentId  the document ID
     * @param title       the title of the document
     * @param ISBN        the ISBN of the document
     * @param author      the author of the document
     * @param urlImage    the URL of the document's image
     * @param genre       the genre of the document
     * @param numberCopy  the number of copies of the document
     * @param description the description of the document
     */
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

    /**
     * Constructs a {@code Document} with the specified details excluding the document ID.
     *
     * @param title       the title of the document
     * @param author      the author of the document
     * @param genre       the genre of the document
     * @param numberCopy  the number of copies of the document
     * @param ISBN        the ISBN of the document
     * @param urlImage    the URL of the document's image
     */
    public Document(String title, String author, String genre, int numberCopy, String ISBN, String urlImage) {
        this.title = title;
        this.ISBN = ISBN;
        this.author = author;
        this.urlImage = urlImage;
        this.genre = genre;
        this.numberCopy = numberCopy;
    }

    /**
     * Constructs a {@code Document} with the specified details excluding the document ID.
     *
     * @param title       the title of the document
     * @param author      the author of the document
     * @param ISBN        the ISBN of the document
     * @param description the description of the document
     * @param urlImage    the URL of the document's image
     */
    public Document(String title, String author, String ISBN, String description, String urlImage) {
        this.title = title;
        this.description = description;
        this.author = author;
        this.urlImage = urlImage;
        this.ISBN = ISBN;
    }

    /**
     * Constructs a {@code Document} with the specified document ID and title.
     *
     * @param documentId the document ID
     * @param title      the title of the document
     */
    public Document(int documentId, String title) {
        this.documentId = documentId;
        this.title = title;
    }

    /**
     * Constructs a {@code Document} with the specified document ID, title, author, and genre.
     *
     * @param documentId the document ID
     * @param title      the title of the document
     * @param author     the author of the document
     * @param genre      the genre of the document
     */
    public Document(int documentId, String title, String author, String genre) {
        this.documentId = documentId;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    /**
     * Constructs a {@code Document} with the specified title, author, ISBN, and URL image.
     *
     * @param title     the title of the document
     * @param author    the author of the document
     * @param ISBN      the ISBN of the document
     * @param urlImage  the URL of the document's image
     */
    public Document(String title, String author, String ISBN, String urlImage) {
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.urlImage = urlImage;
    }

    /**
     * Constructs a {@code Document} with the specified document ID.
     *
     * @param documentId the document ID
     */
    public Document(int documentId) {
        this.documentId = documentId;
    }

    // Getter and Setter Methods
    public int getDocumentId() {
        return documentId;
    }

    public void setDocumentId(int documentId) {
        this.documentId = documentId;
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

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Prints the details of the document.
     */
    public void getDetails() {
        System.out.println("DocumentId: " + documentId + " Title: " + title + " Year: " + year + " Genre: " + genre);
    }

    /**
     * Returns a string representation of the {@code Document} object.
     *
     * @return a string representation of the document
     */
    @Override
    public String toString() {
        return "Document{" +
                "documentId=" + documentId +
                ", title='" + title + '\'' +
                ", year=" + year +
                ", genre='" + genre + '\'' +
                ", ISBN='" + ISBN + '\'' +
                ", author='" + author + '\'' +
                ", urlImage='" + urlImage + '\'' +
                ", numberCopy=" + numberCopy +
                ", description='" + description + '\'' +
                '}';
    }
}
