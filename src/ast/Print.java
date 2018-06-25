/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	print:sentencia -> salida:expresion

public class Print extends AbstractSentencia {

	public Print(Expresion salida) {
		this.salida = salida;

		searchForPositions(salida);	// Obtener linea/columna a partir de los hijos
	}

	public Print(Object salida) {
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

