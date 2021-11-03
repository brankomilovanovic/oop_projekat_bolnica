package zdravstvenoStanje;

import java.io.*;
import java.time.LocalDateTime;
import java.util.*;

import dokumentacija.Dokument;
import dokumentacija.Pregled;
import pacijentiZaposleniUprava.Doktor;
import pacijentiZaposleniUprava.Pacijent;

public class Izvestaj extends Pregled implements Dokument{
	
	private LocalDateTime datumIzdavanja;
	private boolean overen;
	
	
	public Izvestaj() {
		super();
	}


	public Izvestaj(long vremePregleda, LocalDateTime datumIVremePocetka,LocalDateTime datumIzdavanja, boolean overen) {
		super(vremePregleda, datumIVremePocetka);
		this.datumIzdavanja = datumIzdavanja;
		this.overen = overen;
	}


	public LocalDateTime getDatumIzdavanja() {
		return datumIzdavanja;
	}


	public void setDatumIzdavanja(LocalDateTime datumIzdavanja) {
		this.datumIzdavanja = datumIzdavanja;
	}


	public boolean isOveren() {
		return overen;
	}


	public void setOveren(boolean overen) {
		this.overen = overen;
	}


	public static void kreirajIzvestaj(String korisnickoImeDoktora) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String linija;
		int redniBr = 1;
		boolean proveraFajla = false;
		ArrayList<String> listaPacijenata = new ArrayList<String>();
		ArrayList<String> listaSifriDijagnoza = new ArrayList<String>();
		Pacijent pacijent = new Pacijent();
		Doktor doktor = new Doktor();
		doktor.setKorisnickoIme(korisnickoImeDoktora);
		System.out.println("Unesite ime pacijenta: ");
		pacijent.setIme(sc.nextLine());
		
		BufferedReader ucitavanjeKorisnika = new BufferedReader(new FileReader("data\\Korisnici.csv"));
		while ((linija = ucitavanjeKorisnika.readLine()) != null)  
		{  
			String[] ispisKorisnika = linija.split(","); 
			if(pacijent.getIme().toLowerCase().equals(ispisKorisnika[0].toLowerCase()) && (ispisKorisnika[4].equals("1"))) {
				listaPacijenata.add(ispisKorisnika[0] + "," + ispisKorisnika[1] + "," + ispisKorisnika[2] + "," + ispisKorisnika[3] + "," + ispisKorisnika[4] + "," + ispisKorisnika[5]);
				System.out.println(redniBr + ". ime pacijenta: " + ispisKorisnika[0] + " " + ispisKorisnika[1]);
				proveraFajla = true;
				redniBr ++;
			}
		}
		
		if(proveraFajla == false) {
			System.out.println("Nema ni jedan pacijent sa tim imenom.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		System.out.println("Unesite redni broj pacijenta: "); // Biranje doktora po broju u slucaju da postoje dva doktora sa istim imenom
		boolean proveraFajlaRecepata = false;
		String[] pacijentKorisnickoIme = null;
		try {	
			int odabirPacijenta = sc.nextInt();
			pacijentKorisnickoIme = listaPacijenata.get(odabirPacijenta - 1).split(",");
			BufferedReader ucitavanjeRecepta = new BufferedReader(new FileReader("data\\Recepti.csv"));
			while ((linija = ucitavanjeRecepta.readLine()) != null)  
			{  
				String[] ispisKorisnika = linija.split("-");
				if(pacijentKorisnickoIme[2].equals(ispisKorisnika[0])) {
					int brojac = 0;
					String [] ispisDijagnoza = ispisKorisnika[1].split(",|\\]|\\["); // Splitujem znakove , ] [
					for(String i : ispisDijagnoza) {
						if(brojac % 2 != 0) {
							listaSifriDijagnoza.add(i);
						}
						brojac ++;
					}
					proveraFajlaRecepata = true;
				}
			}
			ucitavanjeRecepta.close();
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Uneli ste pogresan broj pacijenta.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		if(proveraFajlaRecepata == false) {
			System.out.println("Pacijent nema ni jednu dijagnozu.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		// Ucitava samo dijagnoze koje postoje
		boolean proveraFajlaDijagnoza = false;
		BufferedReader ucitavanjeDijagnoze = new BufferedReader(new FileReader("data\\Dijagnoza.csv"));
		System.out.println("Pacijent " + pacijentKorisnickoIme[0] + " " + pacijentKorisnickoIme[1] + " ima sledece dijagnoze.");
		while ((linija = ucitavanjeDijagnoze.readLine()) != null)  
		{  
			String[] ispisDijagnoze = linija.split(","); 
			for(String i : listaSifriDijagnoza) {
				if(i.replace(" ", "").equals(ispisDijagnoze[1])) {
					System.out.println(ispisDijagnoze[0]);
					proveraFajlaDijagnoza = true;
				}
			}
		}
		
		if(proveraFajlaDijagnoza == false) {
			System.out.println("Pacijent nema ni jednu dijagnozu.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		ucitavanjeKorisnika.close();
		ucitavanjeDijagnoze.close();
		sc.close();

	}
	
	public String stampaj() {
		return null;
	}


	public boolean overi() {
		return false;
	}
	
}
