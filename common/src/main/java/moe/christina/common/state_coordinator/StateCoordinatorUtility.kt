package moe.christina.common.state_coordinator

fun <T, TState> stateChanger(lambda: (T, TState) -> Unit): StateChanger<T, TState> =
    object : StateChanger<T, TState> {
        override fun changeState(target: T, state: TState): Unit = lambda(target, state)
    }

fun <T, TState> stateChecker(lambda: (T, TState) -> Boolean): StateChecker<T, TState> =
    object : StateChecker<T, TState> {
        override fun checkState(target: T, state: TState): Boolean = lambda(target, state)
    }