/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	asignacionMultiple:sentencia -> left:expresion  right:expresion*

public class AsignacionMultiple extends AbstractSentencia {

	public AsignacionMultiple(Expresion left, List<Expresion> right) {
		this.left = left;
		this.right = right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public AsignacionMultiple(Object left, Object right) {
		this.left = (Expresion) left;
		this.right = (List<Expresion>) right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public List<Expresion> getRight() {
		return right;
	}
	public void setRight(List<Expresion> right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;
	private List<Expresion> right;
}

