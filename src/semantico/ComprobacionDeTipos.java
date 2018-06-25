package semantico;

import java.util.List;

import ast.AccesoArray;
import ast.AccesoCampo;
import ast.Asignacion;
import ast.Cast;
import ast.Cuerpo;
import ast.DefCampo;
import ast.DefEstructura;
import ast.DefFuncion;
import ast.DefReturn;
import ast.DefVariable;
import ast.Expresion;
import ast.ExpresionAritmetica;
import ast.ExpresionComparacion;
import ast.IfElse;
import ast.InvocacionFuncion;
import ast.InvocacionProcedimiento;
import ast.LiteralChar;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.MenosUnario;
import ast.Negacion;
import ast.Position;
import ast.Print;
import ast.PrintLN;
import ast.PrintSP;
import ast.Read;
import ast.Return;
import ast.Tipo;
import ast.TipoArray;
import ast.TipoChar;
import ast.TipoEntero;
import ast.TipoIdent;
import ast.TipoReal;
import ast.Variable;
import ast.While;
import main.GestorErrores;
import visitor.DefaultVisitor;

/**
 * @author José Antonio García García
 */
public class ComprobacionDeTipos extends DefaultVisitor {

	private GestorErrores gestorErrores;

	public ComprobacionDeTipos(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	@Override
	public Object visit(Asignacion node, Object param) {
		super.visit(node, param);
		predicado(mismoTipo(node.getLeft(), node.getRight()),
				"[ERROR] Asignacion: No coinciden los tipos de los operandos", node.getStart());

		predicado(node.getLeft().isModificable(),
				"[ERROR] Asignacion: No se puede modificar la expresion de la izquierda", node.getLeft().getStart());

		// Revisa
		predicado(
				node.getLeft().getTipo().getClass() != TipoIdent.class
						&& node.getLeft().getTipo().getClass() != TipoArray.class,
				"[ERROR] Asignacion: El valor de la izquierda debe ser simple", node.getLeft().getStart());

		return null;
	}

	@Override
	public Object visit(ExpresionAritmetica node, Object param) {
		super.visit(node, param);
		predicado(mismoTipo(node.getLeft(), node.getRight()),
				"[ERROR] Expresion aritmética: No coinciden los tipos de los operandos", node.getStart());

		node.setTipo(node.getLeft().getTipo());
		node.setModificable(false);
		return null;
	}

	@Override
	public Object visit(ExpresionComparacion node, Object param) {
		super.visit(node, param);
		predicado(mismoTipo(node.getLeft(), node.getRight()),
				"[ERROR] Expresion comparativa: No coinciden los tipos de los operandos", node.getStart());

		// Revisar
		if (node.getOperador() == "&&")
			predicado(node.getLeft().getTipo().getClass() == TipoEntero.class,
					"[ERROR] Expresion comparativa: El operador AND solo es aplicable a enteros", node.getStart());

		// Revisar
		if (node.getOperador() == "||")
			predicado(node.getLeft().getTipo().getClass() == TipoEntero.class,
					"[ERROR] Expresion comparativa: El operador OR solo es aplicable a enteros", node.getStart());

		node.setTipo(new TipoEntero());
		node.setModificable(false);

		return null;
	}

	@Override
	public Object visit(Negacion node, Object param) {
		super.visit(node, param);

		// Revisar
		predicado(node.getExpresion().getTipo().getClass() == TipoEntero.class,
				"[ERROR] Negacion: El operador '!' solo es aplicable a enteros", node.getStart());

		node.setTipo(new TipoEntero());
		node.setModificable(false);
		return null;
	}

	@Override
	public Object visit(Variable node, Object param) {
		super.visit(node, param);

		node.setTipo(node.getDefinicion().getTipo());
		node.setModificable(true);

		// Revisar
		if (param != null && node.getTipo().getClass() == TipoIdent.class) {
			TipoIdent struct = (TipoIdent) node.getTipo();
			DefEstructura defStruct = struct.getDefinicion();

			boolean aparece = false;
			for (DefCampo dC : defStruct.getCampos()) {
				if (dC.getNombre().equals(param)) { // Comprueba si existe el campo en la estructura
					aparece = true;
					Tipo tipo = dC.getTipo();
					// Revisar
					if (dC.getTipo().getClass() == TipoArray.class) { // Si es un campo de tipo array
						node.setTipo(((TipoArray) tipo).getTipo()); // Asigna el tipo del tipo del array
					} else
						node.setTipo(dC.getTipo()); // Si es un tipo simple lo asigna sin mas
					break;
				}
			}
			predicado(aparece, "[ERROR] Estructura: No existe el campo en esta estructura " + defStruct.getNombre(),
					node.getStart());
		}
		return null;
	}

	@Override
	public Object visit(LiteralEntero node, Object param) {
		node.setTipo(new TipoEntero());
		node.setModificable(false);
		return null;
	}

	@Override
	public Object visit(LiteralReal node, Object param) {
		node.setTipo(new TipoReal());
		node.setModificable(false);
		return null;
	}

	@Override
	public Object visit(LiteralChar node, Object param) {
		// Revisar
		node.setTipo(new TipoChar());
		node.setModificable(false);
		return null;
	}

	@Override
	public Object visit(TipoArray node, Object param) {
		return node.getTipo();
	}

	@Override
	public Object visit(AccesoArray node, Object param) {
		super.visit(node, param);

		// Revisar
		predicado(node.getPosicion().getTipo().getClass() == TipoEntero.class,
				"[ERROR] Acceso Array: Solo se accede al array mediante un entero", node.getStart());

		// Revisar
		if (node.getPosicion().getTipo().getClass() == TipoEntero.class) {
			// Revisar
			predicado(node.getIdentificador().getTipo().getClass() == TipoArray.class,
					"[ERROR] Acceso Array: Solo se puede acceder al interior de variables si son arrays",
					node.getStart());
			// Revisar
			if (node.getIdentificador().getTipo().getClass() == TipoArray.class) {
				node.setTipo(((TipoArray) node.getIdentificador().getTipo()).getTipo());
				node.setModificable(true);
			}
		}

		return null;
	}

	@Override
	public Object visit(DefFuncion node, Object param) {
		super.visit(node, param);
		return null;
	}

	@Override
	public Object visit(DefReturn node, Object param) {
		if (node.getTipo() != null) {
			// Revisar
			predicado(node.getTipo().getClass() != TipoIdent.class && node.getTipo().getClass() != TipoArray.class,
					"[ERROR] Return: Solo se pueden retornar tipos simples", node.getStart());

		}
		return null;
	}

	@Override
	public Object visit(Cuerpo node, Object param) {
		// Revisar
		super.visit(node, param);
		return null;
	}

	@Override
	public Object visit(Return node, Object param) {
		super.visit(node, param);
		Tipo t = node.getFuncion().getRetorno().getTipo();

		if (t != null && node.getDevolucion() != null)
			// Revisar
			predicado(node.getDevolucion().getTipo().getClass() == t.getClass(),
					"[ERROR] Return: No coincide el tipo del return del cuerpo y el de la declaracion de la funcion",
					node.getStart());

		else if (t == null)
			predicado(node.getDevolucion() == null, "[ERROR] Return: La funcion no acepta valores de retorno",
					node.getStart());

		else if (node.getDevolucion() == null)
			predicado(t == null, "[ERROR] Return: La funcion necesita un valor de retorno", node.getStart());

		return null;
	}

	@Override
	public Object visit(Cast node, Object param) {
		super.visit(node, param);
		// Revisar
		predicado(node.getTipo().getClass() != TipoIdent.class && node.getExpresion().getClass() != TipoIdent.class,
				"[ERROR] Cast: Solo se puede hacer cast de tipos simples", node.getStart());
		// Revisar
		predicado(node.getExpresion().getTipo().getClass() != TipoIdent.class,
				"[ERROR] Cast: Solo se puede hacer cast a expresiones de tipos simples", node.getStart());
		// Revisar
		predicado(node.getTipo().getClass() != node.getExpresion().getTipo().getClass(),
				"[ERROR] Cast: No se puede hacer cast de un tipo al mismo tipo", node.getStart());

		return null;
	}

	@Override
	public Object visit(AccesoCampo node, Object param) {
		super.visit(node, node.getCampo());
		// Revisar
		predicado(node.getExpresion().getTipo().getClass() == TipoIdent.class,
				"[ERROR] Acceso Campo: Solo se puede acceder a campos de expresiones de tipo struct", node.getStart());

		// Revisar
		if (node.getExpresion().getTipo().getClass() == TipoIdent.class) {
			DefEstructura defStruct = ((TipoIdent) node.getExpresion().getTipo()).getDefinicion();
			for (DefCampo dC : defStruct.getCampos()) {
				if (dC.getNombre().equals(node.getCampo())) {
					node.setTipo(dC.getTipo());
					break;
				}
			}

		} else {
			node.setTipo(node.getExpresion().getTipo());
		}
		node.setModificable(true);
		return null;
	}

	@Override
	public Object visit(DefVariable node, Object param) {
		if (node.getAmbito().equals("Parametro")) {
			super.visit(node, param);
			// Revisar
			predicado(node.getTipo().getClass() != TipoArray.class,
					"[ERROR] Funcion: Solo se aceptan parametros de tipo simple", node.getStart());
		}
		return null;
	}

	@Override
	public Object visit(While node, Object param) {
		super.visit(node, param);
		// Revisar
		predicado(node.getCondicion().getTipo().getClass() == TipoEntero.class,
				"[ERROR] While: Solo se aceptan condiciones de tipo entero", node.getStart());

		return null;
	}

	@Override
	public Object visit(IfElse node, Object param) {
		super.visit(node, param);
		// Revisar
		predicado(node.getCondicion().getTipo().getClass() == TipoEntero.class,
				"[ERROR] If Else: Solo se aceptan condiciones de tipo entero", node.getStart());

		return null;
	}

	@Override
	public Object visit(Read node, Object param) {
		super.visit(node, param);
		// Revisar
		predicado(node.getEntrada().getTipo().getClass() != TipoIdent.class,
				"[ERROR] Read: Solo se aceptan expresiones de tipo simple", node.getStart());
		predicado(node.getEntrada().isModificable(), "[ERROR] Read: Solo se aceptan expresiones modificables",
				node.getStart());

		return null;
	}

	@Override
	public Object visit(Print node, Object param) {
		super.visit(node, param);

		predicado(node.getSalida().getTipo() != null, "[ERROR] Print: Es necesario introducir algun valor",
				node.getStart());
		if (node.getSalida().getTipo() != null)
			// Revisar
			predicado(node.getSalida().getTipo().getClass() != TipoIdent.class,
					"[ERROR] Print: Solo se aceptan expresiones de tipo simple", node.getStart());

		return null;
	}

	@Override
	public Object visit(PrintLN node, Object param) {
		super.visit(node, param);

		if (node.getSalida() != null)
			// Revisar
			predicado(node.getSalida().getTipo().getClass() != TipoIdent.class,
					"[ERROR] Println: Solo se aceptan expresiones de tipo simple", node.getStart());

		return null;
	}

	@Override
	public Object visit(PrintSP node, Object param) {
		super.visit(node, param);

		predicado(node.getSalida().getTipo() != null, "[ERROR] Printsp: Es necesario introducir algun valor",
				node.getStart());
		if (node.getSalida().getTipo() != null)
			// Revisar
			predicado(node.getSalida().getTipo().getClass() != TipoIdent.class,
					"[ERROR] Printsp: Solo se aceptan expresiones de tipo simple", node.getStart());

		return null;
	}

	@Override
	public Object visit(InvocacionFuncion node, Object param) {
		super.visit(node, param);

		node.setTipo(node.getDefFuncion().getRetorno().getTipo());
		node.setModificable(false);

		List<DefVariable> parametros = node.getDefFuncion().getParametros();
		boolean coinciden = parametros.size() == node.getArgumentos().size();
		if (!coinciden) {
			predicado(coinciden,
					"[ERROR] Invocacion Funcion: El numero de argumentos no coincide con el numero de parametros que necesita la funcion",
					node.getStart());
		} else {
			boolean coincidenTipos = true;
			for (int i = 0; i < parametros.size(); i++) {
				// Revisar
				if (parametros.get(i).getTipo().getClass() != node.getArgumentos().get(i).getTipo().getClass())
					coincidenTipos = false;
			}
			predicado(coincidenTipos,
					"[ERROR] Invocacion Funcion: No coinciden los tipos de los argumentos con los tipos de los parametros de la funcion",
					node.getStart());
		}
		return null;
	}

	@Override
	public Object visit(InvocacionProcedimiento node, Object param) {
		super.visit(node, param);

		List<DefVariable> parametros = node.getDefFuncion().getParametros();
		boolean coinciden = parametros.size() == node.getArgumentos().size();
		if (!coinciden) {
			predicado(coinciden,
					"[ERROR] Invocacion Procedimiento: El numero de argumentos no coincide con el numero de parametros que necesita la funcion",
					node.getStart());
		} else {
			boolean coincidenTipos = true;
			for (int i = 0; i < parametros.size(); i++) {
				// Revisar
				if (parametros.get(i).getTipo().getClass() != node.getArgumentos().get(i).getTipo().getClass())
					coincidenTipos = false;
			}
			predicado(coincidenTipos,
					"[ERROR] Invocacion Procedimiento: No coinciden los tipos de los argumentos con los tipos de los parametros de la funcion",
					node.getStart());
		}
		return null;
	}

	@Override
	public Object visit(MenosUnario node, Object param) {
		super.visit(node, param);
		node.setTipo(node.getExpresion().getTipo());
		node.setModificable(node.getExpresion().isModificable());
		return null;
	}

	private boolean mismoTipo(Expresion a, Expresion b) {
		return a.getTipo().getClass() == b.getTipo().getClass();
	}

	private boolean esTipoIdent(Expresion e) {
		return e.getTipo().getClass() == TipoIdent.class;
	}

	private boolean esTipoArray(Expresion e) {
		return e.getTipo().getClass() == TipoArray.class;
	}

	private boolean esTipoEntero(Expresion e) {
		return e.getTipo().getClass() == TipoEntero.class;
	}

	private boolean esTipoSimple(Expresion e) {
		return e.getTipo().getClass() != TipoIdent.class && e.getTipo().getClass() != TipoArray.class;
	}

	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso (suponiendo implementado el método "esPrimitivo"):
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", expr.getStart());
	 * 	predicado(esPrimitivo(expr.tipo), "La expresión debe ser de un tipo primitivo", null);
	 * 
	 * NOTA: El método getStart() indica la linea/columna del fichero fuente de donde se leyó el nodo.
	 * Si se usa VGen dicho método será generado en todos los nodos AST. Si no se quiere usar getStart() se puede pasar null.
	 * 
	 * @param condicion Debe cumplirse para que no se produzca un error
	 * @param mensajeError Se imprime si no se cumple la condición
	 * @param posicionError Fila y columna del fichero donde se ha producido el error. Es opcional (acepta null)
	 */
	private void predicado(boolean condicion, String mensajeError, Position posicionError) {
		if (!condicion)
			gestorErrores.error("Comprobación de tipos", mensajeError, posicionError);
	}

}
