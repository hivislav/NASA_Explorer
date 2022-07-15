package ru.hivislav.nasaexplorer.view.planets.earth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import kotlinx.android.synthetic.main.recycler_mars_grid_holder.view.*
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.RecyclerEarthLinearHolderBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerHeaderLinearHolderBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerMarsGridHolderBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerMarsLinearHolderBinding
import ru.hivislav.nasaexplorer.model.entities.*

class EarthRecyclerViewAdapter() : RecyclerView.Adapter<EarthRecyclerViewAdapter.BaseHolder>() {

    private var listPlanetsData: List<PlanetsData> = listOf()

    fun setPlanetsData(data: List<PlanetsData>) {
        listPlanetsData = data
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return listPlanetsData[position].planetType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseHolder {
        return when (viewType) {
            TYPE_EARTH -> {
                val binding = RecyclerEarthLinearHolderBinding.inflate(LayoutInflater.from(parent.context))
                EarthViewHolder(binding)
            }
            TYPE_MARS -> {
                val binding = RecyclerMarsLinearHolderBinding.inflate(LayoutInflater.from(parent.context))
                MarsViewHolder(binding)
            }
            else -> {
                val binding = RecyclerHeaderLinearHolderBinding.inflate(LayoutInflater.from(parent.context))
                HeaderViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: BaseHolder, position: Int) {
        holder.bind(listPlanetsData[position])
    }

    override fun getItemCount(): Int {
        return listPlanetsData.size
    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view) {
        abstract fun bind(planetsData : PlanetsData)
    }

    inner class HeaderViewHolder(private val binding: RecyclerHeaderLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(planetsData: PlanetsData) {
            binding.headerNameTitle.text = planetsData.planetName
        }
    }

    inner class MarsViewHolder(private val binding: RecyclerMarsLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(planetsData: PlanetsData) {
            binding.marsNameTitle.text = planetsData.planetName
        }
    }

    inner class EarthViewHolder(private val binding: RecyclerEarthLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(planetsData: PlanetsData) {
            binding.earthNameTitle.text = planetsData.planetName
        }
    }
}

