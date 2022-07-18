package ru.hivislav.nasaexplorer.view.planets.earth

interface ItemTouchHelperAdapter {
    fun onItemMove (fromPosition: Int, toPosition: Int)
    fun onItemDismiss (position: Int)
}

interface ItemTouchHelperViewHolder {
    fun onItemSelect()
    fun onItemClear()
}