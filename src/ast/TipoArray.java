/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// tipoArray:tipo -> dimension:literalEntero tipo:tipo

public class TipoArray extends AbstractTipo {

	private LiteralEntero dimension;
	private Tipo tipo;

	public TipoArray(LiteralEntero dimension, Tipo tipo) {
		this.dimension = dimension;
		this.tipo = tipo;

		searchForPositions(dimension, tipo); // Obtener linea/columna a partir de los hijos
	}

	public TipoArray(Object dimension, Object tipo) {
		this.dimension = (LiteralEntero) dimension;
		this.tipo = (Tipo) tipo;

		searchForPositions(dimension, tipo); // Obtener linea/columna a partir de los hijos
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

	// Extra

	@Override
	public int getSize() {
		return tipo.getSize() * Integer.parseInt(dimension.getValor());
	}

	@Override
	public String getNombreMAPL() {
		return dimension.getValor() + " * " + tipo.getNombreMAPL();
	}

	@Override
	public char getSufijo() {
		return tipo.getSufijo();
	}

	@Override
	public Tipo promociona(Tipo tipo) {
		return null;
	}
}
