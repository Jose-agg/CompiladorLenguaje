package ast;

/**
 * @author Raúl Izquierdo
 */

public class Token {

	public Token(int token, String lexeme, int line, int column) {
		this.token = token;
		this.lexeme = lexeme;
		this.line = line;
		this.column = column;
	}

	public int getToken() {
		return token;
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

	private int token;
	private String lexeme;
	private int line;
	private int column;
	private Position start, end;
}
