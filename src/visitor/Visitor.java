/**
 * @generated VGen 1.3.3
 */

package visitor;

import ast.*;

public interface Visitor {
	public Object visit(Programa node, Object param);
	public Object visit(DefVariable node, Object param);
	public Object visit(DefFuncion node, Object param);
	public Object visit(DefEstructura node, Object param);
	public Object visit(DefCampo node, Object param);
	public Object visit(DefReturn node, Object param);
	public Object visit(Cuerpo node, Object param);
	public Object visit(TipoEntero node, Object param);
	public Object visit(TipoReal node, Object param);
	public Object visit(TipoChar node, Object param);
	public Object visit(TipoIdent node, Object param);
	public Object visit(TipoArray node, Object param);
	public Object visit(Asignacion node, Object param);
	public Object visit(Print node, Object param);
	public Object visit(PrintLN node, Object param);
	public Object visit(PrintSP node, Object param);
	public Object visit(Read node, Object param);
	public Object visit(IfElse node, Object param);
	public Object visit(While node, Object param);
	public Object visit(Return node, Object param);
	public Object visit(InvocacionProcedimiento node, Object param);
	public Object visit(LiteralEntero node, Object param);
	public Object visit(LiteralReal node, Object param);
	public Object visit(LiteralChar node, Object param);
	public Object visit(Variable node, Object param);
	public Object visit(ExpresionAritmetica node, Object param);
	public Object visit(ExpresionComparacion node, Object param);
	public Object visit(Negacion node, Object param);
	public Object visit(InvocacionFuncion node, Object param);
	public Object visit(Cast node, Object param);
	public Object visit(AccesoArray node, Object param);
	public Object visit(AccesoCampo node, Object param);
	public Object visit(MenosUnario node, Object param);
}
