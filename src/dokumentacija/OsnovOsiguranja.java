package dokumentacija;

public class OsnovOsiguranja {
	
	public enum Osiguranje {zaposleni, student, dete, korisnikSocijalnihUsluga, penzioner}
	private Osiguranje osiguranje;
	
	public OsnovOsiguranja() {
		super();
	}

	public OsnovOsiguranja(Osiguranje osiguranje) {
		this.osiguranje = osiguranje;
	}

	public Osiguranje getOsiguranje() {
		return osiguranje;
	}

	public void setOsiguranje(Osiguranje osiguranje) {
		this.osiguranje = osiguranje;
	}
}
