package christina.common.state_coordinator

fun <TTarget, TState> stateChecker(checkState: (TTarget, TState) -> Boolean): StateChecker<TTarget, TState> =
    object : StateChecker<TTarget, TState> {
        override fun checkState(target: TTarget, state: TState): Boolean = checkState(target, state)
    }