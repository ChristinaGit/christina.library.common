package christina.common.state_coordinator

interface StateCoordinator<TTarget, TState> {
    var defaultStateChanger: StateChanger<TTarget, TState>?
    var stateChecker: StateChecker<TTarget, TState>?

    fun setStateChanger(id: Int, stateChanger: StateChanger<TTarget, TState>?)
    fun getStateChanger(id: Int): StateChanger<TTarget, TState>?

    fun add(id: Int, target: TTarget)
    fun remove(id: Int)

    fun getState(id: Int): TState
    fun setState(id: Int, state: TState)
    fun setState(state: TState)

    fun invalidate(id: Int)
    fun invalidate()
}