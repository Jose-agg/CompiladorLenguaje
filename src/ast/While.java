/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	while:sentencia -> condicion:expresion  correcto:sentencia*

public class While extends AbstractSentencia {

	public While(Expresion condicion, List<Sentencia> correcto) {
		this.condicion = condicion;
		this.correcto = correcto;

		searchForPositions(condicion, correcto);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public While(Object condicion, Object correcto) {
		this.condicion = (Expresion) condicion;
		this.correcto = (List<Sentencia>) correcto;

		searchForPositions(condicion, correcto);	// Obtener linea/columna a partir de los hijos
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

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion condicion;
	private List<Sentencia> correcto;
}

