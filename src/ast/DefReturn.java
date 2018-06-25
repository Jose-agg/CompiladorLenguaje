/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	defReturn -> tipo:tipo

public class DefReturn extends AbstractTraceable implements AST {

	public DefReturn(Tipo tipo) {
		this.tipo = tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public DefReturn(Object tipo) {
		this.tipo = (Tipo) tipo;

		searchForPositions(tipo);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
}

