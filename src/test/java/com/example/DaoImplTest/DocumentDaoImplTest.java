package com.example.DaoImplTest;



import com.example.Model.Copies;
import com.example.Model.Document;
import com.example.DAO.DocumentDaoImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class DocumentDaoImplTest {

    @Mock
    private Connection mockConnection;

    @Mock
    private Statement mockStatement;
    @Mock
    private PreparedStatement mockPreparedStatement;
    @Mock
    private ResultSet mockResultSet;

    private DocumentDaoImpl documentDao;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.openMocks(this);
        documentDao = new DocumentDaoImpl(mockConnection);
    }

    @Test
    void testGetAllDocuments() throws SQLException {
        // Giả lập hành vi của connection và statement
        when(mockConnection.createStatement()).thenReturn(mockStatement);
        when(mockStatement.executeQuery("SELECT * FROM documents")).thenReturn(mockResultSet);

        // Giả lập kết quả của ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // 2 rows
        when(mockResultSet.getInt("documentId")).thenReturn(1, 2);
        when(mockResultSet.getString("title")).thenReturn("Title1", "Title2");
        when(mockResultSet.getString("ISBN")).thenReturn("12345", "67890");
        when(mockResultSet.getString("author")).thenReturn("Author1", "Author2");
        when(mockResultSet.getString("urlImage")).thenReturn("URL1", "URL2");
        when(mockResultSet.getString("genre")).thenReturn("Genre1", "Genre2");
        when(mockResultSet.getInt("number_of_copies")).thenReturn(10, 5);
        when(mockResultSet.getString("description")).thenReturn("Description1", "Description2");

        // Gọi phương thức DAO
        List<Document> documents = documentDao.getAllDocuments();

        // Kiểm tra kết quả
        assertNotNull(documents);
        assertEquals(2, documents.size());
        assertEquals("Title1", documents.get(0).getTitle());
        assertEquals("Author2", documents.get(1).getAuthor());

        // Đảm bảo các phương thức JDBC được gọi đúng cách
        verify(mockConnection, times(1)).createStatement();
        verify(mockStatement, times(1)).executeQuery("SELECT * FROM documents");
        verify(mockResultSet, times(3)).next(); // 2 rows và một lần kết thúc
    }
    @Test
    void testGetAllCopies() throws SQLException {
        int documentId = 1; // ID của tài liệu bạn muốn lấy các bản sao

        // Giả lập hành vi của connection và prepared statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Giả lập kết quả của ResultSet
        when(mockResultSet.next()).thenReturn(true, true, false); // 2 rows
        when(mockResultSet.getInt("document_id")).thenReturn(1, 1);
        when(mockResultSet.getString("title")).thenReturn("Title1", "Title1");
        when(mockResultSet.getString("copy_ISBN")).thenReturn("1234567890", "0987654321");
        when(mockResultSet.getString("status")).thenReturn("Available", "Checked Out");

        // Gọi phương thức DAO
        List<Copies> copies = documentDao.getAllCopies(documentId); // Đảm bảo bạn gọi phương thức qua đối tượng instance

        // Kiểm tra kết quả
        assertNotNull(copies); // Kiểm tra xem danh sách không null
        assertEquals(2, copies.size()); // Kiểm tra số lượng bản sao (2)
        assertEquals("1234567890", copies.get(0).getCopyIsbn()); // Kiểm tra ISBN bản sao đầu tiên
        assertEquals("Checked Out", copies.get(1).getStatus()); // Kiểm tra trạng thái bản sao thứ hai

        // Kiểm tra các phương thức JDBC được gọi đúng cách
        verify(mockConnection, times(1)).prepareStatement(anyString()); // Đảm bảo prepareStatement() được gọi một lần
        verify(mockPreparedStatement, times(1)).setInt(1, documentId); // Đảm bảo setInt() được gọi với documentId
        verify(mockPreparedStatement, times(1)).executeQuery(); // Đảm bảo executeQuery() được gọi đúng một lần
        verify(mockResultSet, times(3)).next(); // Đảm bảo next() được gọi 3 lần (2 bản sao và 1 lần kết thúc)
    }


    @Test
    void testUpdateDocument() throws SQLException {
        // Tạo đối tượng Document giả lập
        Document doc = new Document(1, "Updated Title", "Updated Author",  "Updated Image", "Updated Genre", "Updated description");

        // Giả lập hành vi của connection và prepared statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Gọi phương thức updateDocument()
        documentDao.updateDocument(doc);

        // Kiểm tra xem phương thức setString và setInt đã được gọi đúng với các tham số không
        verify(mockConnection, times(1)).prepareStatement(anyString()); // Đảm bảo prepareStatement được gọi đúng 1 lần
        verify(mockPreparedStatement, times(1)).setString(1, doc.getTitle()); // Đảm bảo setString được gọi với title
        verify(mockPreparedStatement, times(1)).setString(2, doc.getAuthor()); // Đảm bảo setString được gọi với author
        verify(mockPreparedStatement, times(1)).setString(3, doc.getGenre()); // Đảm bảo setString được gọi với genre
        verify(mockPreparedStatement, times(1)).setString(4, doc.getDescription()); // Đảm bảo setString được gọi với description
        verify(mockPreparedStatement, times(1)).setString(5, doc.getUrlImage()); // Đảm bảo setString được gọi với urlImage
        verify(mockPreparedStatement, times(1)).setInt(6, doc.getDocumentId()); // Đảm bảo setInt được gọi với documentId
        verify(mockPreparedStatement, times(1)).executeUpdate(); // Đảm bảo executeUpdate được gọi 1 lần
    }

    @Test
    void testDeleteDocument() throws SQLException {
        int documentId = 1; // ID của tài liệu sẽ được xóa

        // Giả lập hành vi của connection và prepared statement
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

        // Gọi phương thức deleteDocument()
        documentDao.deleteDocument(documentId);

        // Kiểm tra xem phương thức setInt và executeUpdate đã được gọi đúng với tham số không
        verify(mockConnection, times(1)).prepareStatement(anyString()); // Đảm bảo prepareStatement được gọi đúng 1 lần
        verify(mockPreparedStatement, times(1)).setInt(1, documentId); // Đảm bảo setInt được gọi với documentId
        verify(mockPreparedStatement, times(1)).executeUpdate(); // Đảm bảo executeUpdate được gọi 1 lần
    }

    @Test
    void testIsBookNotAvailable() throws SQLException {
        String ISBN = "67890"; // ISBN của cuốn sách không có sẵn

        // Giả lập hành vi của connection, prepared statement và result set
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Giả lập có một dòng kết quả
        when(mockResultSet.getBoolean("is_available")).thenReturn(false); // Giả lập rằng sách không có sẵn

        // Gọi phương thức isBookAvailable
        boolean isAvailable = documentDao.isBookAvailable(ISBN);

        // Kiểm tra kết quả
        assertFalse(isAvailable); // Kiểm tra xem kết quả có đúng là false hay không

        // Đảm bảo rằng phương thức SQL được gọi đúng
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).setString(1, ISBN);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getBoolean("is_available");
    }


    @Test
    void testGetDocumentByISBN() throws SQLException {
        String ISBN = "12345"; // ISBN của cuốn sách cần tìm

        // Giả lập hành vi của connection, prepared statement và result set
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);
        when(mockResultSet.next()).thenReturn(true); // Giả lập có một dòng kết quả
        when(mockResultSet.getInt("documentId")).thenReturn(1); // Giả lập trả về documentId
        when(mockResultSet.getString("title")).thenReturn("Book Title"); // Giả lập trả về title
        when(mockResultSet.getString("author")).thenReturn("Author Name"); // Giả lập trả về author
        when(mockResultSet.getString("genre")).thenReturn("Fiction"); // Giả lập trả về genre

        // Gọi phương thức getDocumentByISBN
        Document document = documentDao.getDocumentByISBN(ISBN);

        // Kiểm tra kết quả
        assertNotNull(document); // Đảm bảo rằng đối tượng Document không null
        assertEquals(1, document.getDocumentId()); // Kiểm tra documentId
        assertEquals("Book Title", document.getTitle()); // Kiểm tra title
        assertEquals("Author Name", document.getAuthor()); // Kiểm tra author
        assertEquals("Fiction", document.getGenre()); // Kiểm tra genre

        // Đảm bảo rằng phương thức SQL được gọi đúng
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).setString(1, ISBN);
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getInt("documentId");
        verify(mockResultSet, times(1)).getString("title");
        verify(mockResultSet, times(1)).getString("author");
        verify(mockResultSet, times(1)).getString("genre");
    }

    @Test
    void testGetAvailCopies() throws SQLException {
        int documentId = 1; // ID của tài liệu muốn lấy bản sao khả dụng

        // Giả lập hành vi của connection, prepared statement và result set
        when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
        when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

        // Giả lập kết quả của ResultSet
        when(mockResultSet.next()).thenReturn(true); // Giả lập có một dòng kết quả
        when(mockResultSet.getString("copy_ISBN")).thenReturn("1234567890"); // ISBN của bản sao
        when(mockResultSet.getString("status")).thenReturn("Available"); // Trạng thái bản sao

        // Gọi phương thức getAvailCopies
        Copies copy = documentDao.getAvailCopies(documentId);

        // Kiểm tra kết quả
        assertNotNull(copy); // Kiểm tra xem bản sao có được trả về hay không
        assertEquals("1234567890", copy.getCopyIsbn()); // Kiểm tra ISBN bản sao
        assertEquals("Available", copy.getStatus()); // Kiểm tra trạng thái bản sao
        assertEquals(documentId, copy.getDocumentId()); // Kiểm tra documentId

        // Đảm bảo rằng phương thức SQL được gọi đúng
        verify(mockConnection, times(1)).prepareStatement(anyString());
        verify(mockPreparedStatement, times(1)).setInt(1, documentId); // Đảm bảo setInt được gọi với documentId
        verify(mockPreparedStatement, times(1)).executeQuery();
        verify(mockResultSet, times(1)).next();
        verify(mockResultSet, times(1)).getString("copy_ISBN");
        verify(mockResultSet, times(1)).getString("status");
    }

}