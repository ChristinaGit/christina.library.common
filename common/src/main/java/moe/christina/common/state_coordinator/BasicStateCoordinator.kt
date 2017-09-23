package moe.christina.common.state_coordinator

import android.util.SparseArray
import collections.forEach

class BasicStateCoordinator<T, TState>
@JvmOverloads
constructor(
    override var defaultStateChanger: StateChanger<T, TState>? = null,
    override var stateChecker: StateChecker<T, TState>? = null
) : StateCoordinator<T, TState> {
    override fun setStateChanger(id: Int, stateChanger: StateChanger<T, TState>?) {
        stateChangers.append(id, stateChanger)
    }

    override fun getStateChanger(id: Int): StateChanger<T, TState>? = stateChangers[id] ?: defaultStateChanger

    override fun add(id: Int, target: T) {
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

    private fun invalidate(id: Int, target: T, state: TState) {
        if (!isActualState(target, state)) {
            performChangeState(id, target, state)
        }
    }

    private fun performChangeState(id: Int, target: T, state: TState) {
        getStateChanger(id)?.changeState(target, state)
    }

    private fun isActualState(target: T, state: TState): Boolean = stateChecker?.checkState(target, state) ?: false

    private val targets = SparseArray<T?>()
    private val states = SparseArray<TState>()
    private val stateChangers = SparseArray<StateChanger<T, TState>>()
}