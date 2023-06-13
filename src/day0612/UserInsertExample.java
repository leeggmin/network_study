package day0612;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserInsertExample {

    public static void main(String[] args) {
        Connection conn = null;

        //JDBC Driver 등록
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //연결하기
            conn = DriverManager.getConnection(
                    "jdbc:mysql://10.80.163.163:3306/thisisjava",
                    "java",
                    "mysql"
            );

            //매개변수화된 SQL 문 작성
            String sql = ""+
                    "INSERT INTO users (userid,username,userpassword,userage,useremail)"+
                    "VALUES(?, ?, ?, ?, ?)";

            //PrepareStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "leeggmin");
            pstmt.setString(2, "이지민");
            pstmt.setString(3, "pwpwpw");
            pstmt.setInt(4, 18);
            pstmt.setString(5, "leeggmin@test.com");

            //SQL문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수 : " + rows);

            //PreparedStatement 닫기
            pstmt.close();

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //연결끊기
                    conn.close();
                    System.out.println("연결 끊기");
                } catch (SQLException e) {}
            }
        }
    }
}
