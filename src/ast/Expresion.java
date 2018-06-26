/**
 * @generated VGen 1.3.3
 */

package ast;

public interface Expresion extends AST {

	public boolean isModificable();

	public Tipo getTipo();

	public void setTipo(Tipo tipo);
}
