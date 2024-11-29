package com.example.Service;

import com.example.Interface.DocumentDao;
import com.example.DAO.DocumentDaoImpl;
import com.example.Model.Copies;
import com.example.Model.Document;

import java.sql.Connection;
import java.util.List;

/**
 * The DocumentService class provides business logic for managing documents.
 * It acts as an intermediary between the application's logic and the database
 * through the DocumentDao interface. The service contains methods for
 * adding, retrieving, updating, and deleting documents.
 *
 * <p>This service also provides functionality to check the availability of
 * books and manage their copies.</p>
 */
public class DocumentService {

    // Singleton instance for the service
    public static DocumentService instance;

    // DAO layer instance to interact with the database
    private final DocumentDao documentDao;

    /**
     * Constructs a DocumentService instance with the given database connection.
     *
     * @param con The database connection to be used by the service.
     */
    public DocumentService(Connection con) {
        documentDao = new DocumentDaoImpl(con);
    }

    /**
     * Adds a new document to the database.
     *
     * @param document The document to be added.
     */
    public void addDocument(Document document) {
        documentDao.addDocument(document);
    }

    /**
     * Retrieves all documents from the database.
     *
     * @return A list of all documents in the database.
     */
    public List<Document> getAllDocument() {
        return documentDao.getAllDocuments();
    }

    /**
     * Updates an existing document in the database.
     *
     * @param document The document to be updated.
     */
    public void updateDocument(Document document) {
        documentDao.updateDocument(document);
    }

    /**
     * Deletes a document from the database based on its ID.
     *
     * @param documentId The ID of the document to be deleted.
     */
    public void deleteDocument(int documentId) {
        documentDao.deleteDocument(documentId);
    }

    /**
     * Retrieves all copies of a specific document by its ID.
     *
     * @param documentId The ID of the document whose copies are to be retrieved.
     * @return A list of copies for the specified document.
     */
    public List<Copies> getAllCopies(int documentId) {
        return documentDao.getAllCopies(documentId);
    }

    /**
     * Checks if a specific book copy (identified by its ISBN) is available.
     *
     * @param copyISBN The ISBN of the book copy to check.
     * @return True if the book copy is available, false otherwise.
     */
    public boolean isBookAvailable(String copyISBN) {
        return documentDao.isBookAvailable(copyISBN);
    }

    /**
     * Retrieves a document from the database by its ISBN.
     *
     * @param docISBN The ISBN of the document to be retrieved.
     * @return The document with the specified ISBN, or null if not found.
     */
    public Document getDocumentByISBN(String docISBN) {
        return documentDao.getDocumentByISBN(docISBN);
    }

    /**
     * Checks if a document is available in the database based on its ISBN.
     *
     * @param isbn The ISBN of the document to check.
     * @return True if the document is available, false otherwise.
     */
    public boolean isDocAvailable(String isbn) {
        return documentDao.isDocAvailable(isbn);
    }

    /**
     * Retrieves the available copies of a document by its ID.
     *
     * @param docId The ID of the document whose available copies are to be retrieved.
     * @return The available copies of the document.
     */
    public Copies getAvailCopies(int docId) {
        return documentDao.getAvailCopies(docId);
    }

    /**
     * Checks if a document with the specified ISBN already exists in the database.
     *
     * @param isbn The ISBN of the document to check.
     * @return True if the document exists, false otherwise.
     */
    public boolean isIsbnExist(String isbn) {
        return documentDao.isIsbnExist(isbn);
    }
}
