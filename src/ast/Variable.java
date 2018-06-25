/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// variable:expresion -> nombre:String

public class Variable extends AbstractExpresion {

	private String nombre;

	public Variable(String nombre) {
		this.nombre = nombre;
	}

	public Variable(Object nombre) {
		this.nombre = (nombre instanceof Token) ? ((Token) nombre).getLexeme() : (String) nombre;

		searchForPositions(nombre); // Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	private DefVariable definicion;

	public void setDefinicion(DefVariable definicion) {
		this.definicion = definicion;
	}

	public DefVariable getDefinicion() {
		return definicion;
	}

	@Override
	public Tipo getTipo() {
		return definicion.getTipo();
	}
}
