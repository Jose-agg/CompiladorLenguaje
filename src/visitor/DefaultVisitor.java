/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;
import java.util.*;

/*
DefaultVisitor. Implementación base del visitor para ser derivada por nuevos visitor.
	No modificar esta clase. Para crear nuevos visitor usar el fichero "_PlantillaParaVisitors.txt".
	DefaultVisitor ofrece una implementación por defecto de cada nodo que se limita a visitar los nodos hijos.
*/
public class DefaultVisitor implements Visitor {

	//	class Programa { List<Definicion> definiciones; }
	public Object visit(Programa node, Object param) {
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	//	class DefVariable { String nombre;  Tipo tipo;  String ambito; }
	public Object visit(DefVariable node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefFuncion { String nombre;  List<DefVariable> parametros;  DefReturn retorno;  Cuerpo cuerpo; }
	public Object visit(DefFuncion node, Object param) {
		visitChildren(node.getParametros(), param);
		if (node.getRetorno() != null)
			node.getRetorno().accept(this, param);
		if (node.getCuerpo() != null)
			node.getCuerpo().accept(this, param);
		return null;
	}

	//	class DefEstructura { String nombre;  List<DefCampo> campos; }
	public Object visit(DefEstructura node, Object param) {
		visitChildren(node.getCampos(), param);
		return null;
	}

	//	class DefCampo { String nombre;  Tipo tipo; }
	public Object visit(DefCampo node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class DefReturn { Tipo tipo; }
	public Object visit(DefReturn node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Cuerpo { List<DefVariable> definicionesVariables;  List<Sentencia> sentencias; }
	public Object visit(Cuerpo node, Object param) {
		visitChildren(node.getDefinicionesVariables(), param);
		visitChildren(node.getSentencias(), param);
		return null;
	}

	//	class TipoEntero {  }
	public Object visit(TipoEntero node, Object param) {
		return null;
	}

	//	class TipoReal {  }
	public Object visit(TipoReal node, Object param) {
		return null;
	}

	//	class TipoChar {  }
	public Object visit(TipoChar node, Object param) {
		return null;
	}

	//	class TipoIdent { String nombre; }
	public Object visit(TipoIdent node, Object param) {
		return null;
	}

	//	class TipoArray { LiteralEntero dimension;  Tipo tipo; }
	public Object visit(TipoArray node, Object param) {
		if (node.getDimension() != null)
			node.getDimension().accept(this, param);
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		return null;
	}

	//	class Asignacion { Expresion left;  Expresion right; }
	public Object visit(Asignacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Print { Expresion salida; }
	public Object visit(Print node, Object param) {
		if (node.getSalida() != null)
			node.getSalida().accept(this, param);
		return null;
	}

	//	class PrintLN { Expresion salida; }
	public Object visit(PrintLN node, Object param) {
		if (node.getSalida() != null)
			node.getSalida().accept(this, param);
		return null;
	}

	//	class PrintSP { Expresion salida; }
	public Object visit(PrintSP node, Object param) {
		if (node.getSalida() != null)
			node.getSalida().accept(this, param);
		return null;
	}

	//	class Read { Expresion entrada; }
	public Object visit(Read node, Object param) {
		if (node.getEntrada() != null)
			node.getEntrada().accept(this, param);
		return null;
	}

	//	class IfElse { Expresion condicion;  List<Sentencia> correcto;  List<Sentencia> incorrecto; }
	public Object visit(IfElse node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCorrecto(), param);
		visitChildren(node.getIncorrecto(), param);
		return null;
	}

	//	class While { Expresion condicion;  List<Sentencia> correcto; }
	public Object visit(While node, Object param) {
		if (node.getCondicion() != null)
			node.getCondicion().accept(this, param);
		visitChildren(node.getCorrecto(), param);
		return null;
	}

	//	class Return { Expresion devolucion; }
	public Object visit(Return node, Object param) {
		if (node.getDevolucion() != null)
			node.getDevolucion().accept(this, param);
		return null;
	}

	//	class InvocacionProcedimiento { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionProcedimiento node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class LiteralEntero { String valor; }
	public Object visit(LiteralEntero node, Object param) {
		return null;
	}

	//	class LiteralReal { String valor; }
	public Object visit(LiteralReal node, Object param) {
		return null;
	}

	//	class LiteralChar { String valor; }
	public Object visit(LiteralChar node, Object param) {
		return null;
	}

	//	class Variable { String nombre; }
	public Object visit(Variable node, Object param) {
		return null;
	}

	//	class ExpresionAritmetica { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionAritmetica node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class ExpresionComparacion { Expresion left;  String operador;  Expresion right; }
	public Object visit(ExpresionComparacion node, Object param) {
		if (node.getLeft() != null)
			node.getLeft().accept(this, param);
		if (node.getRight() != null)
			node.getRight().accept(this, param);
		return null;
	}

	//	class Negacion { Expresion expresion; }
	public Object visit(Negacion node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class InvocacionFuncion { String nombre;  List<Expresion> argumentos; }
	public Object visit(InvocacionFuncion node, Object param) {
		visitChildren(node.getArgumentos(), param);
		return null;
	}

	//	class Cast { Tipo tipo;  Expresion expresion; }
	public Object visit(Cast node, Object param) {
		if (node.getTipo() != null)
			node.getTipo().accept(this, param);
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class AccesoArray { Expresion identificador;  Expresion posicion; }
	public Object visit(AccesoArray node, Object param) {
		if (node.getIdentificador() != null)
			node.getIdentificador().accept(this, param);
		if (node.getPosicion() != null)
			node.getPosicion().accept(this, param);
		return null;
	}

	//	class AccesoCampo { Expresion expresion;  String campo; }
	public Object visit(AccesoCampo node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}

	//	class MenosUnario { Expresion expresion; }
	public Object visit(MenosUnario node, Object param) {
		if (node.getExpresion() != null)
			node.getExpresion().accept(this, param);
		return null;
	}
	
	// Método auxiliar -----------------------------
	protected void visitChildren(List<? extends AST> children, Object param) {
		if (children != null)
			for (AST child : children)
				child.accept(this, param);
	}
}
