package zdravstvenoStanje;

import java.io.*;
import java.util.*;

import pacijentiZaposleniUprava.Administrator;

public class Lek {
	
	private String naziv;
	private boolean uvozni;
	
	public Lek() {
		super();
	}

	public Lek(String naziv, boolean uvozni) {
		super();
		this.naziv = naziv;
		this.uvozni = uvozni;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	}

	public boolean isUvozni() {
		return uvozni;
	}

	public void setUvozni(boolean uvozni) {
		this.uvozni = uvozni;
	}
	
	public static void uklanjanjeLeka() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		String linija = "";
		boolean proveraFajla = false;
		boolean proveraLeka = false;
		List<String> listaLekova = new ArrayList<String>();
		Lek lek = new Lek();
		
		BufferedReader ucitavanjeLekova = new BufferedReader(new FileReader("data\\Lekovi.csv"));
		while ((linija = ucitavanjeLekova.readLine()) != null)  
		{  
			String[] ispisLekova = linija.split(","); 
			String uvozniLek;
			if(Boolean.parseBoolean(ispisLekova[1]) == false) {
				uvozniLek = "NE";
			} else {
				uvozniLek = "DA";
			}
			System.out.println("Ime leka: " + ispisLekova[0] + ", uvozno: " + uvozniLek);
			listaLekova.add(ispisLekova[0] + "," + ispisLekova[1]);
			proveraFajla = true;
		}
		
		
		if(proveraFajla == false) {
			System.out.println("Fajl je prazan.");
			Administrator.prikaziMeniAdministratora();
		} else {
			System.out.println("Unesite ime leka kojeg zelite da uklonite: ");
			lek.setNaziv(sc.nextLine());
			}
		
		for (Iterator<String> iterator = listaLekova.iterator(); iterator.hasNext();) {
			String i = iterator.next();
			String [] podela = i.split(","); // Deli svaki lek u poseban indeks 
			if (podela[0].equals(lek.getNaziv())){ //Uzima samo ime za proveru jednakosti
				iterator.remove();
			    System.out.println("Uspesno ste uklonili lek: " + lek.getNaziv());
			    proveraLeka = true; 
			    
			    //Brisemo sve iz fajla lekovi da bi dodali novu listu bez izbacenog leka
			    FileWriter fileWrite = new FileWriter("data\\Lekovi.csv", false); 
			    fileWrite.close();
			    
				BufferedWriter dodavanjeLeka = new BufferedWriter(new FileWriter("data\\Lekovi.csv", true)); 
			    for(String j : listaLekova) {
			    	File file = new File("data\\Lekovi.csv");
			    	if (file.length() == 0) {   // Proveravamo da li je fajl prazan i dodajemo samo jednu liniju bez newLine 
						dodavanjeLeka.write(j);
						dodavanjeLeka.flush(); // Radimo flush da se ne bi bafer napunio
						//dodavanjeLeka.close();
					} else {   
						dodavanjeLeka.newLine();
						dodavanjeLeka.write(j);
						dodavanjeLeka.flush(); // Radimo flush da se ne bi bafer napunio
						//dodavanjeLeka.close();
					}
			    }
			    dodavanjeLeka.close();
			}
		} 
		
		if(proveraLeka == false) {
			System.out.println("Uneli ste pogresan naziv leka.");
		}
		
		Administrator.prikaziMeniAdministratora();
		ucitavanjeLekova.close();
		sc.close();
	}
	
	public static void dodavanjeLeka() throws IOException {
		
		Scanner sc = new Scanner(System.in);
		File file = new File("data\\Lekovi.csv");
		Lek lek = new Lek();
		
		System.out.println("Unesite ime leka: ");
		lek.setNaziv(sc.nextLine());
		
		if(lek.getNaziv().equals("")) {
			System.out.println("Morate uneti ime leka.");
			Administrator.prikaziMeniAdministratora();
		}
		String linija;
		BufferedReader ucitavanjeLekova = new BufferedReader(new FileReader("data\\Lekovi.csv"));
		while ((linija = ucitavanjeLekova.readLine()) != null)  
		{  
			String[] ispisLekova = linija.split(",");
			if(lek.getNaziv().equals(ispisLekova[0])) {
				System.out.println("Uneli ste vec postojece ime leka.");
				Administrator.prikaziMeniAdministratora();
			}
		}
		ucitavanjeLekova.close();
		
		System.out.println("Da li je uvozan (DA|NE): ");
		String uvozniLek = sc.nextLine();
		
		if(uvozniLek.toLowerCase().equals("da")) {
			lek.setUvozni(true);
			
		} 
		else if(uvozniLek.toLowerCase().equals("ne")) {
			lek.setUvozni(false);
		}
		
		else 
		{
			System.out.println("Uneli ste pogresne parametre.");
			Administrator.prikaziMeniAdministratora();
		}
		
		String dodavanjeNovogLeka = lek.getNaziv() + "," + lek.isUvozni(); 
		BufferedWriter dodavanjeLeka = new BufferedWriter(new FileWriter("data\\Lekovi.csv", true));  
		System.out.println("Uspesno ste dodali lek: " + lek.getNaziv());
		
		if (file.length() == 0) {  
			dodavanjeLeka.write(dodavanjeNovogLeka);
			dodavanjeLeka.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeLeka.close();	
		} else {   
			dodavanjeLeka.newLine();
			dodavanjeLeka.write(dodavanjeNovogLeka);
			dodavanjeLeka.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeLeka.close();
		}
		
		Administrator.prikaziMeniAdministratora();
		sc.close();
	}

}
