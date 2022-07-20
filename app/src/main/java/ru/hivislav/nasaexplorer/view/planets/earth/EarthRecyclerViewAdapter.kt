package ru.hivislav.nasaexplorer.view.planets.earth

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.RecyclerEarthLinearHolderBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerHeaderLinearHolderBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerMarsLinearHolderBinding
import ru.hivislav.nasaexplorer.model.entities.PlanetsData
import ru.hivislav.nasaexplorer.model.entities.TYPE_EARTH
import ru.hivislav.nasaexplorer.model.entities.TYPE_MARS

class EarthRecyclerViewAdapter(
    val callbackAdd: AddItem, val callbackRemove: RemoveItem
) : RecyclerView.Adapter<EarthRecyclerViewAdapter.BaseHolder>(), ItemTouchHelperAdapter {

    private var listPlanetsData: MutableList<Pair<PlanetsData, Boolean>> = mutableListOf()

    fun setListDataForDiffUtil(listPlanetsDataNew: MutableList<Pair<PlanetsData, Boolean>>) {
        val diff = DiffUtil.calculateDiff(DiffUtilCallback(listPlanetsData, listPlanetsDataNew))
        diff.dispatchUpdatesTo(this)
        listPlanetsData = listPlanetsDataNew
    }

    fun setPlanetsData(data: MutableList<Pair<PlanetsData, Boolean>>) {
        listPlanetsData = data
    }

    fun setListDataRemove(listDataNew: MutableList<Pair<PlanetsData, Boolean>>,position: Int){
        listPlanetsData = listDataNew
        notifyItemRemoved(position)
    }

    fun setListDataAdd(listDataNew: MutableList<Pair<PlanetsData, Boolean>>,position: Int){
        listPlanetsData = listDataNew
        notifyItemInserted(position)
    }

    override fun getItemViewType(position: Int): Int {
        return listPlanetsData[position].first.planetType
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

    override fun onBindViewHolder(
        holder: BaseHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            super.onBindViewHolder(holder, position, payloads)
        } else {
            val createCombinedPayloads = createCombinedPayloads(payloads as List<Change<Pair<PlanetsData, Boolean>>>)
            if (createCombinedPayloads.newData.first.planetName != createCombinedPayloads.oldData.first.planetName)
            holder.itemView.findViewById<TextView>(R.id.planetNameTitle).text = createCombinedPayloads.newData.first.planetName
        }
    }

    override fun getItemCount(): Int {
        return listPlanetsData.size
    }

    abstract class BaseHolder(view: View) : RecyclerView.ViewHolder(view), ItemTouchHelperViewHolder {
        abstract fun bind(data : Pair<PlanetsData, Boolean>)
        override fun onItemSelect() {
            itemView.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.light_red))
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }
    }

    inner class HeaderViewHolder(private val binding: RecyclerHeaderLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(data : Pair<PlanetsData, Boolean>) {
            binding.planetNameTitle.text = data.first.planetName
        }
    }

    inner class MarsViewHolder(private val binding: RecyclerMarsLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(data : Pair<PlanetsData, Boolean>) {
            binding.planetNameTitle.text = data.first.planetName

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

            binding.marsDescriptionTextView.visibility =
                if (listPlanetsData[layoutPosition].second) View.VISIBLE else View.GONE

            binding.marsImageView.setOnClickListener {
                listPlanetsData[layoutPosition] = listPlanetsData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }
    }

    inner class EarthViewHolder(private val binding: RecyclerEarthLinearHolderBinding)
        : BaseHolder(binding.root) {
        override fun bind(data : Pair<PlanetsData, Boolean>) {
            binding.planetNameTitle.text = data.first.planetName

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

            binding.earthDescriptionTextView.visibility =
                if (listPlanetsData[layoutPosition].second) View.VISIBLE else View.GONE

            binding.earthImageView.setOnClickListener {
                listPlanetsData[layoutPosition] = listPlanetsData[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }
        }
    }

    override fun onItemMove(fromPosition: Int, toPosition: Int) {
        listPlanetsData.removeAt(fromPosition).apply {
            listPlanetsData.add(toPosition, this)
        }
        notifyItemMoved(fromPosition, toPosition)
    }

    override fun onItemDismiss(position: Int) {
        callbackRemove.remove(position)
    }
}

