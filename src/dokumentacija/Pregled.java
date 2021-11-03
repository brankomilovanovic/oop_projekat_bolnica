package dokumentacija;

import java.io.*;
import java.time.*;
import java.util.*;

import pacijentiZaposleniUprava.*;
import zdravstvenoStanje.*;


public class Pregled {
	
	final static long tranjanjePregleda = 20;
	private long vremePregleda;
	private LocalDateTime datumIVremePocetka;
	
	public Pregled() {
		super();
	}

	public Pregled(long vremePregleda, LocalDateTime datumIVremePocetka) {
		super();
		this.vremePregleda = vremePregleda;
		this.datumIVremePocetka = datumIVremePocetka;
	}

	public long getVremePregleda() {
		return vremePregleda;
	}

	public void setVremePregleda(long vremePregleda) {
		this.vremePregleda = vremePregleda;
	}

	public LocalDateTime getDatumIVremePocetka() {
		return datumIVremePocetka;
	}

	public void setDatumIVremePocetka(LocalDateTime datumIVremePocetka) {
		this.datumIVremePocetka = datumIVremePocetka;
	}

	public static long getTranjanjepregleda() {
		return tranjanjePregleda;
	}

	public static void pregled(String korisnickoImeDoktora) throws IOException {
		
		Scanner sc = new Scanner(System.in);

		String linija;
		int redniBrojPregleda = 1;
		int odabirPacijenta = 0;
		boolean proveraFajla = false;
		ArrayList<String> listaDodanihDijagnoza= new ArrayList<String>();
		ArrayList<String> listaPacijenata = new ArrayList<String>();
		ArrayList<String> listaDijagnoza = new ArrayList<String>();
		ArrayList<String> listaKorisnika = new ArrayList<String>();
		ArrayList<String> listaPacijenataOdabranogDoktora = new ArrayList<String>();
		ArrayList<String> prazanaListaDijagnoza = new ArrayList<String>();
		prazanaListaDijagnoza.add("prazno");
		Doktor doktor = new Doktor();
		doktor.setKorisnickoIme(korisnickoImeDoktora);
		
		BufferedReader ucitavanjePregleda = new BufferedReader(new FileReader("data\\Pregledi.csv"));
		while ((linija = ucitavanjePregleda.readLine()) != null)  
		{  
			String[] ispisPregleda = linija.split(",");
			String[] vremePregleda = ispisPregleda[7].split("T");
			listaKorisnika.add(ispisPregleda[0] + "," + ispisPregleda[1] + "," + ispisPregleda[2] + "," + ispisPregleda[3] + "," + ispisPregleda[4] + "," + ispisPregleda[5] + "," + ispisPregleda[6] + "," + ispisPregleda[7] + "," + ispisPregleda[8]);
			if((ispisPregleda[0].equals(doktor.getKorisnickoIme())) && (ispisPregleda[8].equals("0"))) {
				System.out.println(redniBrojPregleda + ". ime pacijenta: " + ispisPregleda[5] + " " + ispisPregleda[6] + ", termin pregleda: " + vremePregleda[0] + " u " + vremePregleda[1]);
				listaPacijenata.add(ispisPregleda[4] + "," + ispisPregleda[5] + "," + ispisPregleda[6] + "," + ispisPregleda[7]);
				listaPacijenataOdabranogDoktora.add(ispisPregleda[0] + "," + ispisPregleda[1] + "," + ispisPregleda[2] + "," + ispisPregleda[3] + "," + ispisPregleda[4] + "," + ispisPregleda[5] + "," + ispisPregleda[6] + "," + ispisPregleda[7] + "," + ispisPregleda[8]);
				redniBrojPregleda ++;
				proveraFajla = true;
			}			
		}
		
		if(proveraFajla == false) {
			System.out.println("Nemate zakazan ni jedan pregled.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		System.out.println("Unesite redni broj pacijenta kojeg zelite da pregledate: ");
		try {
			odabirPacijenta = sc.nextInt();
			listaPacijenataOdabranogDoktora.get(odabirPacijenta - 1); // odmah proverava da li ima taj indeks u listi
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Uneli ste pogresan broj pacijenta.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		} catch (InputMismatchException e) {
			System.out.println("Ne mozete unositi tekst.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		
		System.out.println("Da li zelite da dodate dijagnoze pacijentu (DA|NE): ");
		String dodavanjeDijagnoze = sc.next();
		
		int redniBrojDijagnoze = 1;
		boolean proveraFajlaDijagnoze = false;
		BufferedReader ucitavanjeDijagnoze = new BufferedReader(new FileReader("data\\Dijagnoza.csv"));
		if(dodavanjeDijagnoze.toLowerCase().equals("da")) {
			while ((linija = ucitavanjeDijagnoze.readLine()) != null)  
			{  
				String[] ispisDijagnoza = linija.split(",");
				System.out.println(redniBrojDijagnoze + ". ime dijagnoze: " + ispisDijagnoza[0] + ", sifra dijagnoze: " + ispisDijagnoza[1] + ", lek za lecenje: " + ispisDijagnoza[2]);
				listaDijagnoza.add(ispisDijagnoza[0] + "," + ispisDijagnoza[1] + "," + ispisDijagnoza[2]);
				redniBrojDijagnoze ++;
				proveraFajlaDijagnoze = true;			
			}
			
			if(proveraFajlaDijagnoze == false) {
				System.out.println("Trenutno nema ni jedne dijagnoze da biste je dodelili pacijentu.");
				 Doktor.prikaziMeniDoktora(korisnickoImeDoktora);
			}
			
		    while(true) {
		    	int odabirDijagnoze = -1;
				try {
					System.out.println("Unesite redni broj dijagnoze (za kraj unesite 0): ");
				    odabirDijagnoze = sc.nextInt();
					String[] odabranaDijagnoza = listaDijagnoza.get(odabirDijagnoze - 1).split(",");
					listaDodanihDijagnoza.add(odabranaDijagnoza[1] + "," + odabranaDijagnoza[2]);
					System.out.println("Dodali ste dijagnozu: " + odabranaDijagnoza[0]);
					
				} catch (InputMismatchException e) {
					System.out.println("Ne mozete unositi tekst.");
					sc.next();
					continue;
				} catch (IndexOutOfBoundsException e) {
					if(odabirDijagnoze == 0) {
						
						String[] uradjenPregled = listaPacijenataOdabranogDoktora.get(odabirPacijenta - 1).split(",");
						String uradjenPregledIspis = uradjenPregled[0] + "," + uradjenPregled[1] + "," + uradjenPregled[2] + "," + uradjenPregled[3] + "," + uradjenPregled[4] + "," + uradjenPregled[5] + "," + uradjenPregled[6] + "," + uradjenPregled[7] + ",1";
					
						listaKorisnika.remove(listaPacijenataOdabranogDoktora.get(odabirPacijenta - 1));
						listaKorisnika.add(uradjenPregledIspis);
						
						FileWriter fileWrite = new FileWriter("data\\Pregledi.csv", false); 
					    fileWrite.close();
					    
					    for(String i : listaKorisnika) {
							BufferedWriter dodavanjePregleda = new BufferedWriter(new FileWriter("data\\Pregledi.csv", true)); 
							File file = new File("data\\Pregledi.csv");
					    	if (file.length() == 0) {   // Proveravamo da li je fajl prazan i dodajemo samo jednu liniju bez newLine 
					    		dodavanjePregleda.write(i);
					    		dodavanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
					    		dodavanjePregleda.close();
							} else {   
								dodavanjePregleda.newLine();
								dodavanjePregleda.write(i);
								dodavanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
								dodavanjePregleda.close();
							}
						}
					    Recept.ispisivanjeRecepta(uradjenPregled[4], listaDodanihDijagnoza);
						break;
					}
					System.out.println("Uneli ste pogresan broj dijagnoze.");
					continue;
				}
				
		    }
		}
		else if(dodavanjeDijagnoze.toLowerCase().equals("ne")) {
			proveraFajlaDijagnoze = true;
			String[] uradjenPregled = listaPacijenataOdabranogDoktora.get(odabirPacijenta - 1).split(",");
			String uradjenPregledIspis = uradjenPregled[0] + "," + uradjenPregled[1] + "," + uradjenPregled[2] + "," + uradjenPregled[3] + "," + uradjenPregled[4] + "," + uradjenPregled[5] + "," + uradjenPregled[6] + "," + uradjenPregled[7] + ",1";
		
			listaKorisnika.remove(listaPacijenataOdabranogDoktora.get(odabirPacijenta - 1));
			listaKorisnika.add(uradjenPregledIspis);
			
			FileWriter fileWrite = new FileWriter("data\\Pregledi.csv", false); 
		    fileWrite.close();
		    
		    for(String i : listaKorisnika) {
				BufferedWriter dodavanjePregleda = new BufferedWriter(new FileWriter("data\\Pregledi.csv", true)); 
				File file = new File("data\\Pregledi.csv");
		    	if (file.length() == 0) {   // Proveravamo da li je fajl prazan i dodajemo samo jednu liniju bez newLine 
		    		dodavanjePregleda.write(i);
		    		dodavanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
		    		dodavanjePregleda.close();
				} else {   
					dodavanjePregleda.newLine();
					dodavanjePregleda.write(i);
					dodavanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
					dodavanjePregleda.close();
				}
			}
		    Recept.ispisivanjeRecepta(uradjenPregled[4], prazanaListaDijagnoza);
		    Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		else {
			System.out.println("Uneli ste pogresne parametre, mozete uneti samo DA ili NE.");
			proveraFajlaDijagnoze = true;
		}
		
		if(proveraFajlaDijagnoze == false) {
			System.out.println("Trenutno nema ni jedne dijagnoze.");
			Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		}
		Doktor.prikaziMeniDoktora(doktor.getKorisnickoIme());
		ucitavanjePregleda.close();
		ucitavanjeDijagnoze.close();
		sc.close();
	}
	
	public static void zakazaniPregledi(String korisnickoImePacijenta) throws IOException {
		String linija;
		Pacijent pacijent = new Pacijent();
		boolean proveraFajla = false;
		BufferedReader ucitavanjePregleda= new BufferedReader(new FileReader("data\\Pregledi.csv")); 
		while ((linija = ucitavanjePregleda.readLine()) != null)  {  
		String[] sviPregledi = linija.split(","); 
		String[] vremePregleda = sviPregledi[7].split("T");
		
		pacijent.setKorisnickoIme(korisnickoImePacijenta);
		
		if(pacijent.getKorisnickoIme().equals(sviPregledi[4])) { 
			proveraFajla = true;
			System.out.println("Datum pregleda: " + vremePregleda[0] + ", vreme pregleda: " + vremePregleda[1] + ", doktor: " + sviPregledi[1] + " " + sviPregledi[2] + ", specijalizacija: " + sviPregledi[3]);
			}
		}
		
		if(proveraFajla == false) {
			System.out.println("Nemate ni jedan pregled zakazan.");
			Pacijent.prikaziMeniPacijenta(pacijent.getKorisnickoIme());
		}
	Pacijent.prikaziMeniPacijenta(pacijent.getKorisnickoIme());
	ucitavanjePregleda.close();
	}
	
	public static void zakazivanjeTerminaPregleda(String korisnickoImePacijenta) throws IOException {
		
		Scanner sc = new Scanner(System.in);
		Pregled pregled = new Pregled();
		BufferedWriter pisanjePregleda = new BufferedWriter(new FileWriter("data\\Pregledi.csv", true));  
		BufferedReader ucitavanjeDoktora = new BufferedReader(new FileReader("data\\Korisnici.csv"));
		File file = new File("data\\Pregledi.csv");
		String linija;
		Pacijent pacijent = new Pacijent();
		int redniBrojDoktora = 1;
		String[] ispisDoktora = null;
		boolean proveraFajla = false;
		String satIMinut;
		List<String> listaDoktora = new ArrayList<String>();
		String[] proveraPacijenta = Knjizica.ucitavanjeKnjizice(korisnickoImePacijenta).split(","); 
		try {
			if(LocalDate.now().isAfter(LocalDate.parse(proveraPacijenta[1]))) {
				System.out.println("Knjizica vam je istekla, prvo je overite.");
			} else {
				try {
					System.out.println("Unesite zeljeni datum pregleda (FORMAT MM-DD): ");
					String mesecIDan = sc.nextLine();
					if(LocalDate.now().isAfter(LocalDate.parse(LocalDate.now().getYear() + "-" + mesecIDan))) {
						System.out.println("Ne mozete uneti prosli datum.");
					} else {
						System.out.println("Unesite zeljeno vreme pregleda (FORMAT HH:MM): ");
						satIMinut = sc.nextLine();
						pregled.setDatumIVremePocetka(LocalDateTime.parse(LocalDateTime.now().getYear() + "-" + mesecIDan + "T" + satIMinut));
						while ((linija = ucitavanjeDoktora.readLine()) != null)  {  
							ispisDoktora = linija.split(","); 
							if(ispisDoktora[4].equals("2")) {
								listaDoktora.add(ispisDoktora[2] + "," + ispisDoktora[0] + "," + ispisDoktora[1] + "," + ispisDoktora[5]);
								System.out.println(redniBrojDoktora + ". ime doktora: " + ispisDoktora[0] + ", prezime: " + ispisDoktora[1] + ", specijalizacija: " + ispisDoktora[5]);
								proveraFajla = true;
								redniBrojDoktora ++;
							} 
						}
						
						while ((linija = ucitavanjeDoktora.readLine()) != null)  {  
							if(korisnickoImePacijenta.equals(ispisDoktora[2])) {
								pacijent.setIme(ispisDoktora[0]);
								pacijent.setPrezime(ispisDoktora[1]);
								pacijent.setKorisnickoIme(ispisDoktora[1]);
							}
						}
						
						if(proveraFajla == false) {
							System.out.println("Ne postoji ni jedan registrovani doktor.");
						} else {
							System.out.println("Unesite redni broj doktora: "); // Biranje doktora po broju u slucaju da postoje dva doktora sa istim imenom
							int odabirDoktora = sc.nextInt();		
							try {
								String dodavanjePregleda = listaDoktora.get(odabirDoktora - 1) + "," + pacijent.getKorisnickoIme() + "," + pacijent.getIme() + "," + pacijent.getPrezime() + "," + pregled.getDatumIVremePocetka() + ",0"; 
								System.out.println("Uspesno ste zakazali pregled kod doktora.");
								
								if (file.length() == 0) {  
									pisanjePregleda.write(dodavanjePregleda);
									pisanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
									pisanjePregleda.close();	
								} else {   
									pisanjePregleda.newLine();
									pisanjePregleda.write(dodavanjePregleda);
									pisanjePregleda.flush(); // Radimo flush da se ne bi bafer napunio
									pisanjePregleda.close();
								}
							} catch (IndexOutOfBoundsException e) {
								System.out.println("Uneli ste pogresan broj doktora.");
							}
						}
					}
				} catch (Exception e) {
					System.out.println("Uneli ste pogresan format datuma (MM-DD) ili vremena (HH:MM).");
				}
			}
		} catch (java.time.format.DateTimeParseException e) {} // Ukoliko fromat nije dobar radi except

		Pacijent.prikaziMeniPacijenta(korisnickoImePacijenta);
		ucitavanjeDoktora.close();
		sc.close();
		
	}
}
