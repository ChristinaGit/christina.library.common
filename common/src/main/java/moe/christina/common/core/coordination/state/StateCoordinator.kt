package moe.christina.common.core.coordination.state

import android.util.SparseArray
import collections.forEach

abstract class StateCoordinator<T, S : Any>
@JvmOverloads
constructor(
    val defaultStateChanger: StateChanger<T, S>? = null
) {
    fun setStateChanger(id: Int, stateChanger: StateChanger<T, S>?) {
        stateChangers.append(id, stateChanger)
    }

    fun getStateChanger(id: Int): StateChanger<T, S>? = stateChangers[id]

    fun add(id: Int, target: T) {
        targets.append(id, target)

        invalidate(id)
    }

    fun remove(id: Int): Unit {
        targets.remove(id)
    }

    fun getState(id: Int): S? = states[id]

    fun setState(id: Int, state: S) {
        val currentState = getState(id)
        if (state != currentState) {
            states.append(id, state)

            invalidate(id)
        }
    }

    fun setState(state: S) {
        targets.forEach { id, _ -> setState(id, state) }
    }

    fun invalidate(id: Int) {
        val target = targets[id]
        val state = getState(id)

        if (target !== null && state !== null) {
            invalidate(id, target, state)
        }
    }

    fun invalidate() {
        targets.forEach { id, _ -> invalidate(id) }
    }

    protected fun invalidate(id: Int, target: T, state: S) {
        if (!isActualState(target, state)) {
            performChangeState(id, target, state)
        }
    }

    protected fun performChangeState(id: Int, target: T, state: S) {
        (stateChangers[id] ?: defaultStateChanger)?.changeState(target, state)
    }

    protected open fun isActualState(target: T, state: S): Boolean = true

    private val targets = SparseArray<T?>()
    private val states = SparseArray<S>()
    private val stateChangers = SparseArray<StateChanger<T, S>>()
}