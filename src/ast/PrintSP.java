/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	printSP:sentencia -> salida:expresion

public class PrintSP extends AbstractSentencia {

	public PrintSP(Expresion salida) {
		this.salida = salida;

		searchForPositions(salida);	// Obtener linea/columna a partir de los hijos
	}

	public PrintSP(Object salida) {
		this.salida = (Expresion) salida;

		searchForPositions(salida);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getSalida() {
		return salida;
	}
	public void setSalida(Expresion salida) {
		this.salida = salida;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion salida;
}

