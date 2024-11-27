package com.example.Service;

import com.example.Interface.DocumentDao;
import com.example.DAO.DocumentDaoImpl;
import com.example.Model.Copies;
import com.example.Model.Document;

import java.sql.Connection;
import java.util.List;

public class DocumentService {
    public static DocumentService instance;
    private final DocumentDao  documentDao;

    public DocumentService(Connection con) {
        documentDao = new DocumentDaoImpl(con);
    }

    public void addDocument(Document document) {
        documentDao.addDocument(document);
    }
    public List<Document> getAllDocument(){

        return documentDao.getAllDocuments();

    }
    public void updateDocument(Document document){
        documentDao.updateDocument(document);

    }
    public void deleteDocument(int documentId){

        documentDao.deleteDocument(documentId);

    }

    public List<Copies> getAllCopies(int documentId){
        return documentDao.getAllCopies(documentId);
    }
    public boolean isBookAvailable(String copyISBN) {
        return documentDao.isBookAvailable(copyISBN);
    }
    public Document getDocumentByISBN(String docISBN) {
        return documentDao.getDocumentByISBN(docISBN);
    }

    public boolean isDocAvailable(String isbn) {
        return documentDao.isDocAvailable(isbn);
    }
    public Copies getAvailCopies(int docId) {
        return documentDao.getAvailCopies(docId);
    }

    public boolean isIsbnExist(String isbn) {
        return documentDao.isIsbnExist(isbn);
    }
}
