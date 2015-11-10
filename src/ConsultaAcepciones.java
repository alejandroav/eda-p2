// DNI 45928098 ALARCON VILLENA, ALEJANDRO
public class ConsultaAcepciones {
	public static void main(String args[]) {
		// comprobamos que los parametros son correctos
		if (args.length == 2) {
			ListaBilingue lista = new ListaBilingue();
			lista.leeDiccionarioRepe(args[0]);
			char c = args[2].charAt(0);
			lista.consultaAcepciones(c);
		}
	}
}