// @author José Antonio García García

/* --------- No es necesario modificar esta sección --------- */
%{
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
%}

%token IF ELSE WHILE READ PRINT PRINTSP PRINTLN RETURN CAST NULL
%token VAR ENTERO REAL CHAR STRUCT ID
%token LITERAL_ENTERO LITERAL_REAL LITERAL_CHAR
%token MAYOR_IGUAL MENOR_IGUAL IGUAL_QUE DISTINTO AND OR

/* --------- Precedencias aquí --------- */
%left AND OR '!'
%left IGUAL_QUE DISTINTO '<' '>' MAYOR_IGUAL MENOR_IGUAL
%left '+' '-'
%left '*' '/'
%left '[' ']' '.' ':' ',' ';'
%nonassoc '(' ')'

%%

/* --------- Añadir las reglas en esta sección --------- */

programa: definiciones		{ raiz = new Programa($1);}
	;

definiciones: definiciones definicion	{ $$ = $1; ((List)$1).add($2); }
	|									{ $$ = new ArrayList(); }
	;
	
definicion: definicionVariable			{ $$ = $1; }
	| definicionStruct					{ $$ = $1; }
	| definicionFuncion					{ $$ = $1; }
	;

/* ---------------- Definicion de variables ------------------------------ */
	
definicionVariable: VAR ID ':' tipo ';'				{ $$ = new DefVariable($2, $4,"Global"); }
	;
	
definicionVariableLocal: VAR ID ':' tipo ';'		{ $$ = new DefVariable($2, $4,"Local"); }
	;
	
definicionesParametros: definicionParametros		{ $$ = $1;}
	| 												{ $$ = new ArrayList(); }
	;
	
definicionParametros: definicionParametro			{ $$ = new ArrayList();((List)$$).add($1);}
	| definicionParametros ',' definicionParametro	{ $$ = $1; ((List)$1).add($3);}
	;

definicionParametro: ID ':' tipo					{ $$ = new DefVariable($1, $3,"Parametro"); }
	;
	
tipo: ENTERO						{ $$ = new TipoEntero(); }
	| REAL							{ $$ = new TipoReal(); }
	| CHAR							{ $$ = new TipoChar(); }
	| ID							{ $$ = new TipoIdent($1); }
	| '[' LITERAL_ENTERO ']' tipo	{ $$ = new TipoArray(new LiteralEntero($2),$4); }
	;

/* ---------------- Definicion de tipos estructura ---------------------- */

definicionStruct: STRUCT ID '{' definicionCampos '}' ';' 	{ $$ = new DefEstructura($2,$4); }
	;

definicionCampos: definicionCampo				{ $$ = new ArrayList(); ((List)$$).add($1); }
	| definicionCampos definicionCampo			{ $$ = $1; ((List)$1).add($2); }
	;
	
definicionCampo: ID ':' tipo ';'				{ $$ = new DefCampo($1,$3); }
	;

/* ---------------- Definicion de funciones ---------------------- */

definicionFuncion: ID '(' definicionesParametros ')' definicionReturn '{' cuerpo'}'	{ $$ = new DefFuncion($1,$3,$5,$7); }
	;

definicionReturn: ':' tipo					{ $$ = new DefReturn($2); }
	|								{ $$ = new DefReturn(null); }
	;
	
cuerpo: definicionVariablesFuncion sentencias		{ $$ = new Cuerpo($1,$2);}
	;
	
definicionVariablesFuncion: definicionVariablesFuncion definicionVariableLocal	{ $$ = $1; ((List)$1).add($2);}
 	|								{ $$ = new ArrayList();} 
	;
	
sentencias: sentencias sentencia	{ $$ = $1; ((List)$1).add($2); }
	|								{ $$ = new ArrayList(); }
	;
	
sentencia: asignacion				{ $$ = $1; }	
	| escritura						{ $$ = $1; }
	| lectura						{ $$ = $1; }
	| condicional					{ $$ = $1; }
	| iterativa						{ $$ = $1; }
	| retorno						{ $$ = $1; }
	| invocacionProcedimiento		{ $$ = $1; }
	;
	
/* ---------------- Procedimiento: conjunto de instrucciones que cumplen una tarea ---------------- */
invocacionProcedimiento: ID '(' argumentos ')' ';'	{ $$ = new InvocacionProcedimiento($1,$3); }
	;
	
/* ---------------- Funcion: procedimiento que retorna un valor ---------------- */
invocacionFuncion: ID '(' argumentos ')'	{ $$ = new InvocacionFuncion($1,$3); }
	;
	
asignacion: expresion '=' expresion ';'		{ $$ = new Asignacion($1,$3); }
	;
	
escritura: PRINT expresion ';'				{ $$ = new Print($2); }
	| PRINTSP expresion ';'					{ $$ = new PrintSP($2); }
	| PRINTLN expresion ';'					{ $$ = new PrintLN($2); }
	| PRINTLN ';'							{ $$ = new PrintLN(null).setPositions($1); }
	;
	
lectura: READ expresion ';'						{ $$ = new Read($2); }
	;
	
condicional: IF '(' expresion ')' '{' sentencias '}'						{ $$ = new IfElse($3,$6,new ArrayList()); }
	| IF '(' expresion ')' '{' sentencias '}' ELSE '{' sentencias '}'		{ $$ = new IfElse($3,$6,$10); }
	;
	
iterativa: WHILE '(' expresion ')' '{' sentencias '}' 		{ $$ = new While($3,$6); }
	;
	
retorno: RETURN expresion ';'					{ $$ = new Return($2); }
	| RETURN ';'								{ $$ = new Return(null).setPositions($1); }
	| RETURN NULL								{ $$ = new Return(null).setPositions($1); }
	;
	
expresion: LITERAL_ENTERO					{ $$ = new LiteralEntero($1); }
	| LITERAL_REAL							{ $$ = new LiteralReal($1); }
	| LITERAL_CHAR							{ $$ = new LiteralChar($1); }
	| ID									{ $$ = new Variable($1); }
	| expresion '+' expresion				{ $$ = new ExpresionAritmetica($1,"+",$3); }
	| expresion '-' expresion				{ $$ = new ExpresionAritmetica($1,"-",$3); }
	| expresion '*' expresion				{ $$ = new ExpresionAritmetica($1,"*",$3); }
	| expresion '/' expresion				{ $$ = new ExpresionAritmetica($1,"/",$3); }
	| expresion '<' expresion				{ $$ = new ExpresionComparacion($1,"<",$3); }
	| expresion '>' expresion				{ $$ = new ExpresionComparacion($1,">",$3); }
	| expresion MENOR_IGUAL expresion		{ $$ = new ExpresionComparacion($1,"<=",$3); }
	| expresion MAYOR_IGUAL expresion		{ $$ = new ExpresionComparacion($1,">=",$3); }
	| expresion IGUAL_QUE expresion			{ $$ = new ExpresionComparacion($1,"==",$3); }
	| expresion DISTINTO expresion			{ $$ = new ExpresionComparacion($1,"!=",$3); }
	| expresion AND expresion				{ $$ = new ExpresionComparacion($1,"&&",$3); }
	| expresion OR expresion				{ $$ = new ExpresionComparacion($1,"||",$3); }
	| '!' expresion							{ $$ = new Negacion($2); }
	| '(' expresion ')'						{ $$ = $2; }
	| invocacionFuncion						{ $$ = $1; }
	| CAST '<' tipo '>' '(' expresion ')'	{ $$ = new Cast($3,$6); }
	| expresion '[' expresion ']'			{ $$ = new AccesoArray($1,$3); }
	| expresion '.' ID						{ $$ = new AccesoCampo($1,$3); }
	| '-' expresion							{ $$ = new MenosUnario($2); }
	;
	
argumentos: argumento				{ $$ = $1; }
	|								{ $$ = new ArrayList(); }
	;
	
argumento: expresion				{ $$ = new ArrayList(); ((List)$$).add($1); } 
	| argumento ',' expresion		{ $$ = $1; ((List)$1).add($3); }
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
