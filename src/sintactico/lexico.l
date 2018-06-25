// @author José Antonio García García

/* --------- No es necesario modificar esta parte --------- */
package sintactico;

import java.io.*;
import main.*;
import ast.Position;

%%
%byaccj
%unicode
%line
%column
%public

%{
	public Yylex(Reader in, GestorErrores gestor) {
		this(in);
		this.gestor = gestor;
	}

	public int line() { return yyline + 1; }
	public int column() { return yycolumn + 1; }
	public String lexeme() { return yytext(); }

	// Traza para probar el léxico de manera independiente al sintáctico
	public static void main(String[] args) throws Exception {
		Yylex lex = new Yylex(new FileReader(Main.programa), new GestorErrores());
		int token;
		while ((token = lex.yylex()) != 0)
			System.out.println("\t[" + lex.line() + ":" + lex.column() + "] Token: " + token + ". Lexema: " + lex.lexeme());
	}

	private GestorErrores gestor;
%}

%%

/* --------- Modificar aquí. Añadir reglas en esta sección --------- */

if								{ return Parser.IF;}
else							{ return Parser.ELSE;}
while							{ return Parser.WHILE;}
read							{ return Parser.READ;}
print							{ return Parser.PRINT;}
printsp							{ return Parser.PRINTSP;}
println							{ return Parser.PRINTLN;}
return							{ return Parser.RETURN;}
cast							{ return Parser.CAST;}
null							{ return Parser.NULL;}

var								{ return Parser.VAR;}
int								{ return Parser.ENTERO;}
real | float					{ return Parser.REAL;}
char | byte						{ return Parser.CHAR;}
struct							{ return Parser.STRUCT;}

[0-9]+							{ return Parser.LITERAL_ENTERO;}
[0-9]+"."[0-9]+					{ return Parser.LITERAL_REAL;}
'.'	| '\\n'						{ return Parser.LITERAL_CHAR;}

">="							{ return Parser.MAYOR_IGUAL;}
"<="							{ return Parser.MENOR_IGUAL;}
"=="							{ return Parser.IGUAL_QUE;}
"!="							{ return Parser.DISTINTO;}
"&&"							{ return Parser.AND;}
"||"							{ return Parser.OR;}

[a-zA-ZÃ±Ã‘][a-zA-Z0-9_ñÑ‘]*		{return Parser.ID;}
[+\-*/><\[\](){}!:;,=\.]			{ return yytext().charAt(0);}

/* --------- Se aconseja dejar estas reglas al final --------- */

"/*"([^*]|\*+[^*/])*\*+"/"		{ }		/* Comentario de varias lineas como este */
"//".*							{ }		// Comentario de una linea como este

[ \n\r]		{ }
"\t"		{ yycolumn += 3; } // Para que coincida con la info del editor de Eclipse (opcional). En eclipse: \t == 4 caracteres. En Jflex: \t == 1 carácter.

.			{ gestor.error("Léxico", "Cadena \"" + yytext() +"\" no reconocida.", new Position(line(), column())); }
