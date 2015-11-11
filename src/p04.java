/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccLisJava, se invoca su metodo leeDiccionario
* con un diccionario desordenado sin palabras repetidas pero alguna palabra
* no tiene traducciones para cada idioma. Se invoca su metodo inserta con
* objetos de tipo Palabra2 cuya cadena en la lengua origen ya esta en el 
* diccionario, unas veces con traducciones nuevas, otras no y otras con 
* idiomas distintos. Se invoca su 
* metodo visualiza indicando mas lineas que palabras tiene el diccionario.
*/
import java.util.*;
public class p04 {
  /**
  * Crea una lista de objetos de tipo Palabra2, sin leerlos de fichero.
  * @return La lista creada, con un objeto en cada una de sus posiciones.
  */
  public static ArrayList<Palabra2> creaLista(){
	
    ArrayList<Palabra2> lista=new ArrayList<Palabra2>();
    Palabra2 tmp=null;
    char[] idiomas={'F','E','P'};
    tmp=new Palabra2("lay",idiomas);
    tmp.setTrad("dejar",'E');
    lista.add(tmp);
    tmp=new Palabra2("normal",idiomas);
    tmp.setTrad("normal",'F');
    lista.add(tmp);
    tmp=new Palabra2("please",idiomas);
    tmp.setTrad("por favor",'P');
    lista.add(tmp);
    tmp=new Palabra2("toy",idiomas);
    tmp.setTrad("jouet",'F');
    lista.add(tmp);
    tmp=new Palabra2("wrong",idiomas);
    tmp.setTrad("errado",'P');
    tmp.setTrad("equivocado",'E');
    tmp.setTrad("faux",'F');
    lista.add(tmp);
    char[] idiomas2={'E','P','F'};
    tmp=new Palabra2("wrong",idiomas2);
    tmp.setTrad("desacertado",'E');
    lista.add(tmp);
    return lista;
  }
              
  public static void main(String[] args){
	  args = new String[1];
	  args[0] = "p04.dic";
    Diccionario diccio=new DiccLisJava();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      diccio.visualiza(15);
      ArrayList<Palabra2> palabras=creaLista();
      Palabra2 actual=null;
      for(int i=0;i<palabras.size();i++){
       actual=palabras.get(i);
       System.out.println("Se inserta "+actual.getOrigen()+"? -> "+diccio.inserta(actual));
      }
      System.out.println("========== DICCIONARIO DESPUES DE INSERTAR ===============");
      diccio.visualiza(15);
    }
    else
     System.out.println("Forma uso: java p04 p04.dic");
    
  }
}
