package ast;

/**
 * @generated VGen 1.3.3
 */

import java.util.*;

public abstract class AbstractTraceable implements Traceable {

	@Override
	public Position getStart() {
		return start;
	}

	@Override
	public Position getEnd() {
		return end;
	}

	public Traceable startPosition(Position start) {
		this.start = start;
		invariant();
		return this;
	}

	public Traceable endPosition(Position end) {
		this.end = end;
		invariant();
		return this;
	}

	// -----------------------------------------------
	// Métodos public para ser usados desde el Yacc

	public Traceable startPosition(Object symbol) {
		return startPosition(findStart(symbol));
	}

	public Traceable endPosition(Object symbol) {
		return endPosition(findEnd(symbol));
	}

	public Traceable setPositions(Object startSymbol, Object endSymbol) {
		this.start = findStart(startSymbol);
		this.end = findEnd(endSymbol);
		invariant();
		return this;
	}

	public Traceable setPositions(Object symbol) {
		return setPositions(symbol, symbol);
	}

	// -----------------------------------------------
	// Método protected para ser llamado desde los
	// constructores de las clases AST

	// searchForPositions: Establece:
	// 1. La posición inicial (start) a partir
	// del PRIMER argumento que tenga posición inicial.
	// 2. La posición final (end) a partir del ULTIMO
	// argumento que tenga posición final.
	protected void searchForPositions(Object... children) {
		setPositions(Arrays.asList(children));
	}

	// -----------------------------------------------
	// Métodos privados para ser llamados SÓLO desde los
	// métodos anteriores

	@SuppressWarnings("unchecked")
	private Position findStart(Object node) {
		if (node instanceof Traceable)
			return ((Traceable) node).getStart();
		if (node instanceof List)
			return findStart((List<Object>) node);
		return null;
	}

	private Position findStart(List<Object> nodes) {
		Position start = null;
		for (Object node : nodes) {
			Position nodeStart = findStart(node);
			if (start == null)
				start = nodeStart;
			else
				start = (nodeStart != null && nodeStart.lessThan(start)) ? nodeStart : start;
		}
		return start;
	}

	@SuppressWarnings("unchecked")
	private Position findEnd(Object node) {
		if (node instanceof Traceable)
			return ((Traceable) node).getEnd();
		if (node instanceof List)
			return findEnd((List<Object>) node);
		return null;
	}

	private Position findEnd(List<Object> nodes) {
		Position end = null;
		for (Object node : nodes) {
			Position nodeEnd = findEnd(node);
			if (end == null)
				end = nodeEnd;
			else
				end = (nodeEnd != null && nodeEnd.greaterThan(end)) ? nodeEnd : end;
		}
		return end;
	}

	private void invariant() {
		if ((getStart() != null || getEnd() != null) && getStart().greaterThan(getEnd()))
			throw new IllegalStateException("Las posiciones del nodo (start y end) son inválidas: o ambas null o (start <= end)");
	}

	private Position start, end;
}
