package pacijentiZaposleniUprava;

import java.io.*;
import java.util.*;

import dokumentacija.Pregled;
import zdravstvenoStanje.Izvestaj;

public class Doktor extends Korisnik {
	
	private String specijalizacija;
	
	public Doktor() {
		super();
	}

	public Doktor(String ime, String prezime, String korisnickoIme, String lozinka,String specijalizacija) {
		super(ime, prezime, korisnickoIme, lozinka);
		this.specijalizacija = specijalizacija;
	}
	
	public String getSpecijalizacija() {
		return specijalizacija;
	}

	public void setSpecijalizacija(String specijalizacija) {
		this.specijalizacija = specijalizacija;
	}
	
	public static void prikaziMeniDoktora(String korisnickoImeDoktora) throws IOException {
		Scanner sc = new Scanner(System.in);
		int brojOpcije = 0;
		
		Doktor doktor = new Doktor();
		doktor.setKorisnickoIme(korisnickoImeDoktora);
		
		System.out.println("");
		System.out.println("Izaberite jednu od opcija:");
		System.out.println("-----------------------------------");
		System.out.println("1.Pregledanje");
		System.out.println("2.Pregled dijagnoza pacijenta");
		System.out.println("3.Odjava korisnika");
		System.out.println("-----------------------------------");
		System.out.println("Unesite broj opcije: ");
		
		try {
			brojOpcije = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		switch (brojOpcije) {
		case 1:
			Pregled.pregled(doktor.getKorisnickoIme());
		case 2:
			Izvestaj.kreirajIzvestaj(doktor.getKorisnickoIme());
		case 3:
			Korisnik.prikaziMeni();
		default:
			System.out.println("Uneli ste pogresnu opciju.");
			prikaziMeniDoktora(doktor.getKorisnickoIme());
			
	    }
		sc.close();
	}
	
	public static void registracijaDoktora() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String linija = ""; 
		File file = new File("data\\Korisnici.csv");
		Doktor doktor = new Doktor();
		
		try {
			System.out.println("Unesite ime doktora: ");
			String imeDoktora = sc.nextLine();
			//Konvertovanje stringa u title (substring(startindex, stopindex))
			imeDoktora = imeDoktora.substring(0, 1).toUpperCase() + imeDoktora.substring(1).toLowerCase(); 
			doktor.setIme(imeDoktora);
			System.out.println("Unesite prezime doktora: ");
			String prezimeDoktora = sc.nextLine();
			//Konvertovanje stringa u title (substring(startindex, stopindex))
			prezimeDoktora = prezimeDoktora.substring(0, 1).toUpperCase() + prezimeDoktora.substring(1).toLowerCase(); 
			doktor.setPrezime(prezimeDoktora);
			System.out.println("Unesite unesite korisnicko ime doktora: ");
			doktor.setKorisnickoIme(sc.nextLine());
			if(doktor.getKorisnickoIme().isEmpty()) {
				System.out.println("Niste uneli odgovaracuje parametre.");
				Administrator.prikaziMeniAdministratora();
			}
			System.out.println("Unesite lozinku doktora: ");
			doktor.setLozinka(sc.nextLine());
			if(doktor.getLozinka().isEmpty()) {
				System.out.println("Niste uneli odgovaracuje parametre.");
				Administrator.prikaziMeniAdministratora();
			}
			System.out.println("Unesite specijalizaciju doktora: ");
			doktor.setSpecijalizacija(sc.nextLine());
			if(doktor.getSpecijalizacija().isEmpty()) {
				System.out.println("Niste uneli odgovaracuje parametre.");
				Administrator.prikaziMeniAdministratora();
			}
		} catch (StringIndexOutOfBoundsException e ) {
			System.out.println("Niste uneli odgovaracuje parametre.");
			Administrator.prikaziMeniAdministratora();
		}
		
		BufferedReader ucitavanjeKorisnika = new BufferedReader(new FileReader("data\\Korisnici.csv")); //Otvara fajl korisnik i cita podatke iz njega
		while ((linija = ucitavanjeKorisnika.readLine()) != null)  //Sve dok ucitavanjeKorisnika nije null petlja se izvrsava
		{  
		String[] sviKorisnici = linija.split(","); //Razdvaja informaciju o korisniku svaku posebno i pravi listu korisnika
			if((doktor.getKorisnickoIme().equals(sviKorisnici[2]))) { System.out.println("Vec postoji korisnik sa tim korisnickim imenom."); ucitavanjeKorisnika.close(); Administrator.prikaziMeniAdministratora(); break; }
		}
		
		System.out.println("Uspesno ste registrovali doktora " + doktor.getIme() + " " + doktor.getPrezime());
		
		String registrovaniDoktor = doktor.getIme() + "," + doktor.getPrezime() + "," + doktor.getKorisnickoIme() + "," + doktor.getLozinka() + ",2," + doktor.getSpecijalizacija();
		BufferedWriter dodavanjeNovogDoktora = new BufferedWriter(new FileWriter("data\\Korisnici.csv", true));  

		if (file.length() == 0) {  
			dodavanjeNovogDoktora.write(registrovaniDoktor);
			dodavanjeNovogDoktora.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeNovogDoktora.close();
		} else {   
			dodavanjeNovogDoktora.newLine();
			dodavanjeNovogDoktora.write(registrovaniDoktor);
			dodavanjeNovogDoktora.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeNovogDoktora.close();
		}
		
		Administrator.prikaziMeniAdministratora();
		sc.close();
	}

}
