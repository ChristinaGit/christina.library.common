package christina.common.state_coordinator.basic

import android.util.SparseArray
import christina.common.state_coordinator.StateChanger
import christina.common.state_coordinator.StateChecker
import christina.common.state_coordinator.StateCoordinator
import collections.forEach

class BasicStateCoordinator<TTarget, TState>
@JvmOverloads
constructor(
    override var defaultStateChanger: StateChanger<TTarget, TState>? = null,
    override var stateChecker: StateChecker<TTarget, TState>? = null
) : StateCoordinator<TTarget, TState> {
    override fun setStateChanger(id: Int, stateChanger: StateChanger<TTarget, TState>?) {
        stateChangers.append(id, stateChanger)
    }

    override fun getStateChanger(id: Int): StateChanger<TTarget, TState>? = stateChangers[id] ?: defaultStateChanger

    override fun add(id: Int, target: TTarget) {
        targets.append(id, target)

        invalidate(id)
    }

    override fun remove(id: Int) {
        targets.remove(id)
    }

    override fun getState(id: Int): TState = states[id]

    override fun setState(id: Int, state: TState) {
        val currentState = getState(id)
        if (state != currentState) {
            states.append(id, state)

            invalidate(id)
        }
    }

    override fun setState(state: TState) {
        targets.forEach { id, _ -> setState(id, state) }
    }

    override fun invalidate(id: Int) {
        val target = targets[id]
        val state = getState(id)

        if (target !== null && state !== null) {
            invalidate(id, target, state)
        }
    }

    override fun invalidate() {
        targets.forEach { id, _ -> invalidate(id) }
    }

    private fun invalidate(id: Int, target: TTarget, state: TState) {
        if (!isActualState(target, state)) {
            performChangeState(id, target, state)
        }
    }

    private fun performChangeState(id: Int, target: TTarget, state: TState) {
        getStateChanger(id)?.changeState(target, state)
    }

    private fun isActualState(target: TTarget, state: TState): Boolean = stateChecker?.checkState(target, state) ?: false

    private val targets = SparseArray<TTarget?>()
    private val states = SparseArray<TState>()
    private val stateChangers = SparseArray<StateChanger<TTarget, TState>>()
}