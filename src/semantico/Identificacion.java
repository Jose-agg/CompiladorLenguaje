package semantico;

import ast.Position;
import main.GestorErrores;
import visitor.DefaultVisitor;

public class Identificacion extends DefaultVisitor {

	private GestorErrores gestorErrores;

	public Identificacion(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	/**
	 * Método auxiliar opcional para ayudar a implementar los predicados de la Gramática Atribuida.
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
