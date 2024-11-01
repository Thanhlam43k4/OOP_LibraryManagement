package com.example.Interface;

import com.example.Model.Copies;
import com.example.Model.Document;

import java.util.List;

public interface DocumentDao {
    void addDocument(Document doc);

    Document getDocumentById(int documentId);

    List<Document> getAllDocuments();

    List<Copies> getAllCopies(int documentId);

    void updateDocument(Document doc);

    void deleteDocument(int documentId);

}
