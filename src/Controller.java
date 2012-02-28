import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;


public class Controller {
	protected static Connection conn;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	public Controller() {
		
	}
	
	public static void connexion() {
		try {
			//CONNEXION
			Torque.init(TORQUE_PROPS);
			String url = "jdbc:postgresql://postgres-info/base5a00";
			conn = DriverManager.getConnection(url, "user5a00", "p00");
		} catch (SQLException e) {
			System.err.println("SQLException : " + e.getMessage());
			System.err.println("SQLState : " + e.getSQLState());
		}  catch (TorqueException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void finConnexion() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
