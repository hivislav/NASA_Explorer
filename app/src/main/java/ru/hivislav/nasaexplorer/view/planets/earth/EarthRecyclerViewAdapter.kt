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

class EarthRecyclerViewAdapter(
    val callbackAdd: AddItem, val callbackRemove: RemoveItem
) : RecyclerView.Adapter<EarthRecyclerViewAdapter.BaseHolder>() {

    private var listPlanetsData: MutableList<PlanetsData> = mutableListOf()

    fun setPlanetsData(data: MutableList<PlanetsData>) {
        listPlanetsData = data
        notifyDataSetChanged()
    }

    fun setListDataRemove(listDataNew: MutableList<PlanetsData>,position: Int){
        listPlanetsData = listDataNew
        notifyItemRemoved(position)
    }

    fun setListDataAdd(listDataNew: MutableList<PlanetsData>,position: Int){
        listPlanetsData = listDataNew
        notifyItemInserted(position)
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

            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }

            if (layoutPosition > 1) {
                binding.moveItemUp.setOnClickListener {
                    listPlanetsData.removeAt(layoutPosition).apply {
                        listPlanetsData.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }

            if (layoutPosition != listPlanetsData.lastIndex) {
                binding.moveItemDown.setOnClickListener {
                    listPlanetsData.removeAt(layoutPosition).apply {
                        listPlanetsData.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }
    }

    inner class EarthViewHolder(private val binding: RecyclerEarthLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(planetsData: PlanetsData) {
            binding.earthNameTitle.text = planetsData.planetName

            binding.addItemImageView.setOnClickListener {
                callbackAdd.add(layoutPosition)
            }

            binding.removeItemImageView.setOnClickListener {
                callbackRemove.remove(layoutPosition)
            }

            binding.moveItemUp.setOnClickListener {
                if (layoutPosition > 1) {
                    listPlanetsData.removeAt(layoutPosition).apply {
                        listPlanetsData.add(layoutPosition - 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition - 1)
                }
            }

            binding.moveItemDown.setOnClickListener {
                if (layoutPosition != listPlanetsData.lastIndex) {
                    listPlanetsData.removeAt(layoutPosition).apply {
                        listPlanetsData.add(layoutPosition + 1, this)
                    }
                    notifyItemMoved(layoutPosition, layoutPosition + 1)
                }
            }
        }
    }
}

