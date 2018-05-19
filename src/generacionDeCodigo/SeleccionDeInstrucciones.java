package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;

import visitor.DefaultVisitor;

public class SeleccionDeInstrucciones extends DefaultVisitor {

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {
		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;
	}

	/*
	 * Poner aquí los visit necesarios. Si se ha usado VGen solo hay que copiarlos
	 * de la clase 'visitor/_PlantillaParaVisitors.txt'.
	 */

	//	Ejemplo:
	//
	//	public Object visit(Programa node, Object param) {
	//		genera("#source \"" + sourceFile + "\"");
	//		genera("call main");
	//		genera("halt");
	//		super.visit(node, param);	// Recorrer los hijos
	//		return null;
	//	}

	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		writer.println(instruccion);
	}

	private PrintWriter writer;
	private String sourceFile;
}
