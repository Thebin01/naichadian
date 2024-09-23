package build;
import java.sql.*;
public class Sql {
    static {//静态代码块，类加载的时候执行把注册驱动程序的代码放在静态代码块中，可以避免多次获取连接对象时重复调用
        try {
            Class.forName("com.mysql.jdbc.Driver");//调用该类直接注册数据库
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    ////把方法设为静态，便于直接调用而不需要实例化类
    public static Connection getConnection() throws SQLException {//调用方法连数据库返回Connection类
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/nai_cha_dian?useSSL=false","root","123456");
    }

    public static void close(Connection conn, Statement ps, ResultSet rs){//释放关闭所有资源
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
