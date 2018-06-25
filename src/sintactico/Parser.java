//### This file created by BYACC 1.8(/Java extension  1.14)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";






//#line 5 "sintac.y"
package sintactico;

import java.io.*;
import java.util.*;
import ast.*;
import main.*;
//#line 24 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//## **user defined:Object
String   yytext;//user variable to return contextual strings
Object yyval; //used to return semantic vals from action routines
Object yylval;//the 'lval' (result) I got from yylex()
Object valstk[] = new Object[YYSTACKSIZE];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
final void val_init()
{
  yyval=new Object();
  yylval=new Object();
  valptr=-1;
}
final void val_push(Object val)
{
  try {
    valptr++;
    valstk[valptr]=val;
  }
  catch (ArrayIndexOutOfBoundsException e) {
    int oldsize = valstk.length;
    int newsize = oldsize*2;
    Object[] newstack = new Object[newsize];
    System.arraycopy(valstk,0,newstack,0,oldsize);
    valstk = newstack;
    valstk[valptr]=val;
  }
}
final Object val_pop()
{
  return valstk[valptr--];
}
final void val_drop(int cnt)
{
  valptr -= cnt;
}
final Object val_peek(int relative)
{
  return valstk[valptr-relative];
}
//#### end semantic value section ####
public final static short IF=257;
public final static short ELSE=258;
public final static short WHILE=259;
public final static short READ=260;
public final static short PRINT=261;
public final static short PRINTSP=262;
public final static short PRINTLN=263;
public final static short RETURN=264;
public final static short CAST=265;
public final static short NULL=266;
public final static short VAR=267;
public final static short ENTERO=268;
public final static short REAL=269;
public final static short CHAR=270;
public final static short STRUCT=271;
public final static short ID=272;
public final static short LITERAL_ENTERO=273;
public final static short LITERAL_REAL=274;
public final static short LITERAL_CHAR=275;
public final static short MAYOR_IGUAL=276;
public final static short MENOR_IGUAL=277;
public final static short IGUAL_QUE=278;
public final static short DISTINTO=279;
public final static short AND=280;
public final static short OR=281;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    2,    3,    7,    8,    8,
    9,    9,   10,    6,    6,    6,    6,    6,    4,   11,
   11,   12,    5,   13,   13,   14,   15,   15,   16,   16,
   17,   17,   17,   17,   17,   17,   17,   24,   26,   18,
   19,   19,   19,   19,   20,   21,   21,   22,   23,   23,
   23,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   27,   27,   27,   27,   27,   27,
   27,   27,   27,   27,   25,   25,   28,   28,
};
final static short yylen[] = {                            2,
    1,    2,    0,    1,    1,    1,    5,    5,    1,    0,
    1,    3,    3,    1,    1,    1,    1,    4,    6,    1,
    2,    4,    8,    2,    0,    2,    2,    0,    2,    0,
    1,    1,    1,    1,    1,    1,    1,    5,    4,    4,
    3,    3,    3,    2,    3,    7,   11,    7,    3,    2,
    2,    1,    1,    1,    1,    3,    3,    3,    3,    3,
    3,    3,    3,    3,    3,    3,    3,    2,    3,    1,
    7,    4,    3,    2,    1,    0,    1,    3,
};
final static short yydefred[] = {                         3,
    0,    0,    0,    0,    0,    2,    4,    5,    6,    0,
    0,    0,    0,    0,    0,    0,    0,   11,   14,   15,
   16,   17,    0,    0,    0,    0,   20,    0,    0,    0,
    0,    7,    0,    0,   21,   13,    0,    0,   12,    0,
    0,   19,   24,   28,   18,   22,    0,    0,   23,    0,
   27,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,   53,   54,    0,    0,    0,   29,   31,
   32,   33,   34,   35,   36,   37,   70,    0,    0,    0,
    0,    0,    0,    0,    0,   44,    0,   51,   50,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   45,   41,   42,   43,   49,    0,
    0,    0,    0,   69,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   73,    0,    8,
    0,    0,    0,    0,    0,    0,   72,   40,   30,   30,
   39,    0,   38,    0,    0,    0,    0,    0,   48,   71,
    0,   30,    0,   47,
};
final static short yydgoto[] = {                          1,
    2,    6,    7,    8,    9,   24,   51,   16,   17,   18,
   26,   27,   38,   47,   48,   52,   69,   70,   71,   72,
   73,   74,   75,   76,  121,   77,   78,  123,
};
final static short yysindex[] = {                         0,
    0, -230, -270, -259,  -13,    0,    0,    0,    0,  -28,
 -102, -237,  -88, -229,  -11,    3,    4,    0,    0,    0,
    0,    0, -224,   -4,   -2, -121,    0,  -88,    1, -237,
  -35,    0,  -88,    2,    0,    0,  -88,  -63,    0,  -88,
    6,    0,    0,    0,    0,    0,  -62, -200,    0, -204,
    0,   62,   12,   31,   32,   88,   88,   88,   77,   73,
   15,   37,    0,    0,    0,   88,   88,   88,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  502,  -88,   88,
   88,   38,  508,  535,  545,    0,  609,    0,    0,  703,
  -88,   88,  850,  -18,  161,   88,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88, -193,   88,
   21,  344,  354,   88,    0,    0,    0,    0,    0,   19,
   41,  818,   40,    0,  -37,  -37,  -37,  -37,  850,  850,
  -37,  -37,  -18,  -18,  -45,  -45,  757,    0,  785,    0,
  -36,  -34,   45,   50,   34,   88,    0,    0,    0,    0,
    0,   88,    0,  818,  -33,  -14,  384, -167,    0,    0,
  -29,    0,    5,    0,
};
final static short yyrindex[] = {                         0,
    0,   97,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   55,    0,    0,    0,    0,   57,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -24,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   24,    0,    0,
    0,  -25,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  791,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   96,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   60,  -27,  250,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   60,    0,    0,    0,    0,    0,    0,
    0,  -26,   63,    0,  361,  418,  441,  447,   -8,  480,
  453,  476,  406,  412,  103,  132,    0,    0,    0,    0,
    0,    0,    0,    0,  812,    0,    0,    0,    0,    0,
    0,    0,    0,  -19,    0,    0,    0,   43,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,    0,    0,  -17,    0,    0,    0,   75,
    0,   82,    0,    0,    0, -110,    0,    0,    0,    0,
    0,    0,    0,    0,   -5,    0,  848,    0,
};
final static int YYTABLESIZE=1129;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         66,
  109,   10,   23,   34,  106,  104,   68,  105,  109,  107,
   36,   67,   11,   68,   77,   41,   68,   77,   66,   43,
   14,   78,   45,  106,   78,   68,   12,  109,  107,   13,
   67,   68,   66,   68,   15,   66,    3,   66,  155,  156,
    4,    5,   25,   29,   68,  108,   28,   30,   31,   67,
   66,  163,   66,  108,   32,   33,   30,   40,   37,   44,
   42,  111,   49,   30,   46,   68,   50,   53,   30,   79,
   80,   81,  108,  120,   91,   46,   92,  114,  138,  140,
  144,  145,   46,  146,   66,  151,  149,   46,  150,  152,
  161,  158,  153,  162,   66,   10,    1,    9,   25,   26,
   76,   68,    0,   75,   39,   66,   67,   35,  143,   66,
  159,    0,   68,    0,    0,    0,   68,   67,    0,    0,
   66,   67,    0,    0,    0,    0,    0,   68,    0,  164,
    0,   89,   67,    0,    0,   86,   55,   55,   55,   55,
   55,   55,   55,   58,   58,   58,   58,   58,   30,   58,
   25,    0,    0,    0,   55,   55,   55,   55,    0,    0,
    0,   58,   58,   58,   58,    0,    0,   46,    0,    0,
    0,    0,   59,   59,   59,   59,   59,    0,   59,   19,
   20,   21,    0,   22,    0,    0,   55,    0,   55,    0,
   59,   59,   59,   59,    0,   58,    0,    0,    0,    0,
    0,  124,  106,  104,    0,  105,  109,  107,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  102,    0,  103,   54,   59,   55,   56,   57,   58,   59,
   60,   61,    0,    0,    0,    0,    0,    0,   62,   63,
   64,   65,   54,    0,   55,   56,   57,   58,   59,   60,
   61,  108,   68,   68,    0,    0,    0,   62,   63,   64,
   65,   54,    0,   55,   56,   57,   58,   59,   60,   61,
    0,   66,   66,    0,    0,    0,   62,   63,   64,   65,
   30,    0,   30,   30,   30,   30,   30,   30,   30,    0,
   74,    0,   74,   74,   74,   30,   30,   30,   30,   46,
    0,   46,   46,   46,   46,   46,   46,   46,   74,   74,
   74,   74,    0,    0,   46,   46,   46,   46,   54,    0,
   55,   56,   57,   58,   59,   60,   61,    0,    0,    0,
    0,    0,    0,   62,   63,   64,   65,   61,   88,    0,
    0,   61,   74,    0,   82,   63,   64,   65,   82,   63,
   64,   65,   61,    0,    0,    0,    0,    0,    0,   82,
   63,   64,   65,    0,    0,    0,    0,    0,    0,    0,
    0,   55,   55,   55,   55,   55,   55,    0,   58,   58,
   58,   58,   58,   58,  141,  106,  104,    0,  105,  109,
  107,    0,    0,    0,  142,  106,  104,    0,  105,  109,
  107,   63,    0,  102,   63,  103,    0,   59,   59,   59,
   59,   59,   59,  102,    0,  103,    0,    0,    0,   63,
   63,   63,   63,    0,  160,  106,  104,    0,  105,  109,
  107,    0,    0,    0,  108,    0,   96,   97,   98,   99,
  100,  101,    0,  102,  108,  103,   56,    0,   56,   56,
   56,    0,   57,   63,   57,   57,   57,    0,   62,    0,
    0,   62,    0,    0,   56,   56,   56,   56,    0,    0,
   57,   57,   57,   57,  108,    0,   62,   62,   62,   62,
    0,   64,    0,    0,   64,    0,    0,   65,    0,    0,
   65,    0,    0,   60,    0,    0,   60,    0,   56,   64,
   64,   64,   64,    0,   57,   65,   65,   65,   65,    0,
   62,   60,   60,   60,   60,    0,   61,    0,    0,   61,
   67,    0,    0,   67,    0,   74,   74,   74,   74,   74,
   74,    0,    0,   64,   61,   61,   61,   61,   67,   65,
   67,    0,    0,  106,  104,   60,  105,  109,  107,  106,
  104,    0,  105,  109,  107,    0,    0,    0,    0,    0,
    0,  102,  110,  103,    0,    0,  115,  102,   61,  103,
    0,    0,   67,    0,    0,    0,  106,  104,    0,  105,
  109,  107,    0,    0,    0,    0,  106,  104,    0,  105,
  109,  107,  108,  116,  102,    0,  103,    0,  108,    0,
    0,    0,    0,  117,  102,    0,  103,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   96,
   97,   98,   99,  100,  101,  108,    0,    0,    0,   96,
   97,   98,   99,  100,  101,  108,   63,   63,   63,   63,
   63,   63,    0,    0,    0,    0,    0,    0,    0,    0,
  106,  104,    0,  105,  109,  107,    0,    0,    0,   96,
   97,   98,   99,  100,  101,    0,    0,  118,  102,    0,
  103,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   56,   56,   56,   56,   56,   56,   57,   57,   57,
   57,   57,   57,   62,   62,   62,   62,   62,   62,  108,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   64,   64,   64,   64,
   64,   64,   65,   65,   65,   65,   65,   65,   60,   60,
   60,   60,   60,   60,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  106,  104,    0,  105,  109,  107,
    0,   61,   61,   61,   61,   61,   61,    0,    0,   67,
   67,  119,  102,    0,  103,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   96,   97,   98,
   99,  100,  101,   96,   97,   98,   99,  100,  101,    0,
    0,    0,    0,  108,    0,    0,    0,    0,  106,  104,
    0,  105,  109,  107,    0,    0,    0,    0,    0,    0,
   96,   97,   98,   99,  100,  101,  102,    0,  103,    0,
   96,   97,   98,   99,  100,  101,  106,  104,    0,  105,
  109,  107,   55,   55,    0,   55,   55,   55,    0,    0,
    0,    0,    0,  148,  102,    0,  103,  108,    0,  147,
   55,   55,   55,   39,   39,    0,   39,   39,   39,  106,
  104,    0,  105,  109,  107,    0,    0,    0,    0,    0,
    0,   39,   39,   39,    0,  108,    0,  102,    0,  103,
    0,   55,    0,    0,   96,   97,   98,   99,  100,  101,
    0,  106,  104,    0,  105,  109,  107,    0,    0,    0,
    0,    0,   39,   83,   84,   85,   87,   90,  108,  102,
    0,  103,    0,   93,   94,   95,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  112,  113,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  122,
  108,    0,    0,  125,  126,  127,  128,  129,  130,  131,
  132,  133,  134,  135,  136,  137,    0,  139,    0,    0,
    0,  122,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   96,   97,
   98,   99,  100,  101,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  154,    0,    0,    0,    0,    0,  157,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   96,   97,   98,   99,  100,  101,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   96,   97,   98,   99,  100,  101,   55,   55,   55,   55,
   55,   55,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   39,   39,   39,
   39,   39,   39,   96,   97,   98,   99,  100,  101,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   96,   97,   98,   99,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         33,
   46,  272,   91,  125,   42,   43,   40,   45,   46,   47,
   28,   45,  272,   41,   41,   33,   44,   44,   33,   37,
  123,   41,   40,   42,   44,   40,   40,   46,   47,   58,
   45,   59,   41,   61,  272,   44,  267,   33,  149,  150,
  271,  272,  272,   41,   40,   91,   58,   44,  273,   45,
   59,  162,   61,   91,   59,   58,   33,   93,   58,  123,
   59,   79,  125,   40,   59,   93,  267,  272,   45,   58,
   40,   40,   91,   91,   60,   33,   40,   40,  272,   59,
   62,   41,   40,   44,   93,   41,  123,   45,  123,   40,
  258,  125,   59,  123,   33,   41,    0,   41,  123,  125,
   41,   40,   -1,   41,   30,   33,   45,   26,  114,   33,
  125,   -1,   40,   -1,   -1,   -1,   40,   45,   -1,   -1,
   33,   45,   -1,   -1,   -1,   -1,   -1,   40,   -1,  125,
   -1,   59,   45,   -1,   -1,   59,   41,   42,   43,   44,
   45,   46,   47,   41,   42,   43,   44,   45,  125,   47,
  272,   -1,   -1,   -1,   59,   60,   61,   62,   -1,   -1,
   -1,   59,   60,   61,   62,   -1,   -1,  125,   -1,   -1,
   -1,   -1,   41,   42,   43,   44,   45,   -1,   47,  268,
  269,  270,   -1,  272,   -1,   -1,   91,   -1,   93,   -1,
   59,   60,   61,   62,   -1,   93,   -1,   -1,   -1,   -1,
   -1,   41,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   60,   -1,   62,  257,   93,  259,  260,  261,  262,  263,
  264,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,  273,
  274,  275,  257,   -1,  259,  260,  261,  262,  263,  264,
  265,   91,  280,  281,   -1,   -1,   -1,  272,  273,  274,
  275,  257,   -1,  259,  260,  261,  262,  263,  264,  265,
   -1,  280,  281,   -1,   -1,   -1,  272,  273,  274,  275,
  257,   -1,  259,  260,  261,  262,  263,  264,  265,   -1,
   41,   -1,   43,   44,   45,  272,  273,  274,  275,  257,
   -1,  259,  260,  261,  262,  263,  264,  265,   59,   60,
   61,   62,   -1,   -1,  272,  273,  274,  275,  257,   -1,
  259,  260,  261,  262,  263,  264,  265,   -1,   -1,   -1,
   -1,   -1,   -1,  272,  273,  274,  275,  265,  266,   -1,
   -1,  265,   93,   -1,  272,  273,  274,  275,  272,  273,
  274,  275,  265,   -1,   -1,   -1,   -1,   -1,   -1,  272,
  273,  274,  275,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,   -1,  276,  277,
  278,  279,  280,  281,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   41,   42,   43,   -1,   45,   46,
   47,   41,   -1,   60,   44,   62,   -1,  276,  277,  278,
  279,  280,  281,   60,   -1,   62,   -1,   -1,   -1,   59,
   60,   61,   62,   -1,   41,   42,   43,   -1,   45,   46,
   47,   -1,   -1,   -1,   91,   -1,  276,  277,  278,  279,
  280,  281,   -1,   60,   91,   62,   41,   -1,   43,   44,
   45,   -1,   41,   93,   43,   44,   45,   -1,   41,   -1,
   -1,   44,   -1,   -1,   59,   60,   61,   62,   -1,   -1,
   59,   60,   61,   62,   91,   -1,   59,   60,   61,   62,
   -1,   41,   -1,   -1,   44,   -1,   -1,   41,   -1,   -1,
   44,   -1,   -1,   41,   -1,   -1,   44,   -1,   93,   59,
   60,   61,   62,   -1,   93,   59,   60,   61,   62,   -1,
   93,   59,   60,   61,   62,   -1,   41,   -1,   -1,   44,
   41,   -1,   -1,   44,   -1,  276,  277,  278,  279,  280,
  281,   -1,   -1,   93,   59,   60,   61,   62,   59,   93,
   61,   -1,   -1,   42,   43,   93,   45,   46,   47,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   61,   62,   -1,   -1,   59,   60,   93,   62,
   -1,   -1,   93,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   -1,   -1,   -1,   -1,   42,   43,   -1,   45,
   46,   47,   91,   59,   60,   -1,   62,   -1,   91,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,   91,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,   91,  276,  277,  278,  279,
  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,  276,
  277,  278,  279,  280,  281,   -1,   -1,   59,   60,   -1,
   62,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  276,  277,  278,  279,  280,  281,  276,  277,  278,
  279,  280,  281,  276,  277,  278,  279,  280,  281,   91,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,
  280,  281,  276,  277,  278,  279,  280,  281,  276,  277,
  278,  279,  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   42,   43,   -1,   45,   46,   47,
   -1,  276,  277,  278,  279,  280,  281,   -1,   -1,  280,
  281,   59,   60,   -1,   62,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  276,  277,  278,  279,  280,  281,   -1,
   -1,   -1,   -1,   91,   -1,   -1,   -1,   -1,   42,   43,
   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,   60,   -1,   62,   -1,
  276,  277,  278,  279,  280,  281,   42,   43,   -1,   45,
   46,   47,   42,   43,   -1,   45,   46,   47,   -1,   -1,
   -1,   -1,   -1,   59,   60,   -1,   62,   91,   -1,   93,
   60,   61,   62,   42,   43,   -1,   45,   46,   47,   42,
   43,   -1,   45,   46,   47,   -1,   -1,   -1,   -1,   -1,
   -1,   60,   61,   62,   -1,   91,   -1,   60,   -1,   62,
   -1,   91,   -1,   -1,  276,  277,  278,  279,  280,  281,
   -1,   42,   43,   -1,   45,   46,   47,   -1,   -1,   -1,
   -1,   -1,   91,   56,   57,   58,   59,   60,   91,   60,
   -1,   62,   -1,   66,   67,   68,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   80,   81,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   92,
   91,   -1,   -1,   96,   97,   98,   99,  100,  101,  102,
  103,  104,  105,  106,  107,  108,   -1,  110,   -1,   -1,
   -1,  114,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,
  278,  279,  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,  146,   -1,   -1,   -1,   -1,   -1,  152,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  276,  277,  278,  279,  280,  281,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  276,  277,  278,  279,  280,  281,  276,  277,  278,  279,
  280,  281,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  276,  277,  278,
  279,  280,  281,  276,  277,  278,  279,  280,  281,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  276,  277,  278,  279,
};
}
final static short YYFINAL=1;
final static short YYMAXTOKEN=281;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",
"','","'-'","'.'","'/'",null,null,null,null,null,null,null,null,null,null,"':'",
"';'","'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,"'['",null,"']'",null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,"'{'",null,"'}'",null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,"IF","ELSE","WHILE","READ","PRINT",
"PRINTSP","PRINTLN","RETURN","CAST","NULL","VAR","ENTERO","REAL","CHAR",
"STRUCT","ID","LITERAL_ENTERO","LITERAL_REAL","LITERAL_CHAR","MAYOR_IGUAL",
"MENOR_IGUAL","IGUAL_QUE","DISTINTO","AND","OR",
};
final static String yyrule[] = {
"$accept : programa",
"programa : definiciones",
"definiciones : definiciones definicion",
"definiciones :",
"definicion : definicionVariable",
"definicion : definicionStruct",
"definicion : definicionFuncion",
"definicionVariable : VAR ID ':' tipo ';'",
"definicionVariableLocal : VAR ID ':' tipo ';'",
"definicionesParametros : definicionParametros",
"definicionesParametros :",
"definicionParametros : definicionParametro",
"definicionParametros : definicionParametros ',' definicionParametro",
"definicionParametro : ID ':' tipo",
"tipo : ENTERO",
"tipo : REAL",
"tipo : CHAR",
"tipo : ID",
"tipo : '[' LITERAL_ENTERO ']' tipo",
"definicionStruct : STRUCT ID '{' definicionCampos '}' ';'",
"definicionCampos : definicionCampo",
"definicionCampos : definicionCampos definicionCampo",
"definicionCampo : ID ':' tipo ';'",
"definicionFuncion : ID '(' definicionesParametros ')' definicionReturn '{' cuerpo '}'",
"definicionReturn : ':' tipo",
"definicionReturn :",
"cuerpo : definicionVariablesFuncion sentencias",
"definicionVariablesFuncion : definicionVariablesFuncion definicionVariableLocal",
"definicionVariablesFuncion :",
"sentencias : sentencias sentencia",
"sentencias :",
"sentencia : asignacion",
"sentencia : escritura",
"sentencia : lectura",
"sentencia : condicional",
"sentencia : iterativa",
"sentencia : retorno",
"sentencia : invocacionProcedimiento",
"invocacionProcedimiento : ID '(' argumentos ')' ';'",
"invocacionFuncion : ID '(' argumentos ')'",
"asignacion : expresion '=' expresion ';'",
"escritura : PRINT expresion ';'",
"escritura : PRINTSP expresion ';'",
"escritura : PRINTLN expresion ';'",
"escritura : PRINTLN ';'",
"lectura : READ expresion ';'",
"condicional : IF '(' expresion ')' '{' sentencias '}'",
"condicional : IF '(' expresion ')' '{' sentencias '}' ELSE '{' sentencias '}'",
"iterativa : WHILE '(' expresion ')' '{' sentencias '}'",
"retorno : RETURN expresion ';'",
"retorno : RETURN ';'",
"retorno : RETURN NULL",
"expresion : LITERAL_ENTERO",
"expresion : LITERAL_REAL",
"expresion : LITERAL_CHAR",
"expresion : ID",
"expresion : expresion '+' expresion",
"expresion : expresion '-' expresion",
"expresion : expresion '*' expresion",
"expresion : expresion '/' expresion",
"expresion : expresion '<' expresion",
"expresion : expresion '>' expresion",
"expresion : expresion MENOR_IGUAL expresion",
"expresion : expresion MAYOR_IGUAL expresion",
"expresion : expresion IGUAL_QUE expresion",
"expresion : expresion DISTINTO expresion",
"expresion : expresion AND expresion",
"expresion : expresion OR expresion",
"expresion : '!' expresion",
"expresion : '(' expresion ')'",
"expresion : invocacionFuncion",
"expresion : CAST '<' tipo '>' '(' expresion ')'",
"expresion : expresion '[' expresion ']'",
"expresion : expresion '.' ID",
"expresion : '-' expresion",
"argumentos : argumento",
"argumentos :",
"argumento : expresion",
"argumento : argumento ',' expresion",
};

//#line 175 "sintac.y"
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
//#line 585 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 1:
//#line 30 "sintac.y"
{ raiz = new Programa(val_peek(0));}
break;
case 2:
//#line 33 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 3:
//#line 34 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 4:
//#line 37 "sintac.y"
{ yyval = val_peek(0); }
break;
case 5:
//#line 38 "sintac.y"
{ yyval = val_peek(0); }
break;
case 6:
//#line 39 "sintac.y"
{ yyval = val_peek(0); }
break;
case 7:
//#line 44 "sintac.y"
{ yyval = new DefVariable(val_peek(3), val_peek(1),"Global"); }
break;
case 8:
//#line 47 "sintac.y"
{ yyval = new DefVariable(val_peek(3), val_peek(1),"Local"); }
break;
case 9:
//#line 50 "sintac.y"
{ yyval = val_peek(0);}
break;
case 10:
//#line 51 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 11:
//#line 54 "sintac.y"
{ yyval = new ArrayList();((List)yyval).add(val_peek(0));}
break;
case 12:
//#line 55 "sintac.y"
{ yyval = val_peek(2); ((List)val_peek(2)).add(val_peek(0));}
break;
case 13:
//#line 58 "sintac.y"
{ yyval = new DefVariable(val_peek(2), val_peek(0),"Parametro"); }
break;
case 14:
//#line 61 "sintac.y"
{ yyval = new TipoEntero(); }
break;
case 15:
//#line 62 "sintac.y"
{ yyval = new TipoReal(); }
break;
case 16:
//#line 63 "sintac.y"
{ yyval = new TipoChar(); }
break;
case 17:
//#line 64 "sintac.y"
{ yyval = new TipoIdent(val_peek(0)); }
break;
case 18:
//#line 65 "sintac.y"
{ yyval = new TipoArray(new LiteralEntero(val_peek(2)),val_peek(0)); }
break;
case 19:
//#line 70 "sintac.y"
{ yyval = new DefEstructura(val_peek(4),val_peek(2)); }
break;
case 20:
//#line 73 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 21:
//#line 74 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 22:
//#line 77 "sintac.y"
{ yyval = new DefCampo(val_peek(3),val_peek(1)); }
break;
case 23:
//#line 82 "sintac.y"
{ yyval = new DefFuncion(val_peek(7),val_peek(5),val_peek(3),val_peek(1)); }
break;
case 24:
//#line 85 "sintac.y"
{ yyval = new DefReturn(val_peek(0)); }
break;
case 25:
//#line 86 "sintac.y"
{ yyval = new DefReturn(null); }
break;
case 26:
//#line 89 "sintac.y"
{ yyval = new Cuerpo(val_peek(1),val_peek(0));}
break;
case 27:
//#line 92 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0));}
break;
case 28:
//#line 93 "sintac.y"
{ yyval = new ArrayList();}
break;
case 29:
//#line 96 "sintac.y"
{ yyval = val_peek(1); ((List)val_peek(1)).add(val_peek(0)); }
break;
case 30:
//#line 97 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 31:
//#line 100 "sintac.y"
{ yyval = val_peek(0); }
break;
case 32:
//#line 101 "sintac.y"
{ yyval = val_peek(0); }
break;
case 33:
//#line 102 "sintac.y"
{ yyval = val_peek(0); }
break;
case 34:
//#line 103 "sintac.y"
{ yyval = val_peek(0); }
break;
case 35:
//#line 104 "sintac.y"
{ yyval = val_peek(0); }
break;
case 36:
//#line 105 "sintac.y"
{ yyval = val_peek(0); }
break;
case 37:
//#line 106 "sintac.y"
{ yyval = val_peek(0); }
break;
case 38:
//#line 110 "sintac.y"
{ yyval = new InvocacionProcedimiento(val_peek(4),val_peek(2)); }
break;
case 39:
//#line 114 "sintac.y"
{ yyval = new InvocacionFuncion(val_peek(3),val_peek(1)); }
break;
case 40:
//#line 117 "sintac.y"
{ yyval = new Asignacion(val_peek(3),val_peek(1)); }
break;
case 41:
//#line 120 "sintac.y"
{ yyval = new Print(val_peek(1)); }
break;
case 42:
//#line 121 "sintac.y"
{ yyval = new PrintSP(val_peek(1)); }
break;
case 43:
//#line 122 "sintac.y"
{ yyval = new PrintLN(val_peek(1)); }
break;
case 44:
//#line 123 "sintac.y"
{ yyval = new PrintLN(null).setPositions(val_peek(1)); }
break;
case 45:
//#line 126 "sintac.y"
{ yyval = new Read(val_peek(1)); }
break;
case 46:
//#line 129 "sintac.y"
{ yyval = new IfElse(val_peek(4),val_peek(1),new ArrayList()); }
break;
case 47:
//#line 130 "sintac.y"
{ yyval = new IfElse(val_peek(8),val_peek(5),val_peek(1)); }
break;
case 48:
//#line 133 "sintac.y"
{ yyval = new While(val_peek(4),val_peek(1)); }
break;
case 49:
//#line 136 "sintac.y"
{ yyval = new Return(val_peek(1)); }
break;
case 50:
//#line 137 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(1)); }
break;
case 51:
//#line 138 "sintac.y"
{ yyval = new Return(null).setPositions(val_peek(1)); }
break;
case 52:
//#line 141 "sintac.y"
{ yyval = new LiteralEntero(val_peek(0)); }
break;
case 53:
//#line 142 "sintac.y"
{ yyval = new LiteralReal(val_peek(0)); }
break;
case 54:
//#line 143 "sintac.y"
{ yyval = new LiteralChar(val_peek(0)); }
break;
case 55:
//#line 144 "sintac.y"
{ yyval = new Variable(val_peek(0)); }
break;
case 56:
//#line 145 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2),"+",val_peek(0)); }
break;
case 57:
//#line 146 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2),"-",val_peek(0)); }
break;
case 58:
//#line 147 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2),"*",val_peek(0)); }
break;
case 59:
//#line 148 "sintac.y"
{ yyval = new ExpresionAritmetica(val_peek(2),"/",val_peek(0)); }
break;
case 60:
//#line 149 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"<",val_peek(0)); }
break;
case 61:
//#line 150 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),">",val_peek(0)); }
break;
case 62:
//#line 151 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"<=",val_peek(0)); }
break;
case 63:
//#line 152 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),">=",val_peek(0)); }
break;
case 64:
//#line 153 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"==",val_peek(0)); }
break;
case 65:
//#line 154 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"!=",val_peek(0)); }
break;
case 66:
//#line 155 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"&&",val_peek(0)); }
break;
case 67:
//#line 156 "sintac.y"
{ yyval = new ExpresionComparacion(val_peek(2),"||",val_peek(0)); }
break;
case 68:
//#line 157 "sintac.y"
{ yyval = new Negacion(val_peek(0)); }
break;
case 69:
//#line 158 "sintac.y"
{ yyval = val_peek(1); }
break;
case 70:
//#line 159 "sintac.y"
{ yyval = val_peek(0); }
break;
case 71:
//#line 160 "sintac.y"
{ yyval = new Cast(val_peek(4),val_peek(1)); }
break;
case 72:
//#line 161 "sintac.y"
{ yyval = new AccesoArray(val_peek(3),val_peek(1)); }
break;
case 73:
//#line 162 "sintac.y"
{ yyval = new AccesoCampo(val_peek(2),val_peek(0)); }
break;
case 74:
//#line 163 "sintac.y"
{ yyval = new MenosUnario(val_peek(0)); }
break;
case 75:
//#line 166 "sintac.y"
{ yyval = val_peek(0); }
break;
case 76:
//#line 167 "sintac.y"
{ yyval = new ArrayList(); }
break;
case 77:
//#line 170 "sintac.y"
{ yyval = new ArrayList(); ((List)yyval).add(val_peek(0)); }
break;
case 78:
//#line 171 "sintac.y"
{ yyval = val_peek(2); ((List)val_peek(2)).add(val_peek(0)); }
break;
//#line 1045 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
