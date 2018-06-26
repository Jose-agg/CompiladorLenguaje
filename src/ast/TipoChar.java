/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// tipoChar:tipo ->

public class TipoChar extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	@Override
	public int getSize() {
		return 1;
	}

	@Override
	public String getNombreMAPL() {
		return "char";
	}

	@Override
	public char getSufijo() {
		return 'b';
	}

	@Override
	public Tipo promociona(Tipo tipo) {
		if (tipo instanceof TipoChar) {
			return this;
		}
		return null;
	}
}
