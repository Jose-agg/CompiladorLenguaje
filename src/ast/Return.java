/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// return:sentencia -> devolucion:expresion

public class Return extends AbstractSentencia {

	private Expresion devolucion;

	public Return(Expresion devolucion) {
		this.devolucion = devolucion;

		searchForPositions(devolucion); // Obtener linea/columna a partir de los hijos
	}

	public Return(Object devolucion) {
		this.devolucion = (Expresion) devolucion;

		searchForPositions(devolucion); // Obtener linea/columna a partir de los hijos
	}

	public Expresion getDevolucion() {
		return devolucion;
	}

	public void setDevolucion(Expresion devolucion) {
		this.devolucion = devolucion;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	private DefFuncion funcion;

	public DefFuncion getFuncion() {
		return funcion;
	}

	public void setFuncion(DefFuncion funcion) {
		this.funcion = funcion;
	}
}
