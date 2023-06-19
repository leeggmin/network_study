package day0619;

import java.sql.*;

public class UserSelectExample {

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
            String sql = "select userid, username, userpassword, userage, useremail from users where userid=?";

            //PrepareStatement 얻기 및 값 지정
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, "winter");

            //SQL문 실행 후, ResultSet을 통해 데이터 읽기
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) { //1개의 데이타 행을 가져왔을 경우
                User user = new User();
                user.setUserid(rs.getString("userid"));
                user.setUserName(rs.getString("username"));
                user.setUserPassword(rs.getString("userpassword"));
                user.setUserAge(rs.getInt(4)); //컬럼 순번을 이용
                user.setUserEmail(rs.getString(5)); //컬럼 순번을 이용

                System.out.println(user.getUserid());
                System.out.println(user.getUserName());
                System.out.println(user.getUserPassword());
                System.out.println(user.getUserAge());
                System.out.println(user.getUserEmail());
            } else { //데이터행ㅇ르 가져오지 않았을 경우
                System.out.println("사용자 아이디가 존재하지 않음");
            }
            rs.close();

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
