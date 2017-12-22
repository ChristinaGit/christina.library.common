package christina.common.state_coordinator.core

import christina.common.core.accessor.MutableAccessor

interface StateCoordinator<in Id : Any, Target : Any, State : Any> {
    fun setStateAccessor(stateAccessor: MutableAccessor<Target, State>)
    fun setStateAccessor(id: Id, stateAccessor: MutableAccessor<Target, State>?)
    fun getStateAccessor(id: Id): MutableAccessor<Target, State>

    fun add(id: Id, target: Target)
    fun remove(id: Id)

    fun getState(id: Id): State
    fun setState(id: Id, state: State)
    fun setState(state: State)

    fun invalidate(id: Id)
    fun invalidate()
}