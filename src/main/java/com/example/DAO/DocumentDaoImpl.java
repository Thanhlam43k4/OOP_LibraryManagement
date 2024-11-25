package com.example.DAO;

import com.example.Interface.DocumentDao;
import com.example.Model.Copies;
import com.example.Model.Document;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DocumentDaoImpl implements DocumentDao {
    private final Connection connection;

    /**
     * Constructor để khởi tạo đối tượng DocumentDaoImpl với một kết nối cơ sở dữ liệu.
     *
     * @param connection kết nối cơ sở dữ liệu.
     */
    public DocumentDaoImpl(Connection connection) {
        this.connection = connection;
    }

    /**
     * Thêm một tài liệu mới vào cơ sở dữ liệu.
     *
     * @param document đối tượng Document chứa thông tin tài liệu cần thêm.
     */
    @Override
    public void addDocument(Document document) {
        final String query = "INSERT INTO documents (title, author, genre, ISBN, number_of_copies, urlImage, description) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getAuthor());
            pstmt.setString(3, document.getGenre());
            pstmt.setString(4, document.getISBN());
            pstmt.setInt(5, document.getNumberCopy());
            pstmt.setString(6, document.getUrlImage());
            pstmt.setString(7, document.getDescription());
            pstmt.executeUpdate();
            System.out.println("Add Document Successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Lấy tất cả tài liệu từ cơ sở dữ liệu.
     *
     * @return danh sách các tài liệu.
     */
    @Override
    public List<Document> getAllDocuments() {
        final String query = "SELECT * FROM documents";
        List<Document> documents = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Document document = new Document(
                        rs.getInt("documentId"),
                        rs.getString("title"),
                        rs.getString("ISBN"),
                        rs.getString("author"),
                        rs.getString("urlImage"),
                        rs.getString("genre"),
                        rs.getInt("number_of_copies"),
                        rs.getString("description")
                );
                documents.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return documents;
    }

    /**
     * Lấy tất cả bản sao của một tài liệu dựa trên documentId.
     *
     * @param documentId ID của tài liệu cần lấy bản sao.
     * @return danh sách các bản sao của tài liệu.
     */
    @Override
    public List<Copies> getAllCopies(int documentId) {
        final String query = "SELECT c.document_id, d.title, c.copy_ISBN, c.status " +
                "FROM copies c " +
                "JOIN documents d ON c.document_id = d.documentId " +
                "WHERE c.document_id = ?";
        List<Copies> copies = new ArrayList<>();
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, documentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Copies copy = new Copies(
                            rs.getInt("document_id"),
                            rs.getString("title"),
                            rs.getString("copy_ISBN"),
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

    /**
     * Cập nhật thông tin tài liệu trong cơ sở dữ liệu.
     *
     * @param document đối tượng Document chứa thông tin tài liệu cần cập nhật.
     */
    @Override
    public void updateDocument(Document document) {
        final String query = "UPDATE documents SET title = ?, author = ?, genre = ?, description = ?, urlImage = ? WHERE documentId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, document.getTitle());
            pstmt.setString(2, document.getAuthor());
            pstmt.setString(3, document.getGenre());
            pstmt.setString(4, document.getDescription());
            pstmt.setString(5, document.getUrlImage());
            pstmt.setInt(6, document.getDocumentId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Xóa một tài liệu khỏi cơ sở dữ liệu dựa trên documentId.
     *
     * @param documentId ID của tài liệu cần xóa.
     */
    @Override
    public void deleteDocument(int documentId) {
        final String query = "DELETE FROM documents WHERE documentId = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, documentId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Kiểm tra xem sách có sẵn trong cơ sở dữ liệu hay không dựa trên ISBN.
     *
     * @param isbn ISBN của sách cần kiểm tra.
     * @return true nếu sách có sẵn, false nếu không.
     */
    @Override
    public boolean isBookAvailable(String isbn) {
        final String query = "SELECT COUNT(*) > 0 AS is_available FROM copies WHERE copy_ISBN = ? AND status = 'Available'";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_available");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy thông tin tài liệu dựa trên ISBN.
     *
     * @param isbn ISBN của tài liệu cần lấy.
     * @return tài liệu có ISBN tương ứng, hoặc null nếu không tìm thấy.
     */
    @Override
    public Document getDocumentByISBN(String isbn) {
        final String query = "SELECT * FROM documents WHERE ISBN = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return new Document(
                            rs.getInt("documentId"),
                            rs.getString("title"),
                            rs.getString("author"),
                            rs.getString("genre"),
                            rs.getString("urlImage")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Kiểm tra xem tài liệu có đang trong giao dịch hay không.
     *
     * @param isbn ISBN của tài liệu cần kiểm tra.
     * @return true nếu tài liệu đang trong giao dịch, false nếu không.
     */
    @Override
    public boolean isDocAvailable(String isbn) {
        final String query = "SELECT COUNT(*) > 0 AS is_in_transaction " +
                "FROM copies c " +
                "JOIN documents d ON c.document_id = d.documentId " +
                "JOIN transactions t ON c.copy_ISBN = t.copy_ISBN " +
                "WHERE d.ISBN = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, isbn);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("is_in_transaction");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Lấy bản sao có sẵn của một tài liệu.
     *
     * @param documentId ID của tài liệu cần lấy bản sao.
     * @return bản sao có trạng thái 'Available', hoặc null nếu không có bản sao nào.
     */
    @Override
    public Copies getAvailCopies(int documentId) {
        final String query = "SELECT copy_ISBN, status FROM copies WHERE document_id = ? AND status = 'Available' LIMIT 1";
        Copies copy = null;
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, documentId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    copy = new Copies(
                            rs.getString("copy_ISBN"),
                            documentId,
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
