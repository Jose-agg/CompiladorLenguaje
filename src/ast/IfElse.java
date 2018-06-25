/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	ifElse:sentencia -> condicion:expresion  correcto:sentencia*  incorrecto:sentencia*

public class IfElse extends AbstractSentencia {

	public IfElse(Expresion condicion, List<Sentencia> correcto, List<Sentencia> incorrecto) {
		this.condicion = condicion;
		this.correcto = correcto;
		this.incorrecto = incorrecto;

		searchForPositions(condicion, correcto, incorrecto);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public IfElse(Object condicion, Object correcto, Object incorrecto) {
		this.condicion = (Expresion) condicion;
		this.correcto = (List<Sentencia>) correcto;
		this.incorrecto = (List<Sentencia>) incorrecto;

		searchForPositions(condicion, correcto, incorrecto);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getCondicion() {
		return condicion;
	}
	public void setCondicion(Expresion condicion) {
		this.condicion = condicion;
	}

	public List<Sentencia> getCorrecto() {
		return correcto;
	}
	public void setCorrecto(List<Sentencia> correcto) {
		this.correcto = correcto;
	}

	public List<Sentencia> getIncorrecto() {
		return incorrecto;
	}
	public void setIncorrecto(List<Sentencia> incorrecto) {
		this.incorrecto = incorrecto;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> correcto;
	private List<Sentencia> incorrecto;
}

