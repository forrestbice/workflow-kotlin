package com.squareup.workflow1.ui

import android.content.Context
import android.view.View
import android.view.ViewGroup
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import kotlin.reflect.KClass

@OptIn(WorkflowUiExperimentalApi::class)
fun <R : Any> ViewRegistry.buildView(rendering: R): View =
  buildView(rendering, ViewEnvironment(mapOf(ViewRegistry to this)), mock<Context>())

@OptIn(WorkflowUiExperimentalApi::class)
class TestViewFactory<R : Any>(override val type: KClass<R>) : ViewFactory<R> {
  var called = false

  override fun buildView(
    initialRendering: R,
    initialViewEnvironment: ViewEnvironment,
    contextForNewView: Context,
    container: ViewGroup?
  ): View {
    called = true
    return mock {
      on {
        getTag(eq(com.squareup.workflow1.ui.R.id.view_show_rendering_function))
      } doReturn (ShowRenderingTag(initialRendering, initialViewEnvironment, { _, _ -> }))
    }
  }
}
