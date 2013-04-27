package be.vodelee.ultimate.core;

import be.vodelee.ultimate.ihm.MainScreen;

public class RunUltimateSoccer {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Tournament.getInstance();
		new MainScreen().setVisible(true);
	}
}
