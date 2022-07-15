package ru.hivislav.nasaexplorer.view.planets.earth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.hivislav.nasaexplorer.databinding.FragmentEarthBinding
import ru.hivislav.nasaexplorer.model.entities.PlanetsData
import ru.hivislav.nasaexplorer.model.entities.TYPE_EARTH
import ru.hivislav.nasaexplorer.model.entities.TYPE_HEADER
import ru.hivislav.nasaexplorer.model.entities.TYPE_MARS


class EarthFragment : Fragment() {

    private var _binding: FragmentEarthBinding? = null
    private val binding get() = _binding!!

    val data = arrayListOf(
        Pair(PlanetsData("Заголовок", planetType = TYPE_HEADER), false),
        Pair(PlanetsData("Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData("Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData("Mars", planetType = TYPE_MARS), false),
        Pair(PlanetsData("Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData("Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData("Earth", planetType = TYPE_EARTH), false),
        Pair(PlanetsData("Mars", planetType = TYPE_MARS), false)
    )

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
    }

    private val callbackAdd = AddItem {
        data.add(it, Pair(PlanetsData("Mars(new)", planetType = TYPE_MARS), false))
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