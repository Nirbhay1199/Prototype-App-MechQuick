package com.nirbhay.mechquick

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView

class ListAdapter(private val context: Context, private val items: List<ServiceCenter>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getCount(): Int {
        return items.size
    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val holder: ViewHolder

        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item, parent, false)
            holder = ViewHolder()
            holder.nameTextView = view.findViewById(R.id.name)
            holder.distanceTextView = view.findViewById(R.id.distance)
            holder.ratingTextView = view.findViewById(R.id.rating)
            view.tag = holder
        } else {
            view = convertView
            holder = convertView.tag as ViewHolder
        }

        val serviceCenter = getItem(position) as ServiceCenter
        holder.nameTextView.text = serviceCenter.name
        holder.distanceTextView.text = "Distance: ${serviceCenter.distance} km"
        holder.ratingTextView.text = "Rating: ${serviceCenter.rating}"

        return view
    }

    private class ViewHolder {
        lateinit var nameTextView: TextView
        lateinit var distanceTextView: TextView
        lateinit var ratingTextView: TextView
    }

}