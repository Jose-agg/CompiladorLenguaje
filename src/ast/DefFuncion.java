/**
 * @generated VGen 1.3.3
 */

package ast;

import java.util.*;
import visitor.*;

//	defFuncion:definicion -> nombre:String  parametros:defVariable*  retorno:defReturn  cuerpo:cuerpo

public class DefFuncion extends AbstractDefinicion {

	public DefFuncion(String nombre, List<DefVariable> parametros, DefReturn retorno, Cuerpo cuerpo) {
		this.nombre = nombre;
		this.parametros = parametros;
		this.retorno = retorno;
		this.cuerpo = cuerpo;

		searchForPositions(parametros, retorno, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	@SuppressWarnings("unchecked")
	public DefFuncion(Object nombre, Object parametros, Object retorno, Object cuerpo) {
		this.nombre = (nombre instanceof Token) ? ((Token)nombre).getLexeme() : (String) nombre;
		this.parametros = (List<DefVariable>) parametros;
		this.retorno = (DefReturn) retorno;
		this.cuerpo = (Cuerpo) cuerpo;

		searchForPositions(nombre, parametros, retorno, cuerpo);	// Obtener linea/columna a partir de los hijos
	}

	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<DefVariable> getParametros() {
		return parametros;
	}
	public void setParametros(List<DefVariable> parametros) {
		this.parametros = parametros;
	}

	public DefReturn getRetorno() {
		return retorno;
	}
	public void setRetorno(DefReturn retorno) {
		this.retorno = retorno;
	}

	public Cuerpo getCuerpo() {
		return cuerpo;
	}
	public void setCuerpo(Cuerpo cuerpo) {
		this.cuerpo = cuerpo;
	}

	@Override
	public Object accept(Visitor v, Object param) { 
		return v.visit(this, param);
	}

	private String nombre;
	private List<DefVariable> parametros;
	private DefReturn retorno;
	private Cuerpo cuerpo;
}

