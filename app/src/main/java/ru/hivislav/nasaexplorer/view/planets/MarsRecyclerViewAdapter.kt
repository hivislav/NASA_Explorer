package ru.hivislav.nasaexplorer.view.planets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import coil.load
import kotlinx.android.synthetic.main.fragment_mars.view.*
import kotlinx.android.synthetic.main.recycler_mars_holder.view.*
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.databinding.FragmentMarsBinding
import ru.hivislav.nasaexplorer.databinding.RecyclerMarsHolderBinding
import ru.hivislav.nasaexplorer.model.entities.MarsPhotoDTO

class MarsRecyclerViewAdapter(
    private val onItemViewClickListener: MarsFragment.OnItemViewClickListener?
) : RecyclerView.Adapter<MarsRecyclerViewAdapter.MarsHolder>() {

    private var marsPhotoData: List<MarsPhotoDTO> = listOf()

    fun setMarsData(data: List<MarsPhotoDTO>) {
        marsPhotoData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsHolder {
        val binding = RecyclerMarsHolderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MarsHolder(binding)
    }

    override fun onBindViewHolder(holder: MarsHolder, position: Int) {
        holder.bind(marsPhotoData[position])
    }

    override fun getItemCount(): Int {
        return marsPhotoData.size
    }

    inner class MarsHolder(private val binding: RecyclerMarsHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(marsPhoto : MarsPhotoDTO) {
            itemView.photoRecyclerMarsHolder.setOnClickListener {
                onItemViewClickListener?.onItemViewClick(marsPhoto)
            }

            itemView.apply {
                binding.photoRecyclerMarsHolder.load(marsPhoto.imgSrc) {
                    placeholder(R.drawable.bg_mars)
                }
                binding.earthDateRecyclerMarsHolder.text = marsPhoto.earthDate
            }
        }
    }
}

