package visitor;

import ast.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * 	-	Muestra la estructura del árbol en HTML.
 * 	-	Destaca los hijos/propiedades a null.
 * 	-	Muestra a qué texto apuntan las posiciones de cada nodo (linea/columna) ayudando así
 * 			a decidir cual de ellas usar en los errores y generación de código.
 * 
 * Esta clase se generará con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto). 
 * 
 */
public class ASTPrinter extends DefaultVisitor {

	
	public static void toHtml(String sourceFile, AST raiz, String filename) {
		// Este método será será generado por VGen.
	}

}

