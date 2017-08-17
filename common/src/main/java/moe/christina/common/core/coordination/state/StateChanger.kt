package moe.christina.common.core.coordination.state

@FunctionalInterface
interface StateChanger<in T, in S> {
    fun changeState(target: T, state: S)
}