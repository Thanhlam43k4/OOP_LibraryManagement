package com.example.Interface;

import com.example.Model.Copies;
import com.example.Model.Document;

import java.util.List;

/**
 * This interface defines the data access methods for managing documents and copies of documents.
 * It provides CRUD operations (Create, Read, Update, Delete) for documents, as well as methods
 * for checking document availability and retrieving document details.
 */
public interface DocumentDao {

    /**
     * Adds a new document to the system.
     *
     * @param doc The document to add.
     */
    void addDocument(Document doc);

    /**
     * Retrieves a list of all documents in the system.
     *
     * @return A list of all documents.
     */
    List<Document> getAllDocuments();

    /**
     * Retrieves a list of all copies of a specific document.
     *
     * @param documentId The ID of the document to retrieve copies for.
     * @return A list of copies for the specified document.
     */
    List<Copies> getAllCopies(int documentId);

    /**
     * Updates the details of an existing document.
     *
     * @param doc The document with updated details.
     */
    void updateDocument(Document doc);

    /**
     * Deletes a document from the system.
     *
     * @param documentId The ID of the document to delete.
     */
    void deleteDocument(int documentId);

    /**
     * Checks if a book (document) is available based on its ISBN.
     *
     * @param copyISBN The ISBN of the book to check availability for.
     * @return {@code true} if the book is available, {@code false} otherwise.
     */
    boolean isBookAvailable(String copyISBN);

    /**
     * Retrieves a document by its ISBN.
     *
     * @param ISBN The ISBN of the document to retrieve.
     * @return The document with the specified ISBN, or {@code null} if not found.
     */
    Document getDocumentByISBN(String ISBN);

    /**
     * Checks if a document is available based on its ISBN.
     *
     * @param docISBN The ISBN of the document to check.
     * @return {@code true} if the document is available, {@code false} otherwise.
     */
    boolean isDocAvailable(String docISBN);

    /**
     * Retrieves the available copies of a document based on its document ID.
     *
     * @param documentId The ID of the document to retrieve available copies for.
     * @return The available copies for the specified document.
     */
    Copies getAvailCopies(int documentId);

    /**
     *
     * @param ISBN th ISBN of the Document
     * @return ${@code true} ISBN is existed
     */
    boolean isIsbnExist(String ISBN);
}
