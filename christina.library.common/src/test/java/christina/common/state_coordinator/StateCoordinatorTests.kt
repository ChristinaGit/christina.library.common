package christina.common.state_coordinator

import christina.common.core.accessor.MutableAccessor
import christina.common.core.accessor.mutableAccessor
import christina.common.state_coordinator.core.StateCoordinator
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

abstract class StateCoordinatorTests(
    private val stateCoordinatorFactory: () -> StateCoordinator<Int, Target, State>
) {
    companion object {
        val defaultStateAccessor: MutableAccessor<Target, State> = mutableAccessor(
            { it.state },
            { target, state -> target.state = state })

        enum class State {
            A,
            B,
            C,
        }

        class Target(var id: Int) {
            var state: State = State.A
        }
    }

    fun generateStateCoordinator() = stateCoordinatorFactory()

    lateinit var stateCoordinator: StateCoordinator<Int, Target, State>

    @Before
    fun setup() {
        stateCoordinator = generateStateCoordinator()
    }

    @Test
    fun `setStateAccessor`() {
        //region Arrange
        val idDefault = 1
        val idNew = 2
        val stateAccessorNew: MutableAccessor<Target, State> = mutableAccessor({ State.C }, { _, _ -> Unit })
        val stateAccessorByDefaultId: MutableAccessor<Target, State>?
        val stateAccessorByNewId: MutableAccessor<Target, State>?
        //endregion

        //region Act
        stateCoordinator.setStateAccessor(idNew, stateAccessorNew)

        stateAccessorByDefaultId = stateCoordinator.getStateAccessor(idDefault)
        stateAccessorByNewId = stateCoordinator.getStateAccessor(idNew)
        //endregion

        //region Assert
        assertEquals(defaultStateAccessor, stateAccessorByDefaultId)
        assertEquals(stateAccessorNew, stateAccessorByNewId)
        //endregion
    }

    @Test
    fun `setState`() {
        //region Arrange
        val originalState = State.B
        val targetState = State.C
        val target = Target(1)
        target.state = originalState
        //endregion

        //region Act
        stateCoordinator.add(target.id, target)
        stateCoordinator.setState(target.id, targetState)
        //endregion

        //region Assert
        assertNotEquals(originalState, targetState)
        assertEquals(target.state, targetState)
        //endregion
    }

    @Test
    fun `setStateAll`() {
        //region Arrange
        val originalState = State.B
        val targetState = State.C
        val targets = (1..25).map(::Target)
        targets.forEach { it.state = originalState }
        //endregion

        //region Act
        targets.forEach { stateCoordinator.add(it.id, it) }
        stateCoordinator.setState(targetState)
        //endregion

        //region Assert
        assertNotEquals(originalState, targetState)
        assertTrue(targets.all { it.state == targetState })
        //endregion
    }

    @Test
    fun `invalidate`() {
        //region Arrange
        val originalState = State.B
        val targetState = State.C
        val target = Target(1)
        //endregion

        //region Act
        target.state = targetState
        stateCoordinator.add(target.id, target)
        target.state = originalState
        stateCoordinator.invalidate(target.id)
        //endregion

        //region Assert
        assertNotEquals(originalState, targetState)
        assertEquals(target.state, targetState)
        //endregion
    }

    @Test
    fun `invalidateAll`() {
        //region Arrange
        val originalState = State.B
        val targetState = State.C
        val targets = (1..25).map(::Target)
        //endregion

        //region Act
        targets.forEach { it.state = targetState }
        targets.forEach { stateCoordinator.add(it.id, it) }
        targets.forEach { it.state = originalState }
        stateCoordinator.invalidate()
        //endregion

        //region Assert
        assertNotEquals(originalState, targetState)
        assertTrue(targets.all { it.state == targetState })
        //endregion
    }
}