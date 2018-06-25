/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	cast:expresion -> nuevoTipo:tipo  expresion:expresion

public class Cast extends AbstractExpresion {

	public Cast(Tipo nuevoTipo, Expresion expresion) {
		this.nuevoTipo = nuevoTipo;
		this.expresion = expresion;

		searchForPositions(nuevoTipo, expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Cast(Object nuevoTipo, Object expresion) {
		this.nuevoTipo = (Tipo) nuevoTipo;
		this.expresion = (Expresion) expresion;

		searchForPositions(nuevoTipo, expresion);	// Obtener linea/columna a partir de los hijos
	}

	public Tipo getNuevoTipo() {
		return nuevoTipo;
	}
	public void setNuevoTipo(Tipo nuevoTipo) {
		this.nuevoTipo = nuevoTipo;
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

	private Tipo nuevoTipo;
	private Expresion expresion;
}

