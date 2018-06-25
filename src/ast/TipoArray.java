/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.*;

//	tipoArray:tipo -> dimension:literalEntero  tipo:tipo

public class TipoArray extends AbstractTipo {

	public TipoArray(LiteralEntero dimension, Tipo tipo) {
		this.dimension = dimension;
		this.tipo = tipo;

		searchForPositions(dimension, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public TipoArray(Object dimension, Object tipo) {
		this.dimension = (LiteralEntero) dimension;
		this.tipo = (Tipo) tipo;

		searchForPositions(dimension, tipo);	// Obtener linea/columna a partir de los hijos
	}

	public LiteralEntero getDimension() {
		return dimension;
	}
	public void setDimension(LiteralEntero dimension) {
		this.dimension = dimension;
	}

	public Tipo getTipo() {
		return tipo;
	}
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private LiteralEntero dimension;
	private Tipo tipo;
}

