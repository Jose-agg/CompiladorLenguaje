package semantico;

import java.util.HashMap;
import java.util.Map;

import ast.DefCampo;
import ast.DefEstructura;
import ast.DefFuncion;
import ast.DefVariable;
import ast.InvocacionFuncion;
import ast.InvocacionProcedimiento;
import ast.Position;
import ast.Return;
import ast.TipoIdent;
import ast.Variable;
import main.GestorErrores;
import visitor.DefaultVisitor;

/**
 * @author José Antonio García García
 */
public class Identificacion extends DefaultVisitor {

	private GestorErrores gestorErrores;

	private TablaSimbolos<String, DefVariable> variables;
	private Map<String, DefFuncion> funciones;
	private Map<String, DefEstructura> estructuras;

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
		this.variables = new TablaSimbolos<String, DefVariable>();
		this.funciones = new HashMap<String, DefFuncion>();
		this.estructuras = new HashMap<String, DefEstructura>();
	}

	@Override
	public Object visit(DefVariable node, Object param) {
		DefVariable definicion = variables.getFromTop(node.getNombre());
		predicado(definicion == null, "Variable ya definida: " + node.getNombre(), node.getStart());
		variables.put(node.getNombre(), node);
		return super.visit(node, param);
	}

	@Override
	public Object visit(DefFuncion node, Object param) {
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion == null, "Función ya definida: " + node.getNombre(), node.getStart());
		funciones.put(node.getNombre(), node);

		variables.set();
		super.visit(node, funciones.get(node.getNombre()));
		variables.reset();

		return null;
	}

	@Override
	public Object visit(DefEstructura node, Object param) {
		DefEstructura definicion = estructuras.get(node.getNombre());
		predicado(definicion == null, "Estructura ya definida: " + node.getNombre(), node.getStart());

		Map<String, DefCampo> campos = new HashMap<String, DefCampo>();
		for (DefCampo campo : node.getCampos()) {
			DefCampo defCampo = campos.get(campo.getNombre());
			predicado(defCampo == null, "Campo ya definido: " + campo.getNombre(), campo.getStart());

			campos.put(campo.getNombre(), campo);
		}

		estructuras.put(node.getNombre(), node);
		return super.visit(node, param);
	}

	@Override
	public Object visit(TipoIdent node, Object param) {
		DefEstructura definicion = estructuras.get(node.getNombre());
		predicado(definicion != null, "Estructura no definida: " + node.getNombre(), node.getStart());

		node.setDefinicion(definicion);
		return super.visit(node, param);
	}

	@Override
	public Object visit(Return node, Object param) {
		node.setFuncion((DefFuncion) param);
		return super.visit(node, param);
	}

	@Override
	public Object visit(InvocacionProcedimiento node, Object param) {
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Procedimiento no definido: " + node.getNombre(), node.getStart());

		node.setDefFuncion(definicion);
		return super.visit(node, param);
	}

	@Override
	public Object visit(InvocacionFuncion node, Object param) {
		DefFuncion definicion = funciones.get(node.getNombre());
		predicado(definicion != null, "Funcion no definida: " + node.getNombre(), node.getStart());

		node.setDefFuncion(definicion); // Enlazar referencia con definición

		return super.visit(node, param);
	}

	@Override
	public Object visit(Variable node, Object param) {
		DefVariable definicion = variables.getFromAny(node.getNombre());
		predicado(definicion != null, "Variable no definida: " + node.getNombre(), node.getStart());
		node.setDefinicion(definicion); // Enlazar referencia con definición	
		return null;
	}

	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
	 * 
	 * Ejemplo de uso:
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", expr.getStart());
	 * 	predicado(variables.get(nombre), "La variable no ha sido definida", null);
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
			gestorErrores.error("Identificación", mensajeError, posicionError);
	}

}
