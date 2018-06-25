package semantico;

import ast.AST;
import main.GestorErrores;

/**
 * Esta clase coordina las dos fases del Análisis Semántico:
 * 1- Fase de Identificación
 * 2- Fase de Inferencia
 * 
 * No es necesario modificar esta clase. En su lugar hay que modificar las clases
 * que son llamadas desde aquí: "Identificacion.java" y "ComprobacionDeTipos.java"
 * 
 * @author José Antonio García García
 *
 */
public class AnalisisSemantico {

	public AnalisisSemantico(GestorErrores gestor) {
		this.gestorErrores = gestor;
	}

	public void analiza(AST raiz) {
		Identificacion identificacion = new Identificacion(gestorErrores);
		raiz.accept(identificacion, null);

		if (gestorErrores.hayErrores())
			return;

		ComprobacionDeTipos comprobacion = new ComprobacionDeTipos(gestorErrores);
		raiz.accept(comprobacion, null);
	}

	private GestorErrores gestorErrores;
}
