package com.example.Model;

/**
 * The {@code Client} class represents a client user in the system.
 * It extends the {@link User} class and provides additional functionality
 * for managing borrowed books.
 *
 * @see User
 */
public class Client extends User {
    private int borrowedBook;

    /**
     * Constructs a {@code Client} object with the specified email and password.
     *
     * @param email    the email of the client
     * @param password the password of the client
     */
    public Client(String email, String password) {
        super(email, password);
    }

    /**
     * Constructs a {@code Client} object with the specified details.
     *
     * @param id           the unique identifier of the client
     * @param username     the username of the client
     * @param email        the email of the client
     * @param age          the age of the client
     * @param phoneNumber  the phone number of the client
     * @param borrowedBook the number of borrowed books by the client
     */
    public Client(int id, String username, String email, int age, String phoneNumber, int borrowedBook) {
        super(id, email, username, age, phoneNumber);
        this.borrowedBook = borrowedBook;
    }

    /**
     * Constructs a {@code Client} object with the specified email, username,
     * phone number, and age.
     *
     * @param email       the email of the client
     * @param username    the username of the client
     * @param phoneNumber the phone number of the client
     * @param age         the age of the client
     */
    public Client(String email, String username, String phoneNumber, int age) {
        super(email, username, phoneNumber, age);
    }

    /**
     * Default constructor for the {@code Client} object.
     */
    public Client() {
        super();
    }

    /**
     * Gets the number of borrowed books by the client.
     *
     * @return the number of borrowed books
     */
    public int getBorrowedBook() {
        return borrowedBook;
    }

    /**
     * Sets the number of borrowed books for the client.
     *
     * @param borrowedBook the number of borrowed books
     */
    public void setBorrowedBook(int borrowedBook) {
        this.borrowedBook = borrowedBook;
    }

    /**
     * {@inheritDoc}
     * <p>
     * Returns the role of the user as "client".
     * </p>
     *
     * @return the role of the user, which is "client"
     */
    @Override
    public String getRole() {
        return "client";
    }
}
