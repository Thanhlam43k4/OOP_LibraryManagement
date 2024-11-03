package com.example.Service;

import com.example.Interface.DocumentDao;
import com.example.DAO.DocumentDaoImpl;
import com.example.Model.Copies;
import com.example.Model.Document;

import java.sql.Connection;
import java.util.List;

public class DocumentService {
    public static DocumentService instance;
    private DocumentDao  documentDao;

    public DocumentService(Connection con) {
        documentDao = new DocumentDaoImpl(con);
    }

    public void addDocument(Document document) {
        documentDao.addDocument(document);
    }
    public Document getDocumentById(int documentId) {
        return  documentDao.getDocumentById(documentId);
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
    public boolean isBookAvailable(String ISBN) {
        return documentDao.isBookAvailable(ISBN);
    }
}
