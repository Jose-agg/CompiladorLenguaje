package semantico;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

/**
 * Implementación de una tabla Hash con contextos. Permite: 
 * - Insertar símbolos (put) en el contexto actual. 
 * - Buscar tanto en el contexto actual (getFromTop) como en todos los contextos (getFromAny). 
 * - Crear y destruir contextos mediante las operaciones set y reset.
 * 
 * La forma habitual de instanciarla será: 
 * TablaSimbolos<String, DefinicionVariable> variables = new TablaSimbolos<String, DefinicionVariable>();
 * 
 */
public class TablaSimbolos<S, D> {

	private Stack<Map<S, D>> pilaAmbitos = new Stack<Map<S, D>>();

	/**
	 * Crea la tabla de simbolos de ambito global
	 * 
	 */
	public TablaSimbolos() {
		set();
	}

	/**
	 * Guarda una variable en el ambito actual
	 * 
	 */
	public void put(S nombre, D def) {
		Map<S, D> map = pilaAmbitos.peek(); // Saca el ambito actual
		map.put(nombre, def); // Le añade la nueva variable

		//pilaAmbitos.peek().put(nombre, def);
	}

	/**
	 * Busca el identificador en el ambito local
	 * 
	 */
	public D getFromTop(S nombre) {
		Map<S, D> map = pilaAmbitos.peek();
		D valor = map.get(nombre);
		if (valor != null)
			return valor;
		return null;
		//return contextos.peek().get(nombre);
	}

	/**
	 * Recorre los ambitos de la pila, de mas local a mas global, hasta que
	 * encuentre ese identificador o termine de revisar todos los datos previamente
	 * guardados
	 * 
	 */
	public D getFromAny(S nombre) {
		for (int i = pilaAmbitos.size() - 1; i >= 0; i--) {
			Map<S, D> contexto = pilaAmbitos.get(i);
			D def = contexto.get(nombre);
			if (def != null)
				return def;
		}
		return null;
	}

	/**
	 * Añade un nuevo mapa a la pila
	 * 
	 */
	public void set() {
		pilaAmbitos.push(new HashMap<S, D>());
	}

	/**
	 * Elimina el ultimo mapa creado de la cima de la pila
	 * 
	 */
	public void reset() {
		pilaAmbitos.pop();
	}
}
