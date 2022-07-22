package ru.hivislav.nasaexplorer.model.entities

const val TYPE_EARTH= 1
const val TYPE_MARS= 2
const val TYPE_HEADER= 3

data class PlanetsData(
    val id: Int = 0,
    val planetName: String = "Planet",
    val planetDescription: String = "Description",
    val planetType: Int = TYPE_MARS
)
