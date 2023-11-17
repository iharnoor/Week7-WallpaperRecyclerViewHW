import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

fun simpleFlow(): Flow<Int> = flow {
    // retrofit call of network request
    for (i in 1..5) {
        delay(1000) // Simulate some asynchronous operation
        emit(i) // Emit values one by one
    }
}

fun main() {
    test()
    Thread.sleep(6000) // todo withContext in future
}

fun test() {
    println("Hello1") // UI
    GlobalScope.launch {
        val flow = simpleFlow() // network call that returns flow

        println("Start collecting flow")

        flow.collect { value ->
            println("Received: $value")
        }

        println("Flow collection completed")
    }
    println("Hello2") // UI
}