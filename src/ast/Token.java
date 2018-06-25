package ast;

/**
 * @generated VGen 1.3.3
 */

public class Token implements Traceable {

	public Token(int tokenType, String lexeme, int line, int column) {
		this.tokenType = tokenType;
		this.lexeme = lexeme;
		this.line = line;
		this.column = column;
	}

	public int getToken() {
		return tokenType;
	}

	public String getLexeme() {
		return lexeme;
	}

	public Position getStart() {
		if (start == null)
			start = new Position(line, column);
		return start;
	}

	public Position getEnd() {
		if (end == null)
			end = new Position(line, column + lexeme.length() - 1);
		return end;
	}

	@Override
	public String toString() {
		return "Token[" + getStart() + ", " + getEnd() + "] = " + lexeme;
	}

	private int tokenType;
	private String lexeme;
	private int line;
	private int column;
	private Position start, end;
}
