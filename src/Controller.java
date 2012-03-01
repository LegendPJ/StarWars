import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.torque.Torque;
import org.apache.torque.TorqueException;
import org.apache.torque.util.Transaction;


public class Controller {
	protected static Connection conn, connTransaction;
	private final static String TORQUE_PROPS = new String("torque-3.3//Torque.properties");
	
	public Controller() {
		
	}
	
	protected static void connexion() {
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
	
	protected static void finConnexion() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	protected void beginTransaction() {
		try {
			connTransaction = Transaction.begin();
		} catch (TorqueException e2) {
			e2.printStackTrace();
		}
	}
	
	protected void commitTransaction() {
		try {
			Transaction.commit(connTransaction);
		} catch (Exception e) {
			this.rollBack();
			e.printStackTrace();
		}
	}
	
	protected void rollBack() {
		try {
			Transaction.rollback(connTransaction);
		} catch (TorqueException e1) {
			e1.printStackTrace();
		}
	}
}
