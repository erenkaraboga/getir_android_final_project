package com.getir.core.common.extentions
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

fun <T> Flow<T>.observeInLifecycle(
    lifecycle: Lifecycle,
    action: (T) -> Unit,
    state: Lifecycle.State = Lifecycle.State.STARTED
) {
    flowWithLifecycle(lifecycle, state)
        .onEach { data -> action(data) }
        .launchIn(lifecycle.coroutineScope)
}
