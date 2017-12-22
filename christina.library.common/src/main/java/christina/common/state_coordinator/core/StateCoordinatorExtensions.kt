package christina.common.state_coordinator.core

operator fun <Id : Any, State : Any> StateCoordinator<Id, *, State>.set(
    id: Id,
    state: State
) = setState(id, state)

operator fun <Id : Any, State : Any> StateCoordinator<Id, *, State>.get(
    id: Id
) = getState(id)