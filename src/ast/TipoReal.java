/**
 * @generated VGen 1.3.3
 */

package ast;

import visitor.Visitor;

// tipoReal:tipo ->

public class TipoReal extends AbstractTipo {

	@Override
	public Object accept(Visitor v, Object param) {
		return v.visit(this, param);
	}

	// Extra

	@Override
	public int getSize() {
		return 4;
	}

	@Override
	public String getNombreMAPL() {
		return "float";
	}

	@Override
	public char getSufijo() {
		return 'f';
	}

}
