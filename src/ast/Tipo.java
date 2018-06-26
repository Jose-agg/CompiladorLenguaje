/**
 * @generated VGen 1.3.3
 */

package ast;

public interface Tipo extends AST {

	public int getSize();

	public String getNombreMAPL();

	public char getSufijo();

	public Tipo promociona(Tipo tipo);
}
