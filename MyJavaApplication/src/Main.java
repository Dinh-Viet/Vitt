import java.sql.*;
import java.util.Scanner;

public class Main {

    public static void getAllCustomer() throws SQLException {
        // gọi dt connection từng lớp
        Connection connection = MySQLConnectionDB.getMySQLConnection();
        // tạo statemnent để thực thi truy vấn
        Statement stm = connection.createStatement();
        // câu lênh truuy vấn cơ sở dữ liệu
        String query = "SELECT * FROM customers";
        //thực thi truy vấn
        ResultSet rs = stm.executeQuery(query);

        while (rs.next()) {
            System.out.println("===========");
            System.out.println("Customer id : " + rs.getInt(1));
            System.out.println("Fisrt name : " + rs.getString(2));
            System.out.println("Last name : " + rs.getString(3));
            System.out.println("email : " + rs.getString(4));
        }
        connection.close();

    }

    public static void addCustomer(Integer customers_id , String firt_name, String last_name, String email) throws SQLException {
        Connection connection = MySQLConnectionDB.getMySQLConnection();

        String query = "INSERT INTO customers (customers_id, firt_name, last_name, email) VALUES (?, ?, ?, ?)";

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, customers_id);
        pstmt.setString(2, firt_name);
        pstmt.setString(3, last_name);
        pstmt.setString(4, email);
        pstmt.executeUpdate();

        System.out.println("Đã thêm thành công.");
        connection.close();
    }

    public static void updateCustomer(int customers_id, String firt_name, String last_name, String email) throws SQLException {
        Connection connection = MySQLConnectionDB.getMySQLConnection();

        String query = "UPDATE customers SET firt_name = ?, last_name = ?, email = ? WHERE customers_id = ?";

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setString(1, firt_name);
        pstmt.setString(2, last_name);
        pstmt.setString(3, email);
        pstmt.setInt(4, customers_id);
        pstmt.executeUpdate();

        System.out.println("Đã cập nhật thành công");
        connection.close();
    }

    public static void deleteCustomer(int customerId) throws SQLException {
        Connection connection = MySQLConnectionDB.getMySQLConnection();

        String query = "DELETE FROM customers WHERE customer_id = ?";

        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, customerId);
        pstmt.executeUpdate();

        System.out.println("Đã xóa thành công.");
        connection.close();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        boolean exit = false;
        while (!exit) {
            System.out.println("========= MENU =========");
            System.out.println("1. Hiển thị danh sách khách hàng");
            System.out.println("2. Thêm khách hàng mới");
            System.out.println("3. Cập nhật thông tin khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Thoát");
            System.out.print("Chọn một lựa chọn từ menu: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character from the buffer

            try {
                switch (choice) {
                    case 1:
                        getAllCustomer();
                        break;
                    case 2:
                        System.out.println("Nhập thông tin mới của khách hàng:");
                        System.out.print("ID: ");
                        int id = scanner.nextInt();
                        scanner.nextLine(); // Clear the newline character from the buffer
                        System.out.print("First Name: ");
                        String firstName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String lastName = scanner.nextLine();
                        System.out.print("Email: ");
                        String email = scanner.nextLine();
                        addCustomer(id, firstName, lastName, email);
                        break;
                    case 3:
                        System.out.println("Nhập ID của khách hàng cần cập nhật:");
                        int updateId = scanner.nextInt();
                        scanner.nextLine(); // Clear the newline character from the buffer
                        System.out.println("Nhập thông tin cập nhật:");
                        System.out.print("First Name: ");
                        String updateFirstName = scanner.nextLine();
                        System.out.print("Last Name: ");
                        String updateLastName = scanner.nextLine();
                        System.out.print("Email: ");
                        String updateEmail = scanner.nextLine();
                        updateCustomer(updateId, updateFirstName, updateLastName, updateEmail);
                        break;
                    case 4:
                        System.out.println("Nhập ID của khách hàng cần xóa:");
                        int deleteId = scanner.nextInt();
                        scanner.nextLine(); // Clear the newline character from the buffer
                        deleteCustomer(deleteId);
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng chọn lại.");
                }
            } catch (SQLException e) {
                System.out.println("Lỗi cập nhật: " + e.getMessage());
            }
        }

        System.out.println("Ứng dụng đã kết thúc.");
    }
}

