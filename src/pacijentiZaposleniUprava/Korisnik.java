package pacijentiZaposleniUprava;

import java.io.*;
import java.util.*;

public abstract class Korisnik {
	
	private static String ime;
	private static String prezime;
	private static String korisnickoIme;
	private static String lozinka;
	static Scanner sc = new Scanner(System.in);
	
	public Korisnik() {
		super();
	}

	public Korisnik(String ime, String prezime, String korisnickoIme, String lozinka) {
		super();
		Korisnik.ime = ime;
		Korisnik.prezime = prezime;
		Korisnik.korisnickoIme = korisnickoIme;
		Korisnik.lozinka = lozinka;
	}

	public String getIme() {
		return ime;
	}

	public void setIme(String ime) {
		Korisnik.ime = ime;
	}

	public String getPrezime() {
		return prezime;
	}

	public void setPrezime(String prezime) {
		Korisnik.prezime = prezime;
	}

	public String getKorisnickoIme() {
		return korisnickoIme;
	}

	public void setKorisnickoIme(String korisnickoIme) {
		Korisnik.korisnickoIme = korisnickoIme;
	}

	public String getLozinka() {
		return lozinka;
	}

	public void setLozinka(String lozinka) {
		Korisnik.lozinka = lozinka;
	}
	
	public static void prijavaKorisnika() throws IOException {
		
		int tipKorisnika = 0;
		String linija = ""; 
		
		System.out.println("");
		System.out.println("Prijava korisnika");
		System.out.println("-----------------------------------");
		System.out.println("Unesite korisnicko ime: ");
		Korisnik.korisnickoIme = sc.next();
		System.out.println("Unesite lozinku: ");
		Korisnik.lozinka = sc.next();	
		try {
			BufferedReader ucitavanjeKorisnika = new BufferedReader(new FileReader("data\\Korisnici.csv")); //Otvara fajl korisnik i cita podatke iz njega
			while ((linija = ucitavanjeKorisnika.readLine()) != null)  //Sve dok ucitavanjeKorisnika nije null petlja se izvrsava
			{  
			String[] sviKorisnici = linija.split(","); //Razdvaja informaciju o korisniku svaku posebno i pravi listu korisnika
				if((korisnickoIme.equals(sviKorisnici[2])) && (lozinka.equals(sviKorisnici[3]))) {
					Korisnik.ime = sviKorisnici[0];
					Korisnik.prezime = sviKorisnici[1];
					Korisnik.korisnickoIme = sviKorisnici[2];
					Korisnik.lozinka = sviKorisnici[3];
					tipKorisnika = Integer.parseInt(sviKorisnici[4]);  // Konvertovanje stringa u int					
					break;
			    } 
			}
		ucitavanjeKorisnika.close(); //Zatvaranje fajla
		} catch (FileNotFoundException e) { //Ukoliko fajl nije pronadjen
			System.out.println("Fajl korisnici nije pronadjen.");
			Korisnik.prikaziMeni();
		} catch (IOException e) { //Ukoliko se prekine ili iz nekog razloga ne ucita fajl
			System.out.println("Greska pri citanju fajla.");
			Korisnik.prikaziMeni();
		} 
		
		if(tipKorisnika == 1) {
			System.out.println("");
			System.out.println("Pozdrav, " + Korisnik.ime + " " + Korisnik.prezime + " uspesno ste se prijavili.");
			Pacijent.prikaziMeniPacijenta(Korisnik.korisnickoIme);
		}
		else if(tipKorisnika == 2) {
			System.out.println("");
			System.out.println("Pozdrav, doktore " + Korisnik.ime + " " + Korisnik.prezime + " uspesno ste se prijavili.");
			Doktor.prikaziMeniDoktora(Korisnik.korisnickoIme);
		}
		else if(tipKorisnika == 3) {
			System.out.println("");
			System.out.println("Pozdrav, administratore " + Korisnik.ime + " " + Korisnik.prezime + " uspesno ste se prijavili.");
			Administrator.prikaziMeniAdministratora();
		}
		else if(tipKorisnika == 0) {
			System.out.println("Korisnik nije pronadjen.");
			Korisnik.prikaziMeni(); // Ukoliko korisnik nije pronadjen vraca ga na pocetni meni.
		}
	}
	
	public static void prikaziMeni() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		int brojOpcije = 3;
		
		System.out.println("");
		System.out.println("Izaberite jednu od opcija:");
		System.out.println("-----------------------------------");
		System.out.println("1.Prijava korisnika");
		System.out.println("2.Izlazak iz aplikacije");
		System.out.println("-----------------------------------");
		System.out.println("Unesite broj opcije: ");
		
		try {
			brojOpcije = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			prikaziMeni();
		}
		switch (brojOpcije) {
		case 1:
			Korisnik.prijavaKorisnika();
			break;
		case 2:
			System.out.println("Kraj programa...");
			System.exit(0);
		default:
			System.out.println("Uneli ste pogresnu opciju.");
			prikaziMeni();
		}
		sc.close();
	}

}
