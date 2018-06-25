/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// defVariable:definicion -> nombre:String tipo:tipo ambito:String

public class DefVariable extends AbstractDefinicion {

	private String nombre;
	private Tipo tipo;
	private String ambito;

	public DefVariable(String nombre, Tipo tipo, String ambito) {
		this.nombre = nombre;
		this.tipo = tipo;
		this.ambito = ambito;

		searchForPositions(tipo); // Obtener linea/columna a partir de los hijos
	}

	public DefVariable(Object nombre, Object tipo, Object ambito) {
		this.nombre = (nombre instanceof Token) ? ((Token) nombre).getLexeme() : (String) nombre;
		this.tipo = (Tipo) tipo;
		this.ambito = (ambito instanceof Token) ? ((Token) ambito).getLexeme() : (String) ambito;

		searchForPositions(nombre, tipo, ambito); // Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public String getAmbito() {
		return ambito;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	private int direccion;

	public int getDireccion() {
		return direccion;
	}

	public void setDireccion(int direccion) {
		this.direccion = direccion;
	}
}
