package day0619;

import java.sql.*;

public class BoardUpdateExample {

    public static void main(String[] args) {
        Connection conn = null;

        //JDBC Driver 등록
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

            //연결하기
            conn = DriverManager.getConnection(
                    "jdbc:mysql://10.80.163.237:3306/thisisjava",
                    "java",
                    "mysql"
            );

            //매개변수화된 SQL 문 작성
            String sql = new StringBuilder()
                    .append("UPDATE boards SET ")
                    .append("btitle=?, ")
                    .append("bcontent=?, ")
                    .append("bfilename=?, ")
                    .append("bfiledata=? ")
                    .append("WHERE bno=?")
                    .toString();

            //PrepareStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "눈사람");
            pstmt.setString(2, "눈으로 만든 사람");
            pstmt.setString(3, null);
            pstmt.setBlob(4, (Blob) null);
            pstmt.setInt(5, 9);

            //SQL문 실행
            int rows = pstmt.executeUpdate();
            System.out.println("수정된 행 수 : " + rows);

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
