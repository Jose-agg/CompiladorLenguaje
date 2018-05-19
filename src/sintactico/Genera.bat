java -jar jflex.jar lexico.l

yacc -Jsemantic=Object -v sintac.y

del Yylex.java~
