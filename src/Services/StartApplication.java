package Services;
import Controllers.Controller;


public class StartApplication {

	public static void main(String[] args) {
		new StartApplication();
	}
	
	public StartApplication() {
		Controller.connexion();
		Controller c = new Controller();
		c.menuPrincipal();
		Controller.finConnexion();
	}

}
