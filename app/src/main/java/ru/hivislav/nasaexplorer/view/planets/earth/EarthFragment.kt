package ru.hivislav.nasaexplorer.view.planets.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import ru.hivislav.nasaexplorer.databinding.FragmentEarthBinding
import ru.hivislav.nasaexplorer.model.entities.PlanetsData
import ru.hivislav.nasaexplorer.model.entities.TYPE_EARTH
import ru.hivislav.nasaexplorer.model.entities.TYPE_HEADER
import ru.hivislav.nasaexplorer.model.entities.TYPE_MARS


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    val data = arrayListOf(
        Pair(PlanetsData(0, "Заголовок", planetType = TYPE_HEADER), false),
        Pair(PlanetsData(1, "Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData(2, "Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData(3, "Mars", planetType = TYPE_MARS), false),
        Pair(PlanetsData(4, "Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData(5, "Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData(6, "Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData(7, "Mars", planetType = TYPE_MARS), false)
    )

    private var isNewList = false

    lateinit var adapter: EarthRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        _binding = FragmentEarthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = EarthRecyclerViewAdapter(callbackAdd, callbackRemove)
        binding.earthRecycler.adapter = adapter.apply {
            setPlanetsData(data)
        }

        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(binding.earthRecycler)

        binding.earthRecyclerDiffUtilFAB.setOnClickListener {
            changeAdapterData()
        }
    }

    private fun changeAdapterData() {
        adapter.setListDataForDiffUtil(createItemList(isNewList).map { it }.toMutableList())
        isNewList = !isNewList
    }

    private fun createItemList(instanceNumber: Boolean): List<Pair<PlanetsData, Boolean>> {
        return when (instanceNumber) {
            false -> listOf(
                Pair(PlanetsData(0, "Header", planetType = TYPE_HEADER), false),
                Pair(PlanetsData(1, "Mars", ""), false),
                Pair(PlanetsData(2, "Mars", ""), false),
                Pair(PlanetsData(3, "Mars", ""), false),
                Pair(PlanetsData(4, "Mars", ""), false),
                Pair(PlanetsData(5, "Mars", ""), false),
                Pair(PlanetsData(6, "Mars", ""), false)
            )
            true -> listOf(
                Pair(PlanetsData(0, "Header", planetType = TYPE_HEADER), false),
                Pair(PlanetsData(1, "Mars", ""), false),
                Pair(PlanetsData(2, "Jupiter", ""), false),
                Pair(PlanetsData(3, "Mars", ""), false),
                Pair(PlanetsData(4, "Neptune", ""), false),
                Pair(PlanetsData(5, "Saturn", ""), false),
                Pair(PlanetsData(6, "Mars", ""), false)
            )
        }
    }

    private val callbackAdd = AddItem {
        data.add(it, Pair(PlanetsData(0,"Mars(new)", planetType = TYPE_MARS), false))
        adapter.setListDataAdd(data, it)
    }

    private val callbackRemove = RemoveItem {
        data.removeAt(it)
        adapter.setListDataRemove(data, it)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance() = EarthFragment()
    }
}