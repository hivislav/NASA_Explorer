package ru.hivislav.nasaexplorer.view.planets

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.size.Scale
import ru.hivislav.nasaexplorer.R
import ru.hivislav.nasaexplorer.model.entities.MarsPhotoDTO

class MarsRecyclerViewAdapter:RecyclerView.Adapter<MarsRecyclerViewAdapter.MarsHolder>() {

    private var marsPhotoData: List<MarsPhotoDTO> = listOf()

    fun setMarsData(data: List<MarsPhotoDTO>) {
        marsPhotoData = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MarsHolder {
        val context: Context = parent.context
        val inflater: LayoutInflater = LayoutInflater.from(context)
        val view: View = inflater.inflate(R.layout.recycler_mars_holder, parent, false)
        return MarsHolder(view)
    }

    override fun onBindViewHolder(holder: MarsHolder, position: Int) {
        holder.bind(marsPhotoData[position])
    }

    override fun getItemCount(): Int {
        return marsPhotoData.size
    }

    inner class MarsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(marsPhoto : MarsPhotoDTO) {
            itemView.apply {
                findViewById<ImageView>(R.id.photoRecyclerMarsHolder).load(marsPhoto.imgSrc) {
                    placeholder(R.drawable.bg_mars)
                }
                findViewById<TextView>(R.id.earthDateRecyclerMarsHolder).text = marsPhoto.earthDate
            }
        }
    }
}

