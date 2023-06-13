package day0612;

import java.io.FileInputStream;
import java.sql.*;

public class BoardInsertExample {

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
                    "INSERT INTO boards (btitle, bcontent, bwriter, bdate, bfilename, bfiledata)"+
                    "VALUES(?, ?, ?, now(), ?, ?)";

            //PrepareStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(
                    sql, Statement.RETURN_GENERATED_KEYS
            );

            pstmt.setString(1, "눈 오는 날");
            pstmt.setString(2, "함박눈이 내려요.");
            pstmt.setString(3, "winter");
            pstmt.setString(4, "snow.png");
            pstmt.setBlob(5, new FileInputStream("파일 경로입력/snow.png"));

            //SQL문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("저장된 행 수 : " + rows);

            //bno 값 얻기
            if (rows == 1) {
                ResultSet rs = pstmt.getGeneratedKeys();

                if (rs.next()) {
                    int bno = rs.getInt(1);
                    System.out.println("저장된 bno : " + bno);
                }
                rs.close();
            }

            //PreparedStatement 닫기
            pstmt.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                try {
                    //연결끊기
                    conn.close();
                } catch (SQLException e) {}
            }
        }
    }
}
