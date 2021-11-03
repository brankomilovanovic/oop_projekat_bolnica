package pacijentiZaposleniUprava;

import java.io.*;
import java.util.*;

import zdravstvenoStanje.Dijagnoza;
import zdravstvenoStanje.Lek;

public class Administrator extends Korisnik {
	
	public static void prikaziMeniAdministratora() throws IOException {
		Scanner sc = new Scanner(System.in);
		int brojOpcije = 0;
		
		System.out.println("");
		System.out.println("Izaberite jednu od opcija:");
		System.out.println("-----------------------------------");
		System.out.println("1.Registracija pacijenta");
		System.out.println("2.Registracija doktora");
		System.out.println("3.Dodavanje leka");
		System.out.println("4.Dodavanje dijagnoze");
		System.out.println("5.Uklanjanje leka");
		System.out.println("6.Uklanjanje dijagnoze");
		System.out.println("7.Odjava korisnika");
		System.out.println("-----------------------------------");
		System.out.println("Unesite broj opcije: ");
		
		try {
			brojOpcije = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			prikaziMeniAdministratora();
		}
		switch (brojOpcije) {
		case 1:
			Pacijent.registracijaPacijenta();
		case 2:
			Doktor.registracijaDoktora();
		case 3:
			Lek.dodavanjeLeka();
		case 4:
			Dijagnoza.dodavanjeDijagnoze();
		case 5:
			Lek.uklanjanjeLeka();
		case 6:
			Dijagnoza.uklanjanjeDijagnoze();
		case 7:
			Korisnik.prikaziMeni();
		default:
			System.out.println("Uneli ste pogresnu opciju.");
			prikaziMeniAdministratora();
	    }
		sc.close();
	}

}
