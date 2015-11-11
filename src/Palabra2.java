// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.util.*;
public class Palabra2 {
	private char[] lenguas;
	private String origen;
	private Vector<String> trad;
	
	public Palabra2(String p, char[] lenguas) {
		origen=p;
		
		if (lenguas==null) { 
			this.lenguas = new char[3];
			this.lenguas[0] = 'E';
			this.lenguas[1] = 'F';
			this.lenguas[2] = 'P';
		}
		else
			this.lenguas = lenguas;
		
		trad = new Vector<String>();
		trad.setSize(this.lenguas.length);
	}	
	
	// inserta t en trad si existe la lengua l
	public int setTrad(String t, char l) {
		if (t!=null && !t.equalsIgnoreCase("")) {
			for (int i = 0; i < lenguas.length; i++)
				if (lenguas[i]==l && !t.equalsIgnoreCase(trad.get(i))) {
					trad.set(i, t);
					return i;
				}
		}
		return -1;
	}
	
	public String getOrigen() {
		return origen;
	}
	
	// devuelve el string de trad para la lengua l
	public String getTraduccion(char l) {
		for (int i = 0; i < lenguas.length; i++)
			if (lenguas[i]==l)
				return trad.elementAt(i);
		
		return null;
	}
	
	// devuelve origen:trad:trad:...
	public void escribeInfo() {
		String value = origen;
		
		for (int i = 0; i < trad.size(); i++)
			if (trad.elementAt(i)!=null)
				value += ":" + trad.elementAt(i);
			else
				value += ":";
		
		System.out.println(value);
	}
	
	// necesito recuperar el array de lenguas para comparar
	public char[] getLenguas() {
		return lenguas;
	}
}