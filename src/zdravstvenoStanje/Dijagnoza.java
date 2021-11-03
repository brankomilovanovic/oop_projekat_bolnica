package zdravstvenoStanje;

import java.io.*;
import java.util.*;

import pacijentiZaposleniUprava.Administrator;

public class Dijagnoza {

	private String naziv;
	private String sifra;
	
	public Dijagnoza() {
		super();
	}

	public Dijagnoza(String naziv, String sifra) {
		super();
		this.naziv = naziv;
		this.sifra = sifra;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public String getSifra() {
		return sifra;
	}

	public void setSifra(String sifra) {
		this.sifra = sifra;
	}
	
	public static void dodavanjeDijagnoze() throws IOException {
	
		Scanner sc = new Scanner(System.in);
		String linija = "";
		boolean proveraFajla = false;
		boolean uspesnoDodavanje = false;
		ArrayList<String> listaLekova = new ArrayList<String>();
		File file = new File("data\\Dijagnoza.csv");
		Dijagnoza dijagnoza = new Dijagnoza();
		Lek lek = new Lek();
		
		String[] ispisDijagnoza = null;
		BufferedReader ucitavanjeDijagnoze = new BufferedReader(new FileReader("data\\Dijagnoza.csv"));
		System.out.println("Unesite ime dijagnoze: ");
		dijagnoza.setNaziv(sc.nextLine());
		
		if(dijagnoza.getNaziv().equals("")) {
			System.out.println("Morate uneti naziv dijagnoze.");
			Administrator.prikaziMeniAdministratora();
		}
		System.out.println("Unesite sifru dijagnoze: ");
		dijagnoza.setSifra(sc.nextLine());
		if(dijagnoza.getSifra().equals("")) {
			System.out.println("Morate uneti sifru dijagnoze");
			Administrator.prikaziMeniAdministratora();
		}
		while ((linija = ucitavanjeDijagnoze.readLine()) != null)  
		{  
			ispisDijagnoza = linija.split(",");
			if(dijagnoza.getSifra().equals(ispisDijagnoza[1])) {
				System.out.println("Uneli ste vec postojecu sifru dijagnoze.");
				Administrator.prikaziMeniAdministratora();
			}
		}
		ucitavanjeDijagnoze.close();
		BufferedWriter dodavanjeNoveDijagnoze = new BufferedWriter(new FileWriter("data\\Dijagnoza.csv", true));  
		BufferedReader ucitavanjeLekova = new BufferedReader(new FileReader("data\\Lekovi.csv"));
		
		while ((linija = ucitavanjeLekova.readLine()) != null)  
		{  
			String[] ispisLekova = linija.split(","); 
			listaLekova.add(ispisLekova[0]);
			if(Boolean.parseBoolean(ispisLekova[1]) == false) {
				ispisLekova[1] = "NE";
			} else {
				ispisLekova[1] = "DA";
			}
			System.out.println("Ime leka: " + ispisLekova[0] + ", uvozno: " + ispisLekova[1]);
			proveraFajla = true;
		}
		
		if(proveraFajla == false) {
			System.out.println("Trenutno nema ni jedan lek.");
			String dodavanjeDijagnoze = dijagnoza.getNaziv() + "," + dijagnoza.getSifra() + "," + "nema";
			System.out.println("Uspesno ste dodali dijagnozu " + dijagnoza.getNaziv() + ", bez leka.");			
			if (file.length() == 0) {   
				dodavanjeNoveDijagnoze.write(dodavanjeDijagnoze);
				dodavanjeNoveDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
				dodavanjeNoveDijagnoze.close();
				uspesnoDodavanje = true;
				Administrator.prikaziMeniAdministratora();
			} else {   
				dodavanjeNoveDijagnoze.newLine();  
				dodavanjeNoveDijagnoze.write(dodavanjeDijagnoze);
				dodavanjeNoveDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
				dodavanjeNoveDijagnoze.close();
				uspesnoDodavanje = true;
				Administrator.prikaziMeniAdministratora();
			}
		} 
		
		System.out.println("Unesite ime leka za dijagnozu: ");
		lek.setNaziv(sc.nextLine());
		
		for (String i : listaLekova) {
			if(lek.getNaziv().equals(i)) {
				String dodavanjeDijagnoze = dijagnoza.getNaziv() + "," + dijagnoza.getSifra() + "," + lek.getNaziv();
				System.out.println("Uspesno ste dodali dijagnozu " + dijagnoza.getNaziv() + ".");
				if (file.length() == 0) {   
					dodavanjeNoveDijagnoze.write(dodavanjeDijagnoze);
					dodavanjeNoveDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
					dodavanjeNoveDijagnoze.close();
					uspesnoDodavanje = true;
					Administrator.prikaziMeniAdministratora();
				} else {   
					dodavanjeNoveDijagnoze.newLine();  
					dodavanjeNoveDijagnoze.write(dodavanjeDijagnoze);
					dodavanjeNoveDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
					dodavanjeNoveDijagnoze.close();
					uspesnoDodavanje = true;
					Administrator.prikaziMeniAdministratora();
				}
			}
		}
		
		if(uspesnoDodavanje == true) {
			Administrator.prikaziMeniAdministratora();
		} 
		else if(uspesnoDodavanje == false) {
			System.out.println("Uneli ste pogresno ime leka.");
			Administrator.prikaziMeniAdministratora();
		}
		
		ucitavanjeLekova.close();
		sc.close();
	}
	
	public static void uklanjanjeDijagnoze() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		boolean proveraFajla = false;
		boolean proveraDijagnoze = false;
		String linija =	"";
		List<String> listaDijagnoza = new ArrayList<String>();
		Dijagnoza dijagnoza = new Dijagnoza();
		
		BufferedReader ucitavanjeDijagnoze = new BufferedReader(new FileReader("data\\Dijagnoza.csv"));
		while ((linija = ucitavanjeDijagnoze.readLine()) != null)  
		{  
			String[] ispisDijagnoza = linija.split(",");
			System.out.println("Naziv: " + ispisDijagnoza[0] + ", Sifra: " + ispisDijagnoza[1] + ", Lek: " + ispisDijagnoza[2]);
			listaDijagnoza.add(ispisDijagnoza[0] + "," + ispisDijagnoza[1] + "," + ispisDijagnoza[2]);
			proveraFajla = true;
		}
		
		if(proveraFajla == false) {
			System.out.println("Fajl je prazan.");
			Administrator.prikaziMeniAdministratora();
		} else {
			System.out.println("Unesite sifru dijagnoze koju zelite da uklonite: ");
			dijagnoza.setSifra(sc.nextLine());
		}
		
		for (Iterator<String> iterator = listaDijagnoza.iterator(); iterator.hasNext();) {
			String i = iterator.next();
			String [] podela = i.split(","); // Deli svaku dijagnozu u poseban indeks 
			if (podela[1].equals(dijagnoza.getSifra())){ //Uzima samo ime za proveru jednakosti
				iterator.remove();
			    System.out.println("Uspesno ste uklonili dijagnozu: " + dijagnoza.getNaziv());
			    proveraDijagnoze = true; 
			    
			    //Brisemo sve iz fajla dijagnoze da bi dodali novu listu bez izbacene dijagnoze
			    FileWriter fileWrite = new FileWriter("data\\Dijagnoza.csv", false); 
			    fileWrite.close();
			    
				BufferedWriter dodavanjeDijagnoze = new BufferedWriter(new FileWriter("data\\Dijagnoza.csv", true)); 
			    for(String j : listaDijagnoza) {
			    	File file = new File("data\\Dijagnoza.csv");
			    	if (file.length() == 0) {   // Proveravamo da li je fajl prazan i dodajemo samo jednu liniju bez newLine 
			    		dodavanjeDijagnoze.write(j);
			    		dodavanjeDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
					} else {   
						dodavanjeDijagnoze.newLine();
						dodavanjeDijagnoze.write(j);
						dodavanjeDijagnoze.flush(); // Radimo flush da se ne bi bafer napunio
					}
			    }
			    dodavanjeDijagnoze.close();
			}
		} 
		
		if(proveraDijagnoze == false) {
			System.out.println("Uneli ste pogresnu sifru dijagnoze.");
		}
		
		Administrator.prikaziMeniAdministratora();
		ucitavanjeDijagnoze.close();
		sc.close();
	}
}
