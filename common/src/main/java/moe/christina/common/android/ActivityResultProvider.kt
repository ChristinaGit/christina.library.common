package moe.christina.common.android

import io.reactivex.Observable
import moe.christina.common.android.event.ActivityResultEvent

interface ActivityResultProvider {
    val onActivityResult: Observable<ActivityResultEvent>
}

