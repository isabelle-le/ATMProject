package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
/**
 * Test connect to mySQl through JDBC
 * console should show: "Great! full control of database" and  "Mia"
 * @author Le Thu Huong
 */
public class test {
     public static void main(String[] args) {
    	 String card_No = "1111";
    	 String password_No = "1234";
         try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
        		System.out.println("Great! full control of database");
                Statement st = con.createStatement();
                ResultSet rs = st.executeQuery("SELECT * FROM accountdetail WHERE card_no = " +"'" + card_No + 
                		"'" + "and password_no =" +"'" + password_No+"'");

                while(rs.next()) {
                    System.out.println( rs.getString("name"));
               }
                st.close();
                rs.close();
                con.close();
        } catch (Exception ex) {
                ex.printStackTrace();
        }
    }
}