package main;

import ast.Position;

public class GestorErrores {

	public void error(String fase, String msg, Position position) {
		String texto = "Error en " + fase + ": ";
		if (position != null)
			texto += "[" + position + "] ";
		error(texto + msg);
	}

	public void error(String msg) {
		errores++;
		System.out.println(msg);
	}

	public boolean hayErrores() {
		return errores > 0;
	}

	private int errores = 0;
}
