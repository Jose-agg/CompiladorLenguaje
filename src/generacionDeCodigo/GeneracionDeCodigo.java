package generacionDeCodigo;

import java.io.Writer;

import ast.AST;

/**
 * Esta clase coordina las dos fases principales de la Generación de Código:
 * 1- Gestión de Memoria (asignación de direcciones)
 * 2- Selección de Instrucciones
 * 
 * No es necesario modificar esta clase. En su lugar hay que modificar las clases
 * que son llamadas desde aquí: "GestionDeMemoria.java" y "SeleccionDeInstrucciones.java".
 *   
 * @author José Antonio García García
 *
 */
public class GeneracionDeCodigo {

	public void genera(String sourceFile, AST raiz, Writer out) {
		GestionDeMemoria gestion = new GestionDeMemoria();
		raiz.accept(gestion, null);

		SeleccionDeInstrucciones selecciona = new SeleccionDeInstrucciones(out, sourceFile);
		raiz.accept(selecciona, null);
	}

}
