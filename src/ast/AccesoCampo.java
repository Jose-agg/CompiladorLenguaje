/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	accesoCampo:expresion -> expresion:expresion  campo:String

public class AccesoCampo extends AbstractExpresion {

	public AccesoCampo(Expresion expresion, String campo) {
		this.expresion = expresion;
		this.campo = campo;

		searchForPositions(expresion);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoCampo(Object expresion, Object campo) {
		this.expresion = (Expresion) expresion;
		this.campo = (campo instanceof Token) ? ((Token)campo).getLexeme() : (String) campo;

		searchForPositions(expresion, campo);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getExpresion() {
		return expresion;
	}
	public void setExpresion(Expresion expresion) {
		this.expresion = expresion;
	}

	public String getCampo() {
		return campo;
	}
	public void setCampo(String campo) {
		this.campo = campo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion expresion;
	private String campo;
}

