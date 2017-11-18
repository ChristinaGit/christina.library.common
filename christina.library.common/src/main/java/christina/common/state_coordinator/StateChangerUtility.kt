package christina.common.state_coordinator

fun <TTarget, TState> stateChanger(changeState: (TTarget, TState) -> Unit): StateChanger<TTarget, TState> =
    object : StateChanger<TTarget, TState> {
        override fun changeState(target: TTarget, state: TState): Unit = changeState(target, state)
    }
