package Vues;

import Controllers.Controller;

public class Vue {
	
	public static final int QUITTER = 0;
	
	private Controller _controller;
	
	public Vue (Controller c) {
		_controller = c;
	}
	
	public Controller getController() {
		return this._controller;
	}
}
