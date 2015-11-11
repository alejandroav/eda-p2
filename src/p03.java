/**
* @author Alicia Garrido Alenda
*
* Se crea un objeto de tipo DiccVector, se invoca su metodo leeDiccionario
* con un diccionario ordenado sin palabras repetidas pero alguna palabra
* no tiene traducciones para cada idioma. Se invoca su metodo visualiza
* sin parametros.
*/
public class p03 {
  public static void main(String[] args){
	args = new String[1];
	args[0] = "p03.dic";
    Diccionario diccio=new DiccVector();
    if(args.length>=1){
      diccio.leeDiccionario(args[0]);
      diccio.visualiza();
    }
    else
     System.out.println("Forma uso: java p03 p03.dic");
    
  }
}
