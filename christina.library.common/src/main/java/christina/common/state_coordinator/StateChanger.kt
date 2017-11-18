package christina.common.state_coordinator

@FunctionalInterface
interface StateChanger<in TTarget, in TState> {
    fun changeState(target: TTarget, state: TState)
}