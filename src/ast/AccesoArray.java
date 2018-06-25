/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	accesoArray:expresion -> identificador:expresion  posicion:expresion

public class AccesoArray extends AbstractExpresion {

	public AccesoArray(Expresion identificador, Expresion posicion) {
		this.identificador = identificador;
		this.posicion = posicion;

		searchForPositions(identificador, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public AccesoArray(Object identificador, Object posicion) {
		this.identificador = (Expresion) identificador;
		this.posicion = (Expresion) posicion;

		searchForPositions(identificador, posicion);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getIdentificador() {
		return identificador;
	}
	public void setIdentificador(Expresion identificador) {
		this.identificador = identificador;
	}

	public Expresion getPosicion() {
		return posicion;
	}
	public void setPosicion(Expresion posicion) {
		this.posicion = posicion;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion identificador;
	private Expresion posicion;
}

