package study.db;

import java.sql.*;

public class MemberService {

    // function을 이용
    public void dbSelect() {

        // java를 통해서 jdbc 드라이버 접속하기

        // 라이브러리에 jdbc driver jar 추가하기

        // db에 접속하기 위해 필요한 정보들 5가지
        // 1. ip
        // 2. prot
        // 3. 계정
        // 4. 패스워드
        // 5. 인스턴스

        // 실제로 API 같은 경우에는 url 주소로 접근을 함
        String url = "jdbc:mariadb://172.29.72.79:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "testuser1234";

        // java에서 연결하는 방법
        // 1. 드라이버로드
        // 2. 커넥션 객체 생성
        // 3. 스테이트먼트 객체 생성
        // 4. 쿼리 실행
        // 5. 결과 수행
        // 6. 객체 연결 해제 (close)
        // 일단 데이터베이스에 대하여 접근하기 by using try catch 구문
        try {
            Class.forName("org.mariadb.jdbc.Driver"); //lib의 Driver class에서 참고해서 가져오기
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // 쿼리 작성과 실행 이전에 이미 rs에 대한 객체를 선언
        Connection connection = null;
        Statement statement = null;
        //PreparedStatement preparedStatement = null;
        ResultSet rs = null;

        //String memberTypevalue = 'email';

        // 쿼리 작성과 실행 부분
        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            statement = connection.createStatement();

            // java에서는 DB에 이미 쿼리를 생성한 뒤에! 해당 쿼리들을 연결하여 시행하는 형식임
            String sql =  "select member.member_type, member.member_id, member.password, member.name " +
                    "    from member " +
                    "where member_type = 'email' ";
            // "where member_type = '" + memberTypeValue + "' ";
            // 근데 이런 단순한 방식은 sqlinjection이라는 해킹공격이 있을 수 있으니 조심하기.. -> PreparedStatement를 사용하는 이유

            // "where member_type = ? ";
            //preparedStatement = connection.prepareStatement(sql)
            //preparedStatement.setString(1, memberTypeValue);
            rs = statement.executeQuery(sql); // 쿼리를 실행하는 메소드

            // rs = preparedStatement.executeQuery(sql);
            // 쿼리 실행 결과를 따로 java의 데이터에 저장
            while(rs.next()) {
                String memberType = rs.getString("member_type");
                String userId = rs.getString("member_id");
                String password = rs.getString("password");
                String name = rs.getString("name");

                System.out.println(memberType + ", " + userId + ", " + password + ", " + "name");
            }


        } catch (SQLException e) { // 예외 및 에러에 대한 처리가 필수이기 때문에 빠뜨리지 말기
            e.printStackTrace();
        } finally { // 일반저긍로 종료 코드 같은 경우는 예외 후에 처리가 될 수도 있기에 finally 구문에 작성해야함
            // 종료 코드
            // try 구문 외에서도 rs 객체에 대하여 선언 미리 필요!(앞의 쿼리 코드 이전 확인)

            // isClosed도 예외를 발생시키기 때문에 이에 대한 처리가 필요
            try { // 하나하나 예외처리 해줘야함
                if (rs != null && !rs.isClosed()) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (statement != null && !statement.isClosed()) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } // 위의 코드가 길긴해도 이게 실제 환경에서 정상작동하는 방법!(내가 연 것은 내가 닫아주자!)

    }


    public void register(Member member) {

        String url = "jdbc:mariadb://172.29.72.79:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "testuser1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); //lib의 Driver class에서 참고해서 가져오기
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet rs = null;

//        String memberTypeValue = "email";
//        String userIdValue = "zerobase@naver.com";
//        int passwordValue = 3333;
//        String nameValue = "제로베이스";

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "insert into member (member_type, member_id, password, name) " +
                    "  values (? , ? , ?, ?) ";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.setString(4, member.getName());

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 저장 성공 ");
            } else {
                System.out.println(" 저장 실패 ");
            }
        } catch (SQLException e) { // 예외 및 에러에 대한 처리가 필수이기 때문에 빠뜨리지 말기
            e.printStackTrace();

        } finally {

            try {
                if (preparedStatement!= null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    public void dbUpdate() {

        String url = "jdbc:mariadb://172.29.72.79:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "testuser1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); //lib의 Driver class에서 참고해서 가져오기
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String memberTypeValue = "email";
        String userIdValue = "zerobase@naver.com";
        String passwordValue = "9999";

        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "update member " +
                    " set " +
                    "    password = ? " +
                    " where member_type = ? " +
                    " and member_id = ? ";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, passwordValue);
            preparedStatement.setString(2, memberTypeValue);
            preparedStatement.setString(3, userIdValue);

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 수정 성공 ");
            } else {
                System.out.println(" 수정 실패 ");
            }
        } catch (SQLException e) { // 예외 및 에러에 대한 처리가 필수이기 때문에 빠뜨리지 말기
            e.printStackTrace();

        } finally {

            try {
                if (preparedStatement!= null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dbDelete(Member member) {

        String url = "jdbc:mariadb://172.29.72.79:3306/testdb1";
        String dbUserId = "testuser1";
        String dbPassword = "testuser1234";

        try {
            Class.forName("org.mariadb.jdbc.Driver"); //lib의 Driver class에서 참고해서 가져오기
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null;


        try {
            connection = DriverManager.getConnection(url, dbUserId, dbPassword);

            String sql = "delete from member " +
                    " where member_type = ? and member_id = ? ";

            preparedStatement = connection.prepareStatement(sql);

            preparedStatement.setString(1, member.getMemberType());
            preparedStatement.setString(2, member.getUserId());

            int affected = preparedStatement.executeUpdate();

            if (affected > 0) {
                System.out.println(" 탈퇴 성공 ");
            } else {
                System.out.println(" 탈퇴 실패 ");
            }
        } catch (SQLException e) { // 예외 및 에러에 대한 처리가 필수이기 때문에 빠뜨리지 말기
            e.printStackTrace();

        } finally {

            try {
                if (preparedStatement!= null && !preparedStatement.isClosed()) {
                    preparedStatement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
