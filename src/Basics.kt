import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

object First {

    @JvmStatic
    fun main(args: Array<String>) {

        GlobalScope.launch {
            delay(1000L)
            println("world")
        }

        print("hello ")
        Thread.sleep(2000L)
    }

}

object Second {

    @JvmStatic
    fun main(args: Array<String>) {

        GlobalScope.launch {
            delay(1000L)
            println("world")
        }

        print("hello ")
        runBlocking {
            //the main thread is blocked, in order to complete the execution of this coroutine
            delay(2000L)
        }

        //Like the First one, but here the main thread waits the second coroutine to complete before the shut down of the JVM
    }

}

object Third {

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {
        //launch the main coroutine

        GlobalScope.launch {
            //launching another coroutine from the main
            delay(1000L)
            println("world")
        }

        print("hello ")
        delay(2000L)

    }

}

object Fourth {

    //we don't want to wait a certain time in order to complete a coroutine
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {

        val job = GlobalScope.launch {
            delay(1000L)
            println("world")
        }

        print("hello ")
        job.join() //before shutting the jvm, we explicitly wait the completion of the coroutine in execution
        //this method can be called only from a coroutine

        //this approach is much more faster

    }

}

object Fifth {

    //Launching coroutines in a specific scope (the one given by runBlocking in this case)
    //instead of launching a coroutine in the globalScope (the threads are always global).

    //This allows us to avoid the call of the join() coroutine method.
    //The outer coroutine does not complete until the launched coroutine in its scope complete
    @JvmStatic
    fun main(args: Array<String>) = runBlocking {

        launch {
            delay(1000L)
            println("world")
        }

        print("hello ")
    }

}
