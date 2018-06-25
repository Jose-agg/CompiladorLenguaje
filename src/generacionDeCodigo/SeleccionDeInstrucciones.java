package generacionDeCodigo;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.AccesoArray;
import ast.AccesoCampo;
import ast.Asignacion;
import ast.Cast;
import ast.Cuerpo;
import ast.DefCampo;
import ast.DefEstructura;
import ast.DefFuncion;
import ast.DefVariable;
import ast.ExpresionAritmetica;
import ast.ExpresionComparacion;
import ast.IfElse;
import ast.InvocacionFuncion;
import ast.InvocacionProcedimiento;
import ast.LiteralChar;
import ast.LiteralEntero;
import ast.LiteralReal;
import ast.MenosUnario;
import ast.Negacion;
import ast.Print;
import ast.PrintLN;
import ast.PrintSP;
import ast.Programa;
import ast.Read;
import ast.Return;
import ast.Tipo;
import ast.TipoArray;
import ast.TipoIdent;
import ast.Variable;
import ast.While;
import visitor.DefaultVisitor;

/**
 * @author José Antonio García García
 */
public class SeleccionDeInstrucciones extends DefaultVisitor {

	private enum TipoAcceso {
		DIRECCION, VALOR
	};

	private PrintWriter writer;
	private String sourceFile;
	private int contadorWhile = 0;
	private int contadorIfs = 0;

	private Map<String, String> instruccion = new HashMap<String, String>();

	public SeleccionDeInstrucciones(Writer writer, String sourceFile) {

		this.writer = new PrintWriter(writer);
		this.sourceFile = sourceFile;

		instruccion.put("+", "add");
		instruccion.put("-", "sub");
		instruccion.put("*", "mul");
		instruccion.put("/", "div");
		instruccion.put("<", "lt");
		instruccion.put(">", "gt");
		instruccion.put("AND", "and");
		instruccion.put("OR", "or");
		instruccion.put("IGUAL", "eq");
		instruccion.put("DISTINTO", "ne");
		instruccion.put("MAYORIGUAL", "ge");
		instruccion.put("MENORIGUAL", "le");

	}

	public Object visit(Programa node, Object param) {
		genera("#source \"" + sourceFile + "\"");

		genera("call main");
		genera("halt");
		visitChildren(node.getDefiniciones(), param);
		return null;
	}

	public Object visit(DefVariable node, Object param) {
		if (node.getAmbito().equals("Global"))
			genera("#GLOBAL " + node.getNombre() + ":" + node.getTipo().getNombreMAPL());

		return null;
	}

	public Object visit(DefEstructura node, Object param) {
		String salida = "#TYPE " + node.getNombre() + ":{";
		for (DefCampo campo : node.getCampos()) {
			salida += "\n\t" + campo.getNombre() + ":" + campo.getTipo().getNombreMAPL();
		}
		genera(salida + "\n}");
		return null;
	}

	public Object visit(DefFuncion node, Object param) {
		genera("#FUNC " + node.getNombre());
		for (DefVariable def : node.getParametros()) {
			genera("#PARAM " + def.getNombre() + ":" + def.getTipo().getNombreMAPL());
		}

		genera("#RET "
				+ ((node.getRetorno().getTipo() == null) ? "VOID" : node.getRetorno().getTipo().getNombreMAPL()));

		for (DefVariable def : node.getCuerpo().getDefinicionesVariables()) {
			genera("#LOCAL " + def.getNombre() + ":" + def.getTipo().getNombreMAPL());
		}
		genera(node.getNombre() + ":");
		super.visit(node, param);

		if (node.getRetorno().getTipo() == null) {
			int sumaVariables = getSizeVariables(node.getCuerpo().getDefinicionesVariables());
			int sumaParametros = getSizeVariables(node.getParametros());

			genera("ret " + 0 + "," + sumaVariables + "," + sumaParametros);
		}

		return null;
	}

	private int getSizeVariables(List<DefVariable> definiciones) {
		int suma = 0;
		for (DefVariable def : definiciones) {
			suma += def.getTipo().getSize();
		}
		return suma;
	}

	public Object visit(Cuerpo node, Object param) {

		int sumaVariablesLocales = getSizeVariables(node.getDefinicionesVariables());
		genera("enter " + sumaVariablesLocales);
		visitChildren(node.getDefinicionesVariables(), param);
		visitChildren(node.getSentencias(), param);

		return null;
	}

	public Object visit(Return node, Object param) {
		genera("#line " + node.getEnd().getLine());

		super.visit(node, TipoAcceso.VALOR); // Para coger lo que hay dentro del return.

		int sumaVariablesLocales = getSizeVariables(node.getFuncion().getCuerpo().getDefinicionesVariables());
		int sumaParametros = getSizeVariables(node.getFuncion().getParametros());

		int tamanioReturn = 0;
		if (node.getDevolucion() != null)
			tamanioReturn = node.getDevolucion().getTipo().getSize(); //Por si fuera un return ;

		genera("ret " + tamanioReturn + "," + sumaVariablesLocales + "," + sumaParametros);

		return null;
	}

	public Object visit(Print node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getSalida().accept(this, TipoAcceso.VALOR);
		genera("out", node.getSalida().getTipo());
		return null;
	}

	public Object visit(Asignacion node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getLeft().accept(this, TipoAcceso.DIRECCION);
		node.getRight().accept(this, TipoAcceso.VALOR);
		genera("store", node.getLeft().getTipo());

		return null;
	}

	public Object visit(ExpresionAritmetica node, Object param) {
		assert (param == TipoAcceso.VALOR);
		node.getLeft().accept(this, TipoAcceso.VALOR);
		node.getRight().accept(this, TipoAcceso.VALOR);

		genera(instruccion.get(node.getOperador()), node.getRight().getTipo());
		return null;
	}

	public Object visit(ExpresionComparacion node, Object param) {
		assert (param == TipoAcceso.VALOR);
		node.getLeft().accept(this, TipoAcceso.VALOR);
		node.getRight().accept(this, TipoAcceso.VALOR);
		if (node.getOperador() == "AND" || node.getOperador() == "OR")
			genera(instruccion.get(node.getOperador()));
		else
			genera(instruccion.get(node.getOperador()), node.getRight().getTipo());
		return null;
	}

	public Object visit(Negacion node, Object param) {
		assert (param == TipoAcceso.VALOR);
		super.visit(node, TipoAcceso.VALOR);
		genera("not");
		return null;
	}

	public Object visit(Variable node, Object param) {
		if (((TipoAcceso) param) == TipoAcceso.VALOR) {
			visit(node, TipoAcceso.DIRECCION);
			genera("load", node.getTipo());
		} else { // Funcion.DIRECCION
			assert (param == TipoAcceso.DIRECCION);
			if (node.getDefinicion().getAmbito().equals("Global")) {
				genera("pusha " + node.getDefinicion().getDireccion());
			} else {
				genera("pusha BP");
				genera("push " + node.getDefinicion().getDireccion());
				genera("add");
			}
		}
		return null;
	}

	public Object visit(AccesoArray node, Object param) {
		node.getIdentificador().accept(this, TipoAcceso.DIRECCION);
		node.getPosicion().accept(this, TipoAcceso.VALOR);
		genera("push " + ((TipoArray) node.getIdentificador().getTipo()).getTipo().getSize());
		genera("mul");
		genera("add");
		if (param == TipoAcceso.VALOR) {
			genera("load", node.getTipo());
		}

		return null;
	}

	public Object visit(AccesoCampo node, Object param) {
		super.visit(node, TipoAcceso.DIRECCION);
		if (node.getExpresion().getTipo().getClass() == TipoIdent.class) {
			DefEstructura defS = ((TipoIdent) node.getExpresion().getTipo()).getDefinicion();
			for (DefCampo dC : defS.getCampos()) {
				if (dC.getNombre().equals(node.getCampo())) {
					genera("push " + dC.getDireccion());
					genera("add");
					break;
				}
			}
			if (param == TipoAcceso.VALOR) {
				genera("load", node.getTipo());
			}
		}
		return null;
	}

	public Object visit(LiteralEntero node, Object param) {
		assert (param == TipoAcceso.VALOR);
		genera("push " + node.getValor());
		return null;
	}

	public Object visit(LiteralReal node, Object param) {
		assert (param == TipoAcceso.VALOR);
		genera("pushf " + node.getValor());
		return null;
	}

	public Object visit(LiteralChar node, Object param) {
		assert (param == TipoAcceso.VALOR);
		String valor = node.getValor().split("'")[1];
		if (valor.equalsIgnoreCase("\\n"))
			genera("pushb 10"); // Caso especial
		else
			genera("pushb " + valor.hashCode());
		return null;
	}

	public Object visit(InvocacionProcedimiento node, Object param) {
		super.visit(node, TipoAcceso.VALOR);

		genera("#line " + node.getEnd().getLine());
		genera("call " + node.getNombre());
		if (node.getDefFuncion().getRetorno().getTipo() != null) {
			genera("pop");
		}
		return null;
	}

	public Object visit(InvocacionFuncion node, Object param) {
		super.visit(node, TipoAcceso.VALOR);

		genera("call " + node.getNombre());
		return null;
	}

	public Object visit(Cast node, Object param) {
		node.getExpresion().accept(this, TipoAcceso.VALOR);
		genera(node.getExpresion().getTipo().getSufijo() + "2" + node.getTipo().getSufijo());
		return null;
	}

	public Object visit(Read node, Object param) {
		node.getEntrada().accept(this, TipoAcceso.DIRECCION);
		genera("in", node.getEntrada().getTipo());
		genera("store", node.getEntrada().getTipo());
		return null;
	}

	public Object visit(While node, Object param) {
		contadorWhile++;
		genera("while" + contadorWhile + ":");
		int conAux = contadorWhile;
		node.getCondicion().accept(this, TipoAcceso.VALOR);
		genera("jz finWhile" + conAux);
		visitChildren(node.getCorrecto(), param);
		genera("jmp while" + conAux);
		genera("finWhile" + conAux + ":");
		return null;
	}

	public Object visit(IfElse node, Object param) {
		contadorIfs++;
		int conAux = contadorIfs;
		node.getCondicion().accept(this, TipoAcceso.VALOR);
		genera("jz else" + conAux);
		visitChildren(node.getCorrecto(), param);
		genera("jmp finIf" + conAux);
		genera("else" + conAux + ":");
		visitChildren(node.getIncorrecto(), param);
		genera("finIf" + conAux + ":");
		return null;
	}

	public Object visit(PrintLN node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getSalida().accept(this, TipoAcceso.VALOR);
		genera("out", node.getSalida().getTipo());
		genera("pushb 10");
		genera("outb");
		return null;
	}

	public Object visit(PrintSP node, Object param) {
		genera("#line " + node.getEnd().getLine());
		node.getSalida().accept(this, TipoAcceso.VALOR);
		genera("out", node.getSalida().getTipo());
		genera("pushb 32");
		genera("outb");
		return null;
	}

	public Object visit(MenosUnario node, Object param) {
		genera("pushi 0");

		super.visit(node, param);
		genera("sub");

		return null;
	}

	// Método auxiliar recomendado -------------
	private void genera(String instruccion) {
		System.out.println(instruccion);
		writer.println(instruccion);
	}

	private void genera(String instruccion, Tipo tipo) {
		genera(instruccion + tipo.getSufijo());
	}
}
