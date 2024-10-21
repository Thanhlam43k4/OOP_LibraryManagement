package com.example.Interface;

import com.example.Model.Document;

import java.util.List;

public interface DocumentDao {
    void addDocument(Document doc);
    Document getDocumentById(int documentId);
    List<Document> getAllDocuments();
    void updateDocument(Document doc);
    void deleteDocument(int documentId);
}
