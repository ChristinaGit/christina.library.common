package christina.common.state_coordinator.basic

import christina.common.state_coordinator.StateCoordinatorTests

class BasicStateCoordinatorTests
    : StateCoordinatorTests({
    BasicStateCoordinator(StateCoordinatorTests.defaultStateAccessor)
})