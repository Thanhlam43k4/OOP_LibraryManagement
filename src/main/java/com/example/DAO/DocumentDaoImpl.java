package com.example.DAO;

import com.example.Interface.DocumentDao;
import com.example.Model.Copies;
import com.example.Model.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImpl implements DocumentDao {
    private final Connection con;

    public DocumentDaoImpl(Connection con) {
        this.con = con;

    }

    @Override
    public void addDocument(Document doc) {
        String query = "INSERT INTO documents (title, year, genre) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, doc.getTitle());
            pstmt.setInt(2, doc.getYear());
            pstmt.setString(3, doc.getGenre());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Document getDocumentById(int documentId) {
        String query = "SELECT * FROM documents WHERE documentId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, documentId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return new Document(
                        rs.getInt("documentId"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Document> getAllDocuments() {
        List<Document> documents = new ArrayList<>();
        String query = "SELECT * FROM documents";
        try (Statement stmt = con.createStatement();
                ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Document doc = new Document(
                        rs.getInt("documentId"),
                        rs.getString("title"),
                        rs.getString("ISBN"),
                        rs.getString("author"),
                        rs.getString("urlImage"),
                        rs.getString("genre"),
                        rs.getInt("number_of_copies"),
                        rs.getString("description"));
                documents.add(doc);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    @Override
    public List<Copies> getAllCopies(int documentId) {
        List<Copies> copies = new ArrayList<>();
        String query = "SELECT c.*, d.title " +
                "FROM copies c " +
                "JOIN documents d ON c.document_id = d.documentId " +
                "WHERE c.document_id = ?";
        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Copies copy = new Copies(
                        rs.getInt("documentId"),
                        rs.getString("title"),
                        rs.getString("copies_ISBN"),
                        rs.getString("status"));
                copies.add(copy);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }
    @Override
    public void updateDocument(Document doc) {
        String query = "UPDATE documents SET title = ?, year = ?, genre = ? WHERE documentId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, doc.getTitle());
            pstmt.setInt(2, doc.getYear());
            pstmt.setString(3, doc.getGenre());
            pstmt.setInt(4, doc.getDocumentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteDocument(int documentId) {
        String query = "DELETE FROM documents WHERE documentId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, documentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
