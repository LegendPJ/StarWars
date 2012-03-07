package Vues;

import Controllers.Controller;

public class Vue {
	
	public static int QUITTER = 100;
	
	private Controller _controller;
	public Vue (Controller c) {
		_controller = c;
	}
	
	public Controller getController() {
		return this._controller;
	}
}
