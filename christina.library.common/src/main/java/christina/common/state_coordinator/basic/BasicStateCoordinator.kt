package christina.common.state_coordinator.basic

import christina.common.core.accessor.MutableAccessor
import christina.common.exception.reasonableException
import christina.common.state_coordinator.core.StateCoordinator
import christina.common.state_coordinator.core.exception.StateCoordinatorErrorReasons.TARGET_ALREADY_ADDED
import christina.common.state_coordinator.core.exception.StateCoordinatorErrorReasons.TARGET_NOT_FOUND
import christina.common.state_coordinator.core.exception.StateCoordinatorException

class BasicStateCoordinator<in Id : Any, Target : Any, State : Any>(
    private var stateAccessor: MutableAccessor<Target, State>
) : StateCoordinator<Id, Target, State> {
    private val stateAccessors: MutableMap<Id, MutableAccessor<Target, State>> = mutableMapOf()
    private val targets: MutableMap<Id, Target> = mutableMapOf()
    private val states: MutableMap<Id, State> = mutableMapOf()

    override fun setStateAccessor(stateAccessor: MutableAccessor<Target, State>) {
        this.stateAccessor = stateAccessor
    }

    override fun setStateAccessor(id: Id, stateAccessor: MutableAccessor<Target, State>?) {
        if (stateAccessor !== null) {
            stateAccessors[id] = stateAccessor
        } else {
            stateAccessors.remove(id)
        }
    }

    override fun getStateAccessor(id: Id) = stateAccessors[id] ?: stateAccessor

    override fun add(id: Id, target: Target) {
        if (targets.containsKey(id)) {
            throw StateCoordinatorException(
                "Target with id:\"${id}\" is already added.",
                reasonableException(TARGET_ALREADY_ADDED))
        }

        states[id] = getStateAccessor(id).get(target)
        targets[id] = target
    }

    override fun remove(id: Id) {
        targets.remove(id) ?: throw StateCoordinatorException(
            "Target with id:\"$id\" not found.",
            reasonableException(TARGET_NOT_FOUND))
    }

    private fun getTarget(id: Id) =
        targets[id] ?: throw StateCoordinatorException(
            "Target with id:\"$id\" not found.",
            reasonableException(TARGET_NOT_FOUND))

    override fun getState(id: Id): State =
        states[id] ?: throw StateCoordinatorException(
            "Target with id:\"$id\" not found.",
            reasonableException(TARGET_NOT_FOUND))

    override fun setState(id: Id, state: State) {
        states[id] = state

        invalidate(id, getTarget(id), state)
    }

    override fun setState(state: State) {
        for (stateWithId in states) {
            stateWithId.setValue(state)

            val id = stateWithId.key
            invalidate(id, getTarget(id), state)
        }
    }

    override fun invalidate(id: Id) {
        invalidate(id, getTarget(id), getState(id))
    }

    override fun invalidate() {
        for (targetWithId in targets) {
            val id = targetWithId.key
            val target = targetWithId.value
            invalidate(id, target, getState(id))
        }
    }

    private fun invalidate(id: Id, target: Target, state: State) {
        val stateAccessor = getStateAccessor(id)
        if (stateAccessor.get(target) != state) {
            stateAccessor.set(target, state)
        }
    }
}