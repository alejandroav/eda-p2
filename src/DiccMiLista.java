// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.io.*;
import java.util.*;
public class DiccMiLista implements Diccionario {
	
	public class NodoL {
		private Palabra2 pal;
		private NodoL next;
		
		public NodoL() {
			pal = null;
			next = null;
		}
		
		public NodoL(Palabra2 p) {
			pal = p;
			next = null;
		}
		
		public void cambiaNext(NodoL n) {
			next = n;
		}
		
		public void setPalabra2(Palabra2 p) {
			pal = p;
		}
		
		public NodoL getNext() {
			return next;
		}
		
		public Palabra2 getPalabra2() {
			return pal;
		}
	}
	
	private int nlenguas;
	private Vector<Character> lenguas;
	private NodoL dicc;
	
	public DiccMiLista() {
		nlenguas = -1;
		lenguas = new Vector<Character>();
		dicc = null;
	}

	@Override
	public void leeDiccionario(String f) {
		// TODO Auto-generated method stub
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

	// insertar la palabra p en el diccionario
	public boolean inserta(Palabra2 p) {
		if (p!=null) {
			// comprobar que las lenguas coinciden
			char[] leng = p.getLenguas();
			
			if (leng.length!=lenguas.size())
				return false;
			
			for (int i = 0; i < leng.length; i++)
				if (leng[i]!=lenguas.elementAt(i))
					return false;
			
			// si la lista esta vacia, insertar
			if (dicc==null) {
				dicc.setPalabra2(p);
				return true;
			}

			boolean exito = false;
			
			// comprobar si ya existe. si existe, anadir cada traduccion a la ya existente
			NodoL iterador = dicc;
			
			while(iterador!=null) {
				if (iterador.getPalabra2().getOrigen().equalsIgnoreCase(p.getOrigen())) {
					for (int j = 0; j < nlenguas; j++)
						if (p.getTraduccion(lenguas.get(j)) != "") {
							dicc.getPalabra2().setTrad(p.getTraduccion(lenguas.get(j)), lenguas.get(j));
							exito = true;
						}
				}
				iterador = iterador.getNext();
			}
			
			if (exito) return true;
			
			// no existe, insertar ordenadamente
			iterador = dicc; exito = false;
			NodoL ant = null; NodoL nuevo = new NodoL(p);
			
			// insertamos en lista
			while (iterador!=null && !exito) {
				if (p.getOrigen().compareToIgnoreCase(iterador.getPalabra2().getOrigen()) < 0) {
					if (ant==null) 
						dicc = nuevo;
					
					else
						ant.cambiaNext(nuevo);
					
					nuevo.cambiaNext(iterador);
					exito = true;
				}
				ant = iterador;
				iterador = iterador.getNext();
			}
			
			// si llegamos aqui va al final
			if (!exito)
				ant.cambiaNext(nuevo);
			
			return true;
		}
		return false;
	}

	// borrar la palabra s del diccionario
	public boolean borra(String s) {
		if (s!=null) {
			NodoL iterador = dicc;
			NodoL ant = null;
			while(iterador!=null) {
				if (iterador.getPalabra2().getOrigen().equalsIgnoreCase(s)) {
					NodoL aux = iterador.getNext();
					
					if (ant==null)
						dicc = aux;
					else
						ant.cambiaNext(aux);
					
					return true;
				}
				ant = iterador;
				iterador = iterador.getNext();
			}
		}
		return false;
	}

	// buscar un elemento. usar mejor algoritmo
	// como se trata de una lista enlazada simple, el unico metodo de busqueda es ir uno a uno
	// la cota superior es lineal, pues hay que recorrer uno a uno los elementos
	// la cota inferior es constante si el diccionario esta vacio, o el string s es null
	public int busca(String s) {
		if(s!=null) {
			NodoL iterador = dicc; int c = 0;
			while (iterador!=null) {
				c++;
				if (iterador.getPalabra2().getOrigen().equalsIgnoreCase(s))
					return c;
				
				iterador = iterador.getNext();
			}		   
		}
		return -1;
	}

	// devolver la traduccion de s a l
	public String traduce(String s, char l) {
		if (s!=null) {
			NodoL iterador = dicc;
			while (iterador!=null) {
				if (iterador.getPalabra2().getOrigen().equalsIgnoreCase(s))
					return iterador.getPalabra2().getTraduccion(l);
				
				iterador = iterador.getNext();
			}
		}
		return null;
	}

	// escribeInfo de todas las p2 en dicc
	public void visualiza() {
		NodoL iterador = dicc;
		while (iterador!=null) {
			iterador.getPalabra2().escribeInfo();
			iterador = iterador.getNext();
		}		
	}

	// escribeInfo de j primeras palabras
	public void visualiza(int j) {
		NodoL iterador = dicc;
		for (int i = 0; i < j; i++) {
			if (iterador == null)
				return;
			
			iterador.getPalabra2().escribeInfo();
			iterador = iterador.next;
		}
	}

	// origen y traduccion a l de j primeras palabaras
	public void visualiza(int j, char l) {
		NodoL iterador = dicc;
		for (int i = 0; i < j; i++) {
			if (iterador == null)
				return;

			if (iterador.getPalabra2().getTraduccion(l)!=null)
				System.out.println(iterador.getPalabra2().getOrigen() + ":" + iterador.getPalabra2().getTraduccion(l));
			else
				System.out.println(iterador.getPalabra2().getOrigen() + ":");
			
			iterador = iterador.next;
		}
	}
}
