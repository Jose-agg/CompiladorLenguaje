/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	expresionAritmetica:expresion -> left:expresion  operador:String  right:expresion

public class ExpresionAritmetica extends AbstractExpresion {

	public ExpresionAritmetica(Expresion left, String operador, Expresion right) {
		this.left = left;
		this.operador = operador;
		this.right = right;

		searchForPositions(left, right);	// Obtener linea/columna a partir de los hijos
	}

	public ExpresionAritmetica(Object left, Object operador, Object right) {
		this.left = (Expresion) left;
		this.operador = (operador instanceof Token) ? ((Token)operador).getLexeme() : (String) operador;
		this.right = (Expresion) right;

		searchForPositions(left, operador, right);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getLeft() {
		return left;
	}
	public void setLeft(Expresion left) {
		this.left = left;
	}

	public String getOperador() {
		return operador;
	}
	public void setOperador(String operador) {
		this.operador = operador;
	}

	public Expresion getRight() {
		return right;
	}
	public void setRight(Expresion right) {
		this.right = right;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion left;
	private String operador;
	private Expresion right;
}

