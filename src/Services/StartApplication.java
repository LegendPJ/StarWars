package Services;
import java.sql.SQLException;

import org.apache.torque.TorqueException;

import Controllers.Controller;


public class StartApplication {

	public static void main(String[] args) {
		new StartApplication();
	}
	
	public StartApplication() {
		try {
			Controller.connexion();
			Controller c = new Controller();
			c.menuPrincipal();
			Controller.finConnexion();
		} catch (TorqueException e) {
			e.printStackTrace();
			System.out.println("La connexion à la base de donnée a échoué");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("La connexion à la base de donnée a échoué");
		}
	}

}
