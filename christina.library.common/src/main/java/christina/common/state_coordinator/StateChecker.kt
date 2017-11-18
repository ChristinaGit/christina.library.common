package christina.common.state_coordinator

@FunctionalInterface
interface StateChecker<in TTarget, in TState> {
    fun checkState(target: TTarget, state: TState): Boolean
}