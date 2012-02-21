

import java.io.*;
import java.util.*;

public class IO {

	// *******************************
	// Attributs
	// *******************************
	private static BufferedReader entree = new BufferedReader(
			new InputStreamReader(System.in));

	// ********************************
	// Méthodes Publiques
	// ********************************
	public static int lireEntier() {
		boolean estEntier = false;
		int valentiere = 0;

		try {
			do {
				try {
					valentiere = Integer.parseInt(entree.readLine());
					estEntier = true;
				} catch (NumberFormatException e) {
					System.out.println("Non un entier. Recommencez.");
				}
			} while (!estEntier);

			return valentiere;
		} catch (IOException e) {
			System.out.println(" ERREUR d'E/S : IO.LIRE_ENTIER");
			return 0;
		}
	}

	// ***********************************************
	public static float lireFlottant() {
		boolean estFlottant = false;
		float valflottant = 0;
		Float flot;
		try {
			do {
				try {
					flot = Float.valueOf(entree.readLine());
					valflottant = flot.floatValue();
					estFlottant = true;
				} catch (NumberFormatException e) {
					System.out.println("Non un flottant. Recommencez.");
				}
			} while (!estFlottant);
			return valflottant;
		} catch (IOException e) {
			System.out.println(" ERREUR d'E/S : IO.LIRE_ENTIER");
			return 0;
		}
	}

	// ***********************************************
	public static String lireChaine() {
		try {
			return entree.readLine();
		} catch (IOException e) {
			System.out.println(" ERREUR d'E/S : IO.LIRE_CHAINE");
			return "";
		}
	} // Fin lireChaine

	// ************************************************
	// E/S sur les DATES
	// ************************************************
	public static GregorianCalendar lireDate() {
		boolean ok = false;
		int jour;
		int mois;
		int an;
		do {
			System.out.println();
			System.out.print("Jour ?: ");
			jour = lireEntier();
			if (jour >= 1 & jour <= 31)
				ok = true;
			else
				System.out.println(" Jour incorrect ");
		} while (!ok);
		do {
			ok = false;
			System.out.print("Mois ?: ");
			mois = lireEntier();
			if (mois >= 1 & mois <= 12)
				ok = true;
			else
				System.out.println(" Mois incorrect ");
		} while (!ok);
		do {
			ok = false;
			System.out.print("Annee ?: ");
			an = lireEntier();
			if (an >= 1900 & an <= 2100)
				ok = true;
			else
				System.out.println(" Annnée incorrecte (1900..2100)");
		} while (!ok);
		GregorianCalendar unedate = new GregorianCalendar(an, mois - 1, jour);
		return unedate;
	}

	// ***********************************************
	public static String ecrireDate(GregorianCalendar date) {
		if (date == null)
			return "nul";
		else
			return date.get(Calendar.DAY_OF_MONTH) + "-"
					+ (date.get(Calendar.MONTH) + 1) + "-"
					+ date.get(Calendar.YEAR);
	}

	// ***********************************************
	public static String ecrireDateComplete(GregorianCalendar date) {

		if (date == null)
			return "nul";
		else {
			return (IO.ecrireDate(date) + "  " + date.get(Calendar.HOUR_OF_DAY)
					+ "H " + date.get(Calendar.MINUTE) + "mn "
					+ date.get(Calendar.SECOND) + "sec "
					+ date.get(Calendar.MILLISECOND) + "millisec");
		}

	}

	// ***********************************************
	public static int jourSemaine(GregorianCalendar date) {
		if (date == null)
			return -1;
		else
			return date.get(Calendar.DAY_OF_WEEK);
	}

	// ***********************************************
	public static int heure(GregorianCalendar date) {
		if (date == null)
			return -1;
		else
			return date.get(Calendar.HOUR_OF_DAY);
	}

	// ***********************************************
	public static int minute(GregorianCalendar date) {
		if (date == null)
			return -1;
		else
			return date.get(Calendar.MINUTE);
	}

}
