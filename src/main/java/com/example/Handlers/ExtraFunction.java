package com.example.Handlers;

import java.util.Random;
import org.mindrot.jbcrypt.BCrypt;

/**
 * A utility class that provides extra functions for handling string manipulations
 * and generating a verification code based on a user's email.
 */
public class ExtraFunction {

    /**
     * Extracts the ISBN without the suffix part after the last dash.
     * For example, for ISBN "978-3-16-148410-0", it returns "978-3-16-148410".
     *
     * @param fullISBN The full ISBN string which may contain a suffix separated by a dash.
     * @return The extracted ISBN without the suffix.
     */
    public static String extractISBN(String fullISBN) {
        int lastDashIndex = fullISBN.lastIndexOf('-');
        if (lastDashIndex != -1) {
            return fullISBN.substring(0, lastDashIndex);
        }
        return fullISBN;
    }

    /**
     * Generates a 6-digit verification code based on the provided email address.
     * The verification code is generated using the hash of the email and a random number generator.
     *
     * @param email The email address used to generate the verification code.
     * @return A 6-digit string representing the generated verification code.
     */
    public static String generateVerificationCode(String email) {
        // Generate a hash code based on the email
        int hashCode = email.hashCode();

        // Convert the hash code to a string
        String hashString = String.valueOf(hashCode);

        // Create a random object based on the email hash code
        Random random = new Random(hashCode);

        // Generate a 6-digit verification code
        int verificationCode = random.nextInt(1000000); // 6 digits

        // Format the verification code as a 6-digit string
        return String.format("%06d", verificationCode);
    }


    /**
     * Mã hóa mật khẩu (hash password) sử dụng BCrypt.
     *
     * @param plainPassword Mật khẩu người dùng nhập vào.
     * @return Mật khẩu đã được mã hóa.
     */
    public static String encode(String plainPassword) {
        // Sử dụng BCrypt để tạo ra hash từ mật khẩu
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    /**
     * Kiểm tra mật khẩu người dùng nhập vào có khớp với mật khẩu đã mã hóa hay không.
     *
     * @param plainPassword Mật khẩu người dùng nhập vào.
     * @param hashedPassword Mật khẩu đã được mã hóa.
     * @return true nếu mật khẩu khớp, false nếu không.
     */
    public static boolean decode(String plainPassword, String hashedPassword) {
        // So sánh mật khẩu người dùng nhập vào với mật khẩu đã mã hóa
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }

}
