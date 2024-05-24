package com.nirbhay.mechquick

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.nirbhay.mechquick.databinding.FragmentSearchBinding
import org.json.JSONObject


class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.search.setOnClickListener {
            binding.listView.visibility = View.VISIBLE
            val listView = binding.listView
            val adapter = ListAdapter(requireContext(), serviceCenter())
            listView.adapter = adapter
            binding.refreshLayout.isRefreshing = false
            val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(requireView().windowToken, 0)
            binding.editText.isCursorVisible = false
        }

        binding.refreshLayout.setOnRefreshListener {
            binding.listView.visibility = View.GONE
            binding.refreshLayout.isRefreshing = false
            binding.editText.text.clear()
        }

        binding.editText.setOnClickListener {
            binding.editText.isCursorVisible = true
        }
    }


    private fun serviceCenter(): MutableList<ServiceCenter> {
        val jsonData = """
{
  "serviceCenters": [
    {"name": "City Auto Repairs", "distance": 2.5, "rating": 4.7},
    {"name": "Quick Fix Garage", "distance": 3.2, "rating": 4.5},
    {"name": "Precision Car Service", "distance": 1.8, "rating": 4.9},
    {"name": "Neighborhood Mechanics", "distance": 4.0, "rating": 4.3},
    {"name": "Express Auto Service", "distance": 2.9, "rating": 4.6},
    {"name": "Reliable Auto Care", "distance": 3.5, "rating": 4.4},
    {"name": "Downtown Service Hub", "distance": 5.1, "rating": 4.2},
    {"name": "Premium Car Maintenance", "distance": 2.2, "rating": 4.8},
    {"name": "All-Star Auto Center", "distance": 4.8, "rating": 4.1},
    {"name": "Trusty Wheels Service", "distance": 1.5, "rating": 4.9}
  ]
}
"""

        val serviceCenters = mutableListOf<ServiceCenter>()
        val jsonObject = JSONObject(jsonData)
        val jsonArray = jsonObject.getJSONArray("serviceCenters")

        val jsonObjectList = mutableListOf<JSONObject>()
        for (i in 0 until jsonArray.length()) {
            jsonObjectList.add(jsonArray.getJSONObject(i))
        }

        jsonObjectList.shuffle()

        for (item in jsonObjectList) {
            val name = item.getString("name")
            val distance = item.getDouble("distance")
            val rating = item.getDouble("rating")
            serviceCenters.add(ServiceCenter(name, distance, rating))
        }

        return serviceCenters
    }
}