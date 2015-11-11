// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.io.*;
import java.util.*;
public class DiccVector implements Diccionario {

	private int nlenguas;
	private Vector<Character> lenguas;
	private Vector<Palabra2> dicc;
	
	public DiccVector() {
		nlenguas=-1;
		lenguas = new Vector<Character>();
		dicc = new Vector<Palabra2>();
	}
	
	// lee fichero y almacena en el diccionario, ordenado por origen, captura Exception
	public void leeDiccionario(String f) {
		if (f!=null) {
			FileReader fichero = null;
			BufferedReader lectura = null;
			try {
				// flujos
				fichero = new FileReader(f);
				lectura = new BufferedReader(fichero);
				String linea = lectura.readLine();
				nlenguas = Integer.parseInt(linea);
	
				// obtengo las partes
				linea = lectura.readLine();
				String partes[] = null;
				partes = linea.split("[ ]");
				
				for (int i = 0; i < nlenguas; i++) {
					lenguas.add(partes[i].charAt(0));
				}
				
				Palabra2 nueva = null;
				
				linea = lectura.readLine();
	
				// recorro las lineas, creando las palabras y sus traducciones e insertando
				while (linea != null) {
					partes = linea.split("[ ]*\\*[ ]*");
					
					char[] l = new char[nlenguas];
					for (int i = 0; i < nlenguas; i++) {
						l[i] = lenguas.elementAt(i);
					}
					nueva = new Palabra2(partes[0],l);
					
					for (int i = 1; i < partes.length; i++) {
						nueva.setTrad(partes[i],l[i-1]);
					}
					
					inserta(nueva);
					linea = lectura.readLine();
				}
			} catch (Exception e) {
				System.out.println(e);
			} finally {
				try {
					if (fichero != null)
						fichero.close();
					if (lectura != null)
						lectura.close();
				} catch (Exception ex) {
					System.out.println(ex);
				}
			}		
		}
	}

	// insertar de forma ordenada la nueva palabra en el vector si las lenguas coinciden
	// basicamente, recorro el diccionario elemento a elemento para asegurarme de que no esta repetido, y lo inserto alla donde corresponda
	// la cota superior es lineal, pues hay que recorrer todo el diccionario y empujar elementos
	// la cota inferior es constante, en caso de que las lenguas no coincidan
	public boolean inserta(Palabra2 p) {
		if (p!=null) {
			// comprobar que las lenguas coinciden
			char[] leng = p.getLenguas();
			if (leng.length!=lenguas.size()) {
				return false;
			}
			
			for (int i = 0; i < leng.length; i++) {
				if (leng[i]!=lenguas.elementAt(i)) {
					return false;
				}
			}
			
			boolean exito = false;
			
			// comprobar si ya existe. si existe, anadir cada traduccion a la ya existente
			for (int i = 0; i < dicc.size(); i++) {
				if (dicc.elementAt(i).getOrigen().equalsIgnoreCase(p.getOrigen())) {
					for (int j = 0; j < nlenguas; j++)
						if (p.getTraduccion(lenguas.get(j)) != "") {
							if (dicc.elementAt(i).setTrad(p.getTraduccion(lenguas.get(j)), lenguas.get(j)) >= 0)
								exito = true;
						}
				}
			}
			
			if (exito)
				return true;
			
			if (busca(p.getOrigen())<0) {		
				// no existe, insertar ordenadamente
				for (int i = 0; i < dicc.size(); i++) {
					if (p.getOrigen().compareToIgnoreCase(dicc.get(i).getOrigen()) < 0) {
						dicc.add(i, p);
						return true;
					}
				}
				
				// si llego aqui es que va al final
				dicc.add(p);
				return true;
			}
		}

		return false;
	}

	// borrar la palabra s del diccionario
	public boolean borra(String s) {
		if (s!=null) {
			for (int i = 0; i < dicc.size(); i++)
				if (dicc.elementAt(i).getOrigen().equalsIgnoreCase(s)) {
					dicc.removeElementAt(i);
					return true;
				}
		}
		return false;
	}

	// buscar un elemento. usar mejor algoritmo
	// puesto que cada elemento tiene un indice, y estan ordenados, utilizo la busqueda binaria
	// la cota superior es logaritmica, pues recorre el diccionario acotando por la mitad cada vez
	// la cota inferior es constante, en caso de que el tamaño del diccionario sea 0
	public int busca(String s) {
		if(s!=null && dicc.size() > 0) {						                                        
			int n = dicc.size();													
			int comp = 0;															
			int centro;																
			int inf = 0;																
			int sup = n-1;															
			while(inf<=sup){														
				comp++;																
				centro=(sup+inf)/2;													
				if(dicc.elementAt(centro).getOrigen().compareToIgnoreCase(s)==0)			
					return comp;
				else if (s.compareToIgnoreCase(dicc.elementAt(centro).getOrigen()) < 0){    
					sup=centro-1;				
				}
				else inf=centro+1;
		   } return comp*-1;
			
		}
		return -1;
	}

	// devolver la traduccion de s a l
	public String traduce(String s, char l) {
		if (s!=null) {
			for (Palabra2 p : dicc) {
				if (p.getOrigen().equalsIgnoreCase(s))
					return p.getTraduccion(l);
			}
		}
		return null;
	}

	// escribeInfo de todas las p2 en dicc
	public void visualiza() {
		for (Palabra2 p: dicc) {
			p.escribeInfo();
		}		
	}

	// escribeInfo de j primeras palabras
	public void visualiza(int j) {
		for (int i = 0; i < j && i < dicc.size(); i++) {
			dicc.elementAt(i).escribeInfo();
		}
	}

	// origen y traduccion a l de j primeras palabaras
	public void visualiza(int j, char l) {
		for (int i = 0; i < j && i < dicc.size(); i++) {
			if (dicc.get(i).getTraduccion(l)!=null)
				System.out.println(dicc.get(i).getOrigen() + ":" + dicc.get(i).getTraduccion(l));
			else
				System.out.println(dicc.get(i).getOrigen() + ":");
		}
	}
}
