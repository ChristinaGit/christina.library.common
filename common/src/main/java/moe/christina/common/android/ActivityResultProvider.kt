package moe.christina.common.android

import moe.christina.common.android.event.ActivityResultEventData
import moe.christina.common.event.Event

interface ActivityResultProvider {
    val onActivityResult: Event<ActivityResultEventData>
}

