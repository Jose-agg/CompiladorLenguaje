// @author José Antonio García García

/* --------- No es necesario modificar esta sección --------- */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

/* --------- Precedencias aquí --------- */
%left '+' '-'
%left '*' '/'

%%

/* --------- Añadir las reglas en esta sección --------- */

program: ';' 
	;







%%
/* --------- No es necesario modificar esta sección --------- */

public Parser(Yylex lex, GestorErrores gestor, boolean debug) {
	this(debug);
	this.lex = lex;
	this.gestor = gestor;
}

/* --------- Métodos de acceso para el main --------- */

public int parse() { return yyparse(); }
public AST getAST() { return raiz; }

/* --------- Funciones requeridas por Yacc --------- */

void yyerror(String msg) {
	Token lastToken = (Token) yylval;
	gestor.error("Sintáctico", "Token = " + lastToken.getToken() + ", lexema = \"" + lastToken.getLexeme() + "\". " + msg, lastToken.getStart());
}

int yylex() {
	try {
		int token = lex.yylex();
		yylval = new Token(token, lex.lexeme(), lex.line(), lex.column());
		return token;
	} catch (IOException e) {
		return -1;
	}
}

private Yylex lex;
private GestorErrores gestor;
private AST raiz;
