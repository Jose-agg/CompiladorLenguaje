package visitor;

import ast.*;

/**
 * ASTPrinter. Utilidad que ayuda a validar un arbol AST:
 * 	-	Muestra la estructura del �rbol en HTML.
 * 	-	Destaca los hijos/propiedades a null.
 * 	-	Muestra a qu� texto apuntan las posiciones de cada nodo (linea/columna) ayudando as�
 * 			a decidir cual de ellas usar en los errores y generaci�n de c�digo.
 * 
 * Esta clase se generar� con VGen. El uso de esta clase es opcional (puede eliminarse del proyecto). 
 * 
 */
public class ASTPrinter extends DefaultVisitor {

	
	public static void toHtml(String sourceFile, AST raiz, String filename) {
		// Este m�todo ser� ser� generado por VGen.
	}

}

