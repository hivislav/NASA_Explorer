package ru.hivislav.nasaexplorer.view.coordinator

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

class TextToolbarBehavior(context: Context, attrs: AttributeSet? = null) :
    CoordinatorLayout.Behavior<TextView>(context, attrs) {

    override fun layoutDependsOn(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        return dependency is AppBarLayout
    }

    override fun onDependentViewChanged(
        parent: CoordinatorLayout,
        child: TextView,
        dependency: View
    ): Boolean {
        val screenSize = dependency.height
        if (dependency is AppBarLayout) {
            child.alpha = 1 - (abs(dependency.y) /(dependency.height/2))
        }
        return super.onDependentViewChanged(parent, child, dependency)
    }
}