package session;

public class user {
    private static String username; 
    private static String role;    

    public static String getUsername() {
        return username;
    }

    public static void setUsername(String username) {
        user.username = username;
    }

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        user.role = role;
    }

    // Xóa thông tin session (khi đăng xuất)
    public static void clearSession() {
        username = null;
        role = null;
    }
}
