# CompiladorLenguaje #

Este repositorio contiene el diseño, implementación y documentación de un compilador de lenguaje de programación aplicando las técnicas de construcción de procesadores de lenguajes vistas a lo largo de las clases de la asignatura de Diseño de Lenguajes de Programación del grado en Ingeniería Informática del Software impartida en la Universidad de Oviedo.

## Autoria ##

Este proyecto fue desarrollado por José Antonio García García: [MrKarrter](https://github.com/MrKarrter). En su desarrollo se han utilizado cuatro herramientas para agilizar la implementación del mismo: 
- [JFlex](http://www.jflex.de/)
- [Berkeley Yacc](http://byaccj.sourceforge.net/)
- VGen y MAPL. Ambas desarrolladas por el profesor de la asignatura para uso didáctico: Raúl Izquierdo Castanedo.

## Diseño ##

Para facilitar la implementación de este proyecto se ha divido el trabajo en 6 fases: 
- **Análisis Léxico**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/sintactico/lexico.l).
- **Análisis Sintáctico**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/sintactico/sintac.y).
- **Análisis Semántico: Identificación**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/semantico/Identificacion.java).
- **Análisis Semántico: Comprobación de Tipos**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/semantico/ComprobacionDeTipos.java).
- **Generación Código: Gestión de Memoria**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/generacionDeCodigo/GestionDeMemoria.java).
- **Generación Código: Selección de Instrucciones**. Se encuentra implementado en este [fichero](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/src/generacionDeCodigo/SeleccionDeInstrucciones.java).

![FasesTraductor](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/Archivos_Documentacion/Fases%20Traductor.png)

## Lenguaje ##

Las caracteristicas del lenguaje que maneja este compilador se encuentran detalladas en el fichero: [Descripción del Lenguaje](https://github.com/MrKarrter/CompiladorLenguaje/blob/master/Archivos_Documentacion/Descripcion%20del%20Lenguaje.pdf). Los puntos más importantes serian:
 - Trabaja con 3 tipos simples: entero, float y char y 2 tipos compuestos: arrays y estructuras.
 - Las variables globales pueden estar definidas en cualquier parte del fichero y serán visibles únicamente en las funciones definidas posteriormente.
 - Las variables locales estarán definidas al principio de las funciones exclusivamente.
 - No hay conversion implicita al tipo de la izquierda.
 - No se pueden crear ni defincion multiple de variables **(a,b,c = 3)** ni asignacion multiple **( a = b = 0)**.
 - Se pueden realizar conversiones explicitas entre los tipos simples. **cast\<int>(5.6)**
 - La lista de operadores validos es la siguiente: **+ - * / < <= > >= == != &&(and) ||(or) !(not)**
 - Algunas de las sentencias que soporta: condicional (if else), iterativa (while), salida y entrada por consola (print, read), llamada a funciones y/o procedimientos **(a = f())** y **(f())**
 - Como ampliación se ha añadido al asignación multiple de arrays **(a = {444,555,666})**;

Un ejemplo de un programa de este lenguaje seria el siguiente:
```go
// Comentarios de una linea
/* Comentarios
   de más de una linea */

var numeroTelefono:int;
var letraPreferida:char;
var altura:float;
   
struct DiaSemana { 
  posicion:int;
  mnemonico:char;
};

var lunes:DiaSemana;
var telefonosAmigos:[5]int;

funcion(parametro:real):int {
	var variableLocal:int;
	variableLocal = cast<int>(parametro);
	lunes.posicion = 1;
	lunes.mnemonico = 'L';
	return variableLocal;
}

procedimiento(){
	telefonosAmigos[0] = 111;
	telefonosAmigos[1] = 222;
	telefonosAmigos[2] = 333;
}

main(){	
	procedimiento();				//procedimiento		// ampliacion
	print telefonosAmigos[0];		// 111				// 444
	print telefonosAmigos[1];		// 222				// 555
	print telefonosAmigos[2];		// 333				// 666
	print '\n';
	print funcion(123.456);			// 123
	print lunes.posicion;			// 1
	print lunes.mnemonico;			// 'L'
	print '\n';

}
```
