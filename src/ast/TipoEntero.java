/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// tipoEntero:tipo ->

public class TipoEntero extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	@Override
	public int getSize() {
		return 2;
	}

	@Override
	public String getNombreMAPL() {
		return "int";
	}

	@Override
	public char getSufijo() {
		return 'i';
	}

	@Override
	public Tipo promociona(Tipo tipo) {
		if (tipo instanceof TipoEntero || tipo instanceof TipoChar) {
			return this;
		}
		return null;
	}

}
