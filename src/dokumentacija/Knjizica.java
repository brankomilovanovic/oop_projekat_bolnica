package dokumentacija;

import java.io.*;
import java.time.*;
import java.util.*;

import pacijentiZaposleniUprava.Pacijent;

public class Knjizica {

	private String identifikator;
	private LocalDate rokVazenja;
	private static BufferedReader ucitavanjeKnjizice;
	static List<String> listaKnjizica = new ArrayList<String>();
	static List<String> listaPacijenata = new ArrayList<String>();
	
	public Knjizica() {
		super();
	}
	
	public Knjizica(String identifikator, LocalDate rokVazenja) {
		super();
		this.identifikator = identifikator;
		this.rokVazenja = rokVazenja;
	}

	public String getIdentifikator() {
		return identifikator;
	}

	public void setIdentifikator(String identifikator) {
		this.identifikator = identifikator;
	}

	public LocalDate getRokVazenja() {
		return rokVazenja;
	}

	public void setRokVazenja(LocalDate rokVazenja) {
		this.rokVazenja = rokVazenja;
	}
	
	public static void ucitavanjeSvihKnjizica() throws IOException {
		String linija;
		listaKnjizica.clear();
		listaPacijenata.clear();
		BufferedReader ucitavanjeSvihKnjizice = new BufferedReader(new FileReader("data\\Knjizice.csv")); 
		while ((linija = ucitavanjeSvihKnjizice.readLine()) != null)  {  
		String[] sveKnjizice = linija.split(","); 
		listaKnjizica.add(sveKnjizice[0] + "," + sveKnjizice[1]);
		listaPacijenata.add(sveKnjizice[0]);
	    }
		ucitavanjeSvihKnjizice.close();
	}
	
	public static String ucitavanjeKnjizice(String korisnickoImePacijenta) throws IOException {
		String linija;
		ucitavanjeKnjizice = new BufferedReader(new FileReader("data\\Knjizice.csv")); 
		Knjizica knjizica = new Knjizica();
		while ((linija = ucitavanjeKnjizice.readLine()) != null)  {  
		String[] sveKnjizice = linija.split(","); 
		if(korisnickoImePacijenta.equals(sveKnjizice[0])) { 
			knjizica.setIdentifikator(sveKnjizice[0]);
			knjizica.setRokVazenja(LocalDate.parse(sveKnjizice[1]));	
			return knjizica.getIdentifikator() + "," + knjizica.getRokVazenja();
			}
		}
		ucitavanjeKnjizice.close();
		return "Korisnik nema knjizicu" + "," + " ili je treba overiti.";
	}
	
	public static void overavanjeKnjizice(String korisnickoImePacijenta) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		Knjizica knjizica = new Knjizica();
		ucitavanjeSvihKnjizica();
		String[] knjizicaPacijenta = Knjizica.ucitavanjeKnjizice(korisnickoImePacijenta).split(",");
		try {
			knjizica.setIdentifikator(knjizicaPacijenta[0]);
			knjizica.setRokVazenja(LocalDate.parse(knjizicaPacijenta[1]));
			if(LocalDate.now().isBefore(knjizica.getRokVazenja())) {
				System.out.println("Knjizica vam nije istekla, ne mozete je overiti.");
				Pacijent.prikaziMeniPacijenta(korisnickoImePacijenta);
			} 
		} catch (java.time.format.DateTimeParseException e ) { //hvata gresku pri spajanju stringa kada korisnik nema knjizicu
			System.out.println("Pacijent nema knjizicu.");
			Pacijent.prikaziMeniPacijenta(korisnickoImePacijenta);
		}
		System.out.println("Trenutni datum vazenja knjizice: " + knjizica.rokVazenja);
		System.out.println("Unesite broj meseci na koliko produzavate knjizicu (12,24 ili 36): ");
		int mesec = 0;
		try {
			mesec = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			Pacijent.prikaziMeniPacijenta(korisnickoImePacijenta);
		}
		if((mesec != 12) && (mesec != 24) && (mesec != 36)) {
			System.out.println("Mozete uneti samo 12,24 ili 36 meseca.");
		} else {
			knjizica.setRokVazenja(knjizica.getRokVazenja().plusMonths(mesec)); // Uvecavamo rok vazenja za broj meseci
			System.out.println("Produzili ste rok vazenja knjizice, novi datum vazenja je: " + knjizica.getRokVazenja());			
			String[] promenaDatuma = listaKnjizica.get(listaPacijenata.indexOf(korisnickoImePacijenta)).split(",");
			listaKnjizica.remove(listaPacijenata.indexOf(korisnickoImePacijenta)); // Brisemo stari datum iz liste
			promenaDatuma[1] = knjizica.getRokVazenja().toString();
			listaKnjizica.add(promenaDatuma[0] + "," + promenaDatuma[1]); // Dodajemo novi datum u listu
			
			FileWriter fileWrite = new FileWriter("data\\Knjizice.csv", false); 
		    fileWrite.close();
		   
			for(String i : listaKnjizica) {
				BufferedWriter dodavanjeKnjizice = new BufferedWriter(new FileWriter("data\\Knjizice.csv", true)); 
				File file = new File("data\\Knjizice.csv");
		    	if (file.length() == 0) {   // Proveravamo da li je fajl prazan i dodajemo samo jednu liniju bez newLine 
		    		dodavanjeKnjizice.write(i);
		    		dodavanjeKnjizice.flush(); // Radimo flush da se ne bi bafer napunio
		    		dodavanjeKnjizice.close();
				} else {   
					dodavanjeKnjizice.newLine();
					dodavanjeKnjizice.write(i);
					dodavanjeKnjizice.flush(); // Radimo flush da se ne bi bafer napunio
					dodavanjeKnjizice.close();
				}
			}
		}	
	Pacijent.prikaziMeniPacijenta(korisnickoImePacijenta);
	sc.close();
	}
}

