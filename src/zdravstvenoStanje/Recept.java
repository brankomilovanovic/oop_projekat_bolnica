package zdravstvenoStanje;

import java.io.*;
import java.util.*;

import dokumentacija.Dokument;

public class Recept implements Dokument{
	
	private boolean overen;
	
	public Recept() {
		super();
	}
	
	public Recept(boolean overen) {
		super();
		this.overen = overen;
	}

	public boolean isOveren() {
		return overen;
	}

	public void setOveren(boolean overen) {
		this.overen = overen;
	}

	public String stampaj() {
		return null;
	}

	public boolean overi() {
		return false;
	}
	
	public static void ispisivanjeRecepta(String korisnickoImePacijenta, ArrayList<String> listaDodanihDijagnoza) throws IOException {

		Recept recept = new Recept();
		recept.setOveren(true);
		File file = new File("data\\Recepti.csv");
		BufferedWriter dodavanjeRecepata = new BufferedWriter(new FileWriter("data\\Recepti.csv", true));  

		String dodavanjeRecepta = korisnickoImePacijenta + "-" + listaDodanihDijagnoza + "-" + recept.isOveren();
		System.out.println("Uspesno ste ispisali i overili recept.");			
		if (file.length() == 0) {   
			dodavanjeRecepata.write(dodavanjeRecepta);
			dodavanjeRecepata.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeRecepata.close();
		} else {   
			dodavanjeRecepata.newLine();  
			dodavanjeRecepata.write(dodavanjeRecepta);
			dodavanjeRecepata.flush(); // Radimo flush da se ne bi bafer napunio
			dodavanjeRecepata.close();
		}
	}

}
