package generacionDeCodigo;

import ast.DefEstructura;
import ast.DefFuncion;
import ast.DefVariable;
import visitor.DefaultVisitor;

/** 
 * Clase encargada de asignar direcciones a las variables 
 * 
 * @author José Antonio García García
 */
public class GestionDeMemoria extends DefaultVisitor {

	private int contadorDireccionesGlobales = 0;
	private int contadorDireccionesLocales = 0;

	public Object visit(DefVariable node, Object param) {
		super.visit(node, param);
		if (node.getAmbito().equals("Global")) {
			node.setDireccion(contadorDireccionesGlobales);
			contadorDireccionesGlobales += node.getTipo().getSize();
		} else if (node.getAmbito().equals("Local")) {
			contadorDireccionesLocales += -node.getTipo().getSize();
			node.setDireccion(contadorDireccionesLocales);
		}
		return null;
	}

	public Object visit(DefFuncion node, Object param) {
		super.visit(node, param);

		contadorDireccionesLocales = 0;
		int direccionesParam = 4;
		for (int i = node.getParametros().size() - 1; i >= 0; i--) {
			node.getParametros().get(i).setDireccion(direccionesParam);
			direccionesParam += node.getParametros().get(i).getTipo().getSize();
		}

		return null;
	}

	public Object visit(DefEstructura node, Object param) {
		super.visit(node, param);

		int sumaDireccionesCampos = 0;

		for (int i = 0; i < node.getCampos().size(); i++) {
			node.getCampos().get(i).setDireccion(sumaDireccionesCampos);
			sumaDireccionesCampos += node.getCampos().get(i).getTipo().getSize();
		}

		return null;
	}

}
