package pacijentiZaposleniUprava;

import java.io.*;
import java.time.LocalDate;
import java.util.*;

import dokumentacija.*;

public class Pacijent extends Korisnik {
	
	public static void prikaziMeniPacijenta(String korisnickoIme) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		int brojOpcije = 0;
		Pacijent pacijent = new Pacijent();
		
		pacijent.setKorisnickoIme(korisnickoIme);
		System.out.println("");
		System.out.println("Izaberite jednu od opcija:");
		System.out.println("-----------------------------------");
		System.out.println("1.Zakazivanje termina pregleda");
		System.out.println("2.Overavanje knjižice");
		System.out.println("3.Pregledanje zakazanih termina");
		System.out.println("4.Odjava korisnika");
		System.out.println("-----------------------------------");
		System.out.println("Unesite broj opcije: ");
		
		try {
			brojOpcije = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			prikaziMeniPacijenta(pacijent.getKorisnickoIme());
		}
		switch (brojOpcije) {
			case 1:
				Pregled.zakazivanjeTerminaPregleda(pacijent.getKorisnickoIme());
			case 2:
				Knjizica.overavanjeKnjizice(pacijent.getKorisnickoIme());
			case 3:
				Pregled.zakazaniPregledi(pacijent.getKorisnickoIme());
			case 4:
				Korisnik.prikaziMeni();
			default:
				System.out.println("Uneli ste pogresnu opciju.");
				prikaziMeniPacijenta(pacijent.getKorisnickoIme());
		}
		sc.close();
	}
	
	public static void registracijaPacijenta() throws IOException {
			
		Scanner sc = new Scanner(System.in);
		String linija = ""; 
		File file = new File("data\\Korisnici.csv");
		File file2 = new File("data\\Knjizice.csv");
		Pacijent pacijent = new Pacijent();
		
		try {
			System.out.println("Unesite ime pacijenta: ");
			String imePacijenta = sc.nextLine();
			//Konvertovanje stringa u title (substring(startindex, stopindex))
			imePacijenta = imePacijenta.substring(0, 1).toUpperCase() + imePacijenta.substring(1).toLowerCase(); 
			pacijent.setIme(imePacijenta);
			System.out.println("Unesite prezime pacijenta: ");
			String prezimePacijenta = sc.nextLine();
			//Konvertovanje stringa u title (substring(startindex, stopindex))
			prezimePacijenta = prezimePacijenta.substring(0, 1).toUpperCase() + prezimePacijenta.substring(1).toLowerCase(); 
			pacijent.setPrezime(prezimePacijenta);
			System.out.println("Unesite unesite korisnicko ime pacijenta: ");
			pacijent.setKorisnickoIme(sc.nextLine());
			if(pacijent.getKorisnickoIme().isEmpty()) {
				System.out.println("Niste uneli odgovaracuje parametre.");
				Administrator.prikaziMeniAdministratora();
			}
			System.out.println("Unesite lozinku pacijenta: ");
			pacijent.setLozinka(sc.nextLine());
			if(pacijent.getLozinka().isEmpty()) {
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
			if((pacijent.getKorisnickoIme().equals(sviKorisnici[2]))) { System.out.println("Vec postoji korisnik sa tim korisnickim imenom."); ucitavanjeKorisnika.close(); Administrator.prikaziMeniAdministratora(); break; }
		}
		
		System.out.println("Uspesno ste registrovali pacijenta " + pacijent.getIme() + " " + pacijent.getPrezime());
			
		LocalDate trenutnoVreme = LocalDate.now();
		LocalDate sledecaGodina = trenutnoVreme.plusYears(1); // Automatsko dodavanje knjizice sa vazenjem godinu dana
		String dodavanjeNoveKnjizice = pacijent.getKorisnickoIme() + "," + sledecaGodina; //Identifikator je korisnicko ime pacijenta
		BufferedWriter dodavanjeKnjizice = new BufferedWriter(new FileWriter("data\\Knjizice.csv", true));  
		if (file2.length() == 0) {  
			dodavanjeKnjizice.write(dodavanjeNoveKnjizice);
			dodavanjeKnjizice.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeKnjizice.close();
		} else {   
			dodavanjeKnjizice.newLine();
			dodavanjeKnjizice.write(dodavanjeNoveKnjizice);
			dodavanjeKnjizice.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeKnjizice.close();
		}
			
		String registrovaniKorisnik = pacijent.getIme() + "," + pacijent.getPrezime() + "," + pacijent.getKorisnickoIme() + "," + pacijent.getLozinka() + ",1,nema";
		BufferedWriter dodavanjeNovogKorisnika = new BufferedWriter(new FileWriter("data\\Korisnici.csv", true));  
		if (file.length() == 0) {  
			dodavanjeNovogKorisnika.write(registrovaniKorisnik);
			dodavanjeNovogKorisnika.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeNovogKorisnika.close();
		} else {   
			dodavanjeNovogKorisnika.newLine();
			dodavanjeNovogKorisnika.write(registrovaniKorisnik);
			dodavanjeNovogKorisnika.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeNovogKorisnika.close();
			
		}
		
		Administrator.prikaziMeniAdministratora();
		sc.close();
	}
}
