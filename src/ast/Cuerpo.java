/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	cuerpo -> definicionesVariables:defVariable*  sentencias:sentencia*

public class Cuerpo extends AbstractTraceable implements AST {

	public Cuerpo(List<DefVariable> definicionesVariables, List<Sentencia> sentencias) {
		this.definicionesVariables = definicionesVariables;
		this.sentencias = sentencias;

		searchForPositions(definicionesVariables, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public Cuerpo(Object definicionesVariables, Object sentencias) {
		this.definicionesVariables = (List<DefVariable>) definicionesVariables;
		this.sentencias = (List<Sentencia>) sentencias;

		searchForPositions(definicionesVariables, sentencias);	// Obtener linea/columna a partir de los hijos
	}

	public List<DefVariable> getDefinicionesVariables() {
		return definicionesVariables;
	}
	public void setDefinicionesVariables(List<DefVariable> definicionesVariables) {
		this.definicionesVariables = definicionesVariables;
	}

	public List<Sentencia> getSentencias() {
		return sentencias;
	}
	public void setSentencias(List<Sentencia> sentencias) {
		this.sentencias = sentencias;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private List<DefVariable> definicionesVariables;
	private List<Sentencia> sentencias;
}

