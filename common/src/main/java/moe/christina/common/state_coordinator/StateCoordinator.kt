package moe.christina.common.state_coordinator

interface StateCoordinator<T, TState> {
    var defaultStateChanger: StateChanger<T, TState>?
    var stateChecker: StateChecker<T, TState>?

    fun setStateChanger(id: Int, stateChanger: StateChanger<T, TState>?)
    fun getStateChanger(id: Int): StateChanger<T, TState>?

    fun add(id: Int, target: T)
    fun remove(id: Int)

    fun getState(id: Int): TState
    fun setState(id: Int, state: TState)
    fun setState(state: TState)

    fun invalidate(id: Int)
    fun invalidate()
}