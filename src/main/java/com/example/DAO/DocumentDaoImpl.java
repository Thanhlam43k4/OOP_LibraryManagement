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
        String query = "INSERT INTO documents (title,author,genre,ISBN,number_of_copies,urlImage) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, doc.getTitle());
            pstmt.setString(2, doc.getAuthor());
            pstmt.setString(3, doc.getGenre());
            pstmt.setString(4, doc.getISBN());
            pstmt.setInt(5, doc.getNumberCopy());
            pstmt.setString(6,doc.getUrlImage());
            pstmt.executeUpdate();
            System.out.println("Add Document Successfully!!!!");
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
        String query = "SELECT c.document_id, d.title, c.copy_ISBN ,c.status " +
                "FROM copies c " +
                "JOIN documents d ON c.document_id = d.documentId " +
                "WHERE c.document_id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setInt(1, documentId);  // Thiết lập giá trị cho tham số đầu tiên (documentId)

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Copies copy = new Copies(
                            rs.getInt("document_id"), // Giả sử bạn có getter cho documentId
                            rs.getString("title"),
                            rs.getString("copy_ISBN"),  // Sửa lỗi chính tả từ "copies_ISBN" thành "copy_ISBN"
                            rs.getString("status")
                    );
                    copies.add(copy);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copies;
    }
    @Override
    public void updateDocument(Document doc) {
        String query = "UPDATE documents SET title = ?, year = ?, genre = ? , description = ? WHERE documentId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, doc.getTitle());
            pstmt.setInt(2, doc.getYear());
            pstmt.setString(3, doc.getGenre());
            pstmt.setString(4,doc.getDescription());
            pstmt.setInt(5, doc.getDocumentId());
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

    @Override
    public boolean isBookAvailable(String ISBN) {
        String query = "SELECT COUNT(*) > 0 AS is_available FROM copies WHERE copy_ISBN = ? AND status = 'Available'";
        try(PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1,ISBN);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_available"); // Trả về true nếu có sẵn, false nếu không
            }

        }catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Document getDocumentByISBN(String ISBN) {
        String query = "SELECT * " +
                "FROM documents d " +
                "WHERE ISBN = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, ISBN); // Set the ISBN parameter in the query
            ResultSet rs = pstmt.executeQuery(); // Execute the query
            if (rs.next()) {
                // Assuming Document has a constructor that accepts these fields
                int documentId = rs.getInt("documentId"); // Adjust based on your Document fields
                String title = rs.getString("title");
                String author = rs.getString("author");
                String genre = rs.getString("genre");
                // Add other fields as necessary

                return new Document(documentId,title,author,genre); // Return the Document object
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle SQL exceptions
        }
        return null; // Return null if no document found
    }
}
