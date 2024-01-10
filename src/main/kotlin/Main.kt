import java.lang.StringBuilder
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    // colores texto
    val GREEN = "\u001B[32m"
    val RESET = "\u001B[0m"
    val WHITE = "\u001B[37m"
    val RED = "\u001B[31m"
    val YELLOW = "\u001B[33m"
    val BLUE = "\u001B[34m"
    val PURPLE = "\u001B[35m"
    val CYAN = "\u001B[36m"
    //colores fondo
    val GREEN_BACKGROUND = "\u001B[42m"
    val RED_BACKGROUND = "\u001B[41m"
    val YELLOW_BACKGROUND = "\u001B[43m"
    val BLUE_BACKGROUND = "\u001B[44m"
    val PURPLE_BACKGROUND = "\u001B[45m"
    val CYAN_BACKGROUND = "\u001B[46m"
    val WHITE_BACKGROUND = "\u001B[47m"

    val list = listOf("uno","dos","tres","cuatro","cinco","seis","siete","ocho","nueve","diez")

    val rm = ReproductorMidi("pugnodollari.mid")
    println("Cargando juego...")
    Thread.sleep(5000)

    val sol = list.random()
    var pal = creaPalabra(sol)

    var fallos = 0
    var aciertos = 0

    val listaRespuestas = mutableListOf<Char>()

    do{
        DibujoAhorcado.dibujar(fallos)
        println("Estado de la palabra secreta: $pal")
        //println("DEBUG_SOL: $sol")
        println("Llevas $fallos fallos (máximo número de fallos permitidos 7). Introduce una letra que creas que está en la palabra secreta")

        //Pequeña comprobación para evitar excepciones por entrada vacía
        var r = inputHandler(readln(), false)

        //Si la respuesta está vacía cuenta como fallo y pasa al siguiente turno
        if(r.length<1) {
            fallos++
            continue
        }

        //Se tomará como respuesta el primer caracter, independientemente de la longitud de la entrada
        var res = r[0]

        //Se comprueba que la letra introducida no sea repetida. En caso de serlo será ignorada y se avanzará al siguiente turno
        var rep=false
        for(c in listaRespuestas){
            if(c==res){
                println("¡LETRA REPETIDA!")
                rep=true
                break
            }
        }
        if(rep) continue

        //Se comprueba si la letra introducida existe en la solución. Si no se acierta como mínimo una posición contará como fallo
        var acierto=false
        for(i in 0..sol.length-1){
            if(sol[i]==res){
                if(!acierto){
                    acierto=true
                    listaRespuestas.add(res)
                }
                aciertos++
                var sb = StringBuilder(pal)
                sb.setCharAt(i,res)
                pal=sb.toString()
            }
        }
        if(!acierto)
            fallos++

        //Si se ha completado la palabra el jugador gana el juego y este termina
        if(aciertos==sol.length){
            println("¡Has ganado! ¡El pobre John Ahorcado te estará agradecido de por vida!")
            cerrarJuego()
        }

    }
        while(fallos<7)

    //Como solo se puede salir del bucle cuando se llega al límite de fallos, se muestra aquí el mensaje comunicando al jugador que ha perdido
    DibujoAhorcado.dibujar(7)
    println("¡Que lástima! ¡No has podido salvar al pobre John Ahorcado de un destino fatal! Vuelve a intentarlo en otra vida")
    cerrarJuego()
}


fun creaPalabra(sol:String):String{
    var p=""
    for(i in 1..sol.length){
        p += "*"
    }
    return p
}


//El propósito de esta función es dar una oportunidad extra al jugador en caso de enviar una respuesta vacía

fun inputHandler(input:String, e:Boolean):String{
    if(input.length>0){
        return input[0].toString()
    }
    else if(!e){
        println("¡Cuidado, has enviado una respuesta vacía! Puedes intentarlo de nuevo, pero si vuelve a pasar contará como fallo")
        return inputHandler(readln(),true)
    }
    else{
        return ""
    }
}

//Para evitar un cierre abrupto que corte la música de golpe
fun cerrarJuego(){
    println("Pulsa ENTER para cerrar el juego")
    readln()
    exitProcess(0)
}