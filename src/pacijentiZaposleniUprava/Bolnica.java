package pacijentiZaposleniUprava;

public class Bolnica {
	
	private String naziv;
	
	public Bolnica() {
		super();
	}

	public Bolnica(String naziv) {
		super();
		this.naziv = naziv;
	}

	public String getNaziv() {
		return naziv;
	}

	public void setNaziv(String naziv) {
		this.naziv = naziv;
	
	}
	
	public String toString() {
		return "Bolnica [naziv=" + naziv + "]";
	}
	
	

}
