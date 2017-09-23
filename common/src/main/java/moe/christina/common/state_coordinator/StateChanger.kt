package moe.christina.common.state_coordinator

@FunctionalInterface
interface StateChanger<in T, in TState> {
    fun changeState(target: T, state: TState)
}