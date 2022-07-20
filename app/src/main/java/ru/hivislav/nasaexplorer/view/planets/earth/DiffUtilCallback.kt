package ru.hivislav.nasaexplorer.view.planets.earth

import androidx.recyclerview.widget.DiffUtil
import ru.hivislav.nasaexplorer.model.entities.PlanetsData

class DiffUtilCallback(
    private var oldItems: List<Pair<PlanetsData, Boolean>>,
    private var newItems: List<Pair<PlanetsData, Boolean>>
): DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.id == newItems[newItemPosition].first.id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].first.planetName == newItems[newItemPosition].first.planetName
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any {
        val old = oldItems[oldItemPosition]
        val new = newItems[newItemPosition]

        return Change(old, new)
    }
}

data class Change<T> (val oldData: T, val newData: T)

fun <T> createCombinedPayloads (payloads: List<Change<T>>) : Change<T> {
    assert(payloads.isNotEmpty())
    val firstChange = payloads.first()
    val lastChange = payloads.last()

    return Change(firstChange.oldData, lastChange.newData)
}