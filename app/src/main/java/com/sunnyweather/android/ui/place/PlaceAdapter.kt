package com.sunnyweather.android.ui.place

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sunnyweather.android.databinding.PlaceItemBinding
import com.sunnyweather.android.logic.model.Place
import com.sunnyweather.android.ui.weather.WeatherActivity

class PlaceAdapter(private val fragment: PlaceFragment, private val placeList: ArrayList<Place>) :
    RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {
    lateinit var mBinding : PlaceItemBinding

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        mBinding = PlaceItemBinding.inflate(LayoutInflater.from(parent.context))
        val holder = ViewHolder(mBinding.root)
        holder.itemView.setOnClickListener {
            val position = holder.absoluteAdapterPosition
            val place = placeList[position]
            val activity = fragment.activity
            if (activity is WeatherActivity) {
                activity.mBinding.drawerLayout.closeDrawers()
                activity.viewModel.locationLng = place.lon
                activity.viewModel.locationLat = place.lat
                activity.viewModel.placeName = place.name
                activity.refreshWeather()
            } else {
                val intent = Intent(parent.context, WeatherActivity::class.java).
                apply {
                    putExtra("location_lng", place.lon)
                    putExtra("location_lat", place.lat)
                    putExtra("place_name", place.name)
                }
                fragment.startActivity(intent)
                activity?.finish()
            }
            fragment.viewModel.savePlace(place)
        }
        return holder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val place = placeList[position]
        mBinding.placeName.text = place.name
        val address = "${place.country}${place.adm1}"
        mBinding.placeAddress.text = address
    }
    override fun getItemCount() = placeList.size
}