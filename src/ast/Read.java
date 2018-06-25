/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	read:sentencia -> entrada:expresion

public class Read extends AbstractSentencia {

	public Read(Expresion entrada) {
		this.entrada = entrada;

		searchForPositions(entrada);	// Obtener linea/columna a partir de los hijos
	}

	public Read(Object entrada) {
		this.entrada = (Expresion) entrada;

		searchForPositions(entrada);	// Obtener linea/columna a partir de los hijos
	}

	public Expresion getEntrada() {
		return entrada;
	}
	public void setEntrada(Expresion entrada) {
		this.entrada = entrada;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private Expresion entrada;
}

