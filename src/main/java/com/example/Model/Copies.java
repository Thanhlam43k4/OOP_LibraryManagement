package com.example.Model;  // Đặt tên package theo chuẩn chữ thường

/**
 * The Copies class extends Document to represent copies of a document
 * with a unique ISBN and status.
 */
public class Copies extends Document {

    private String copyISBN;  // Dùng camelCase cho tên biến
    private String status;    // Dùng camelCase cho tên biến

    // Constructor mặc định
    public Copies() {
    }

    /**
     * Constructor để tạo một bản sao của tài liệu với ID, tên, ISBN và trạng thái.
     * @param documentId ID của tài liệu.
     * @param title Tiêu đề của tài liệu.
     * @param copyISBN ISBN của bản sao tài liệu.
     * @param status Trạng thái của bản sao (Available hoặc Checkout).
     */
    public Copies(int documentId, String title, String copyISBN, String status) {
        super(documentId, title);
        this.copyISBN = copyISBN;
        this.status = status;
    }

    /**
     * Constructor để tạo một bản sao của tài liệu với ISBN và trạng thái.
     * @param copyIsbn ISBN của bản sao.
     * @param status Trạng thái của bản sao (Available hoặc Checkout).
     */
    public Copies(String copyIsbn, String status) {
        this.copyISBN = copyIsbn;
        this.status = status;
    }

    /**
     * Constructor để tạo một bản sao của tài liệu với ID tài liệu, ISBN và trạng thái.
     * @param documentId ID của tài liệu.
     * @param copyISBN ISBN của bản sao.
     * @param status Trạng thái của bản sao (Available hoặc Checkout).
     */
    public Copies(String copyISBN, int documentId, String status) {
        super(documentId);
        this.copyISBN = copyISBN;
        this.status = status;
    }

    // Getter và Setter cho thuộc tính copyIsbn
    public String getCopyIsbn() {
        return copyISBN;
    }

    public void setCopyIsbn(String copyISBN) {
        this.copyISBN = copyISBN;
    }

    // Getter và Setter cho thuộc tính status
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * Trả về chuỗi đại diện cho đối tượng Copies.
     * @return Chuỗi mô tả đối tượng Copies.
     */
    @Override
    public String toString() {
        return "Copies{" +
                "documentId=" + super.getDocumentId() +
                ", title='" + super.getTitle() + '\'' +
                ", copyIsbn='" + copyISBN + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
