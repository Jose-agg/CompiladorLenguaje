/**
 * @generated VGen 1.3.3
 */

package ast;

public abstract class AbstractExpresion extends AbstractTraceable implements Expresion {

	private boolean modificable;
	private Tipo tipo;

	@Override
	public boolean isModificable() {
		return modificable;
	}

	public void setModificable(boolean modificable) {
		this.modificable = modificable;
	}

	@Override
	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}
}
