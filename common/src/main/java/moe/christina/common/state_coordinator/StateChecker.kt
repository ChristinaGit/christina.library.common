package moe.christina.common.state_coordinator

@FunctionalInterface
interface StateChecker<in T, in TState> {
    fun checkState(target: T, state: TState): Boolean
}