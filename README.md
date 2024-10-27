#  To do List OOP Library Mangement App

*Thành Viên Nhóm*:

+ 22022212: Nguyễn Thành Lâm.
+ 22022196: Nguyễn Hữu Trọng Anh.
+ 22022163: Đỗ Nhất Anh.

**Frontend**

    Thiết kế thêm admin load database.

    Thiết kế thêm phần thêm sửa xóa admin , Thêm Info ISBN ở phần add.

    User Management Borrow thành book_borrowed.
    
    * Update phần mượn trả sách trong User Management TAB.

    Update Status mượn trả sách với actual Date UserName trong bản transaction.
    
    Viết thêm panel khi click vào document ở admin dashboard hiện thêm list copy.

**Controller:**

    Client: cần userId để query đến transaction trong hàm addDocElementNodes, load username.

    DocInfo: query doc description và check doc đã được mượn chưa ở hàm setInfo.

    TransCard: thiếu full.

    Admin: thiếu.

    Profile: chưa load Info.

    SignInUp: thiếu username.


**Class:**

    Admin && Client: Add 2 class
     + Client thì có borrowed_book.
     
        
    Client Click Borrow để mượn sách.

    Tách Thesis vs Books.

**Database Model**

    Bỏ Amount Ready của database documents thêm vào copies (Int) là tổng số copy.

    Thêm phần copies_document.

    Upload Database Model và kiểm tra kỹ lại các query injection trong db.

    Sửa lại các khóa chính của các bảng copies với admin và client.

**Service**

    Tao thêm service xử lý session manager để query.

    Xử lý transaction Service mỗi khi User Borrow Book.

    Viết add User, Email Username, PhoneNumber , Age.
