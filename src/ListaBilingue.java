// DNI 45928098 ALARCON VILLENA, ALEJANDRO
import java.io.*;
import java.util.*;

public class ListaBilingue {
	public class NodoLD {
		private String origen;
		private String trad;
		private NodoLD nextOrigen;
		private NodoLD nextTrad;
		
		public NodoLD() {
			origen=trad=null;
			nextOrigen=nextTrad=null;
		}
		
		public NodoLD(String o, String t) {
			origen = o;
			trad = t;
		}
		
		public void cambiaNextOrigen(NodoLD n) {
			nextOrigen = n;
		}
		
		public void cambiaNextTrad(NodoLD n) {
			nextTrad = n;
		}
		
		public void setOrigen(String p) {
			origen = p;
		}
		
		public void setTrad(String p) {
			trad = p;
		}
		
		public NodoLD getNextOrigen() {
			return nextOrigen;
		}
		
		public NodoLD getNextTrad() {
			return nextTrad;
		}
		
		public String getOrigen() {
			return origen;
		}
		
		public String getTrad() {
			return trad;
		}
	}
	
	private NodoLD diccOrigen;
	private NodoLD diccTrad;
	
	public ListaBilingue() {
		diccOrigen = diccTrad = null;
	}

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
				
				String origen = null;
				String trad = null;
				String[] partes;
				
				linea = lectura.readLine();
	
				// recorro las lineas, creando las palabras y sus traducciones e insertando
				while (linea != null) {
					partes = linea.split("[ ]*\\*[ ]*");
					if (partes.length==2 && partes[0]!="" && partes[1]!="")
						inserta(origen,trad);
					
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

	// insertar en los diccionarios
	// recorrer cada diccionario e insertar el nuevo nodo donde toque
	// tiene un coste lineal, pues hay que recorrer ambas listas nodo a nodo (-)(n)
	public boolean inserta(String o,String d) {
		if (o!=null && d!=null) {			
			// si la lista esta vacia, insertar
			if (diccOrigen==null) {
				NodoLD nueva = new NodoLD(o,d);
				diccOrigen = nueva;
				diccTrad = nueva;
				return true;
			}

			if (buscaO(o)!=null || buscaD(d)!=null)
				return false;
			
			// no existe, insertar ordenadamente
			NodoLD iterador = diccOrigen;
			NodoLD ant = null;
			boolean exito = false;
			NodoLD nuevo = new NodoLD(o,d);
			
			// insertamos en origen
			while (iterador!=null && !exito) {
				if (o.compareToIgnoreCase(iterador.getOrigen()) < 0) {
					if (ant==null) 
						diccOrigen = nuevo;
					
					else
						ant.cambiaNextOrigen(nuevo);
					
					nuevo.cambiaNextOrigen(iterador);
					exito = true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
			
			if (!exito)
				ant.cambiaNextOrigen(nuevo);
			
			// insertamos en trad
			iterador = diccTrad; exito = false;
			ant = null;
			
			// insertamos en origen
			while (iterador!=null && !exito) {
				if (o.compareToIgnoreCase(iterador.getTrad()) < 0) {
					if (ant==null) 
						diccTrad = nuevo;
					
					else
						ant.cambiaNextTrad(nuevo);
					
					nuevo.cambiaNextTrad(iterador);
					exito = true;
				}
				ant = iterador;
				iterador = iterador.getNextTrad();
			}
			
			if (!exito)
				ant.cambiaNextTrad(nuevo);
			
			return true;
		}
		
		return false;
	}

	// borrar el origen s del diccionario
	public boolean borraO(String s) {
		if (s!=null) {
			NodoLD iterador = diccOrigen;
			NodoLD ant = null;
			
			while(iterador!=null) {
				if (iterador.getOrigen().equalsIgnoreCase(s)) {
					NodoLD aux = iterador.getNextOrigen();
					
					if (ant==null)
						diccOrigen = aux;
					
					else
						ant.cambiaNextOrigen(aux);
					
					return true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
			
			iterador = diccTrad;
			ant = null;
			
			while(iterador!=null) {
				if (iterador.getOrigen().equalsIgnoreCase(s)) {
					NodoLD aux = iterador.getNextTrad();
					
					if (ant==null)
						diccTrad = aux;
					
					else
						ant.cambiaNextTrad(aux);
					
					return true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
		}
		return false;
	}
	
	// borrar la traduccion s del diccionario
	public boolean borraD(String s) {
		if (s!=null) {
			NodoLD iterador = diccOrigen;
			NodoLD ant = null;
			
			while(iterador!=null) {
				if (iterador.getTrad().equalsIgnoreCase(s)) {
					NodoLD aux = iterador.getNextOrigen();
					
					if (ant==null)
						diccOrigen = aux;
					
					else
						ant.cambiaNextOrigen(aux);
					
					return true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
			
			iterador = diccTrad;
			ant = null;
			
			while(iterador!=null) {
				if (iterador.getTrad().equalsIgnoreCase(s)) {
					NodoLD aux = iterador.getNextTrad();
					
					if (ant==null)
						diccTrad = aux;
					
					else
						ant.cambiaNextTrad(aux);
					
					return true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
		}
		return false;
	}

	// buscar un elemento. usar mejor algoritmo
	// como se trata de una lista enlazada simple, el unico metodo de busqueda es ir uno a uno
	// la cota superior es lineal, pues hay que recorrer uno a uno los elementos
	// la cota inferior es constante si el diccionario esta vacio, o el string s es null
	public String buscaO(String s) {
		if(s!=null) {
			NodoLD iterador = diccOrigen;
			while (iterador!=null) {
				if (iterador.getOrigen().equalsIgnoreCase(s))
					return iterador.getTrad();
				
				iterador = iterador.getNextOrigen();
			}		   
		}
		return null;
	}
	
	// buscar un elemento. usar mejor algoritmo
	// como se trata de una lista enlazada simple, el unico metodo de busqueda es ir uno a uno
	// la cota superior es lineal, pues hay que recorrer uno a uno los elementos
	// la cota inferior es constante si el diccionario esta vacio, o el string s es null
	public String buscaD(String s) {
		if(s!=null) {
			NodoLD iterador = diccTrad;
			while (iterador!=null) {
				if (iterador.getTrad().equalsIgnoreCase(s))
					return iterador.getOrigen();
				
				iterador = iterador.getNextTrad();
			}		   
		}
		return null;
	}
	
	// devolver la posicion en el diccorigen
	public int indiceO(String s) {
		if(s!=null) {
			NodoLD iterador = diccOrigen; int c = 0;
			while (iterador!=null) {
				c++;
				if (iterador.getOrigen().equalsIgnoreCase(s))
					return c;
				
				iterador = iterador.getNextOrigen();
			}		   
		}
		return -1;
	}
	
	// devolver la posicion en el dicctrad. recorremos el diccionario aumentando un contador
	// tiene un coste lineal, pues hay que recorrer el diccionario nodo a nodo hasta hallar s
	public int indiceD(String s) {
		if(s!=null) {
			NodoLD iterador = diccTrad; int c = 0;
			while (iterador!=null) {
				c++;
				if (iterador.getTrad().equalsIgnoreCase(s))
					return c;
				
				iterador = iterador.getNextTrad();
			}		   
		}
		return -1;
	}
	
	// muestra todo el diccionario ordenado por origen
	public void visualizaO() {
		NodoLD iterador = diccOrigen;
		while (iterador!=null) {
			System.out.println(iterador.getOrigen() + ":" + iterador.getTrad());
			iterador = iterador.getNextOrigen();
		}
	}
	
	// muestra todo el diccionario ordenado por destino
	public void visualizaD() {
		NodoLD iterador = diccTrad;
		while (iterador!=null) {
			System.out.println(iterador.getTrad() + ":" + iterador.getOrigen());
			iterador = iterador.getNextTrad();
		}
	}
	
	// devuelve el par en la posicion i de origen
	public Vector<String> getO(int i) {
		if (i > 0) {
			NodoLD iterador = diccOrigen;
			while (i > 1 && iterador!=null) {
				i--;
				iterador = iterador.getNextOrigen();
			}
			
			if (iterador!=null) {
				Vector<String> res = new Vector<String>();
				res.add(iterador.getOrigen());
				res.add(iterador.getTrad());
				return res;
			}
		}		
		return null;
	}
	
	// devuelve el par en la posicion i de destino
	public Vector<String> getD(int i) {
		if (i > 0) {
			NodoLD iterador = diccTrad;
			while (i > 1 && iterador!=null) {
				i--;
				iterador = iterador.getNextTrad();
			}
			
			if (iterador!=null) {
				Vector<String> res = new Vector<String>();
				res.add(iterador.getOrigen());
				res.add(iterador.getTrad());
				return res;
			}
		}		
		return null;
	}
	
	// leer el diccionario permitiendo repetir palabras
	public void leeDiccionarioRepe(String f) {
		// TODO Auto-generated method stub
		if (f!=null) {
			FileReader fichero = null;
			BufferedReader lectura = null;
			try {
				// flujos
				fichero = new FileReader(f);
				lectura = new BufferedReader(fichero);
				String linea = lectura.readLine();
				
				String origen = null;
				String trad = null;
				String[] partes;
				
				linea = lectura.readLine();
	
				// recorro las lineas, creando las palabras y sus traducciones e insertando
				while (linea != null) {
					partes = linea.split("[ ]*\\*[ ]*");
					if (partes.length==2 && partes[0]!="" && partes[1]!="")
						insertaRepe(origen,trad);
					
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
	
	// insertar en los diccionarios permitiendo que se repitan
	// recorrer cada diccionario e insertar el nuevo nodo donde toque
	// tiene un coste lineal, pues hay que recorrer ambas listas nodo a nodo (-)(n)
	public boolean insertaRepe(String o,String d) {
		if (o!=null && d!=null) {			
			// si la lista esta vacia, insertar
			if (diccOrigen==null) {
				NodoLD nueva = new NodoLD(o,d);
				diccOrigen = nueva;
				diccTrad = nueva;
				return true;
			}

			NodoLD iterador = diccOrigen;
			NodoLD nuevo = new NodoLD(o,d);
			NodoLD ant = null;
			
			// comprobar si ya exite esa combinacion
			while(iterador!=null) {
				if (iterador.getOrigen().equalsIgnoreCase(o) && iterador.getTrad().equalsIgnoreCase(d))
					return false;
				iterador = iterador.getNextOrigen();
			}
			
			// no existe, insertar ordenadamente
			iterador = diccOrigen;
			ant = null;
			boolean exito = false;
			
			// insertamos en origen
			while (iterador!=null && !exito) {
				if (o.compareToIgnoreCase(iterador.getOrigen()) <= 0) {
					if (ant==null) 
						diccOrigen = nuevo;
					
					else
						ant.cambiaNextOrigen(nuevo);
					
					nuevo.cambiaNextOrigen(iterador);
					exito = true;
				}
				ant = iterador;
				iterador = iterador.getNextOrigen();
			}
			
			if (!exito)
				ant.cambiaNextOrigen(nuevo);
			
			// insertamos en trad
			iterador = diccTrad; exito = false;
			ant = null;
			
			while (iterador!=null && !exito) {
				if (o.compareToIgnoreCase(iterador.getTrad()) <= 0) {
					if (ant==null) 
						diccTrad = nuevo;
					
					else
						ant.cambiaNextTrad(nuevo);
					
					nuevo.cambiaNextTrad(iterador);
					exito = true;
				}
				ant = iterador;
				iterador = iterador.getNextTrad();
			}
			
			if (!exito)
				ant.cambiaNextTrad(nuevo);
			
			return true;
		}
		
		return false;
	}

	// muestra las palabras con mas de una acepcion
	public void consultaAcepciones(char p) {
		NodoLD iterador;
		if (p == 'O') {
			String ant = null;
			boolean exito = false;
			iterador = diccOrigen;
			
			while(iterador!=null) {				
				if (ant == iterador.getOrigen())
					System.out.print("," + iterador.getTrad());
				
				if (ant!=iterador.getOrigen() && iterador.getNextOrigen().equals(iterador.getOrigen())) {
					if (exito)
						System.out.println();
					exito = true;
					System.out.print(iterador.getOrigen()+ ":" +iterador.getTrad());
				}
					
				ant = iterador.getOrigen();
				iterador = iterador.getNextOrigen();
			}
			
			if (!exito)
				System.out.println("No existe");
		}
		
		if (p == 'D') {
			String ant = null;
			boolean exito = false;
			iterador = diccTrad;
			
			while(iterador!=null) {				
				if (ant == iterador.getTrad())
					System.out.print("," + iterador.getOrigen());
				
				if (ant!=iterador.getTrad() && iterador.getNextTrad().equals(iterador.getTrad())) {
					if (exito)
						System.out.println();
					exito = true;
					System.out.print(iterador.getTrad()+ ":" +iterador.getOrigen());
				}
					
				ant = iterador.getTrad();
				iterador = iterador.getNextTrad();
			}
			
			if (!exito)
				System.out.println("No existe");	
		}
	}
}