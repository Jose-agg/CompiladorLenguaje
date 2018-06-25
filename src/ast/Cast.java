/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	cast:expresion -> tipo:tipo  expresion:expresion

public class Cast extends AbstractExpresion {

	public Cast(Tipo tipo, Expresion expresion) {
		this.tipo = tipo;
		this.expresion = expresion;

		searchForPositions(tipo, expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Cast(Object tipo, Object expresion) {
		this.tipo = (Tipo) tipo;
		this.expresion = (Expresion) expresion;

		searchForPositions(tipo, expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Tipo tipo;
	private Expresion expresion;
}

