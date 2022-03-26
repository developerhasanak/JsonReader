package com.hasan.jsonreader

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hasan.jsonreader.databinding.ActivityMainBinding
import com.hasan.jsonreader.model.CityModel
import com.hasan.jsonreader.util.getJsonDataFromAsset

import java.util.*

class MainActivity : AppCompatActivity(),AdapterView.OnItemSelectedListener {
    lateinit var adapter1: ArrayAdapter<String>
    lateinit var adapter2: ArrayAdapter<String>
    private var spinneril = ArrayList<String>()
    private var spinnerilce = ArrayList<String>()
    private var city: List<CityModel>? = null
   lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding =ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        super.onCreate(savedInstanceState)
        setContentView(view)

        spinnerProcess(view)
    }

    private fun spinnerProcess(view: View) {
        val jsonFileString = getJsonDataFromAsset(view.context,"il_ilce.json")
        val gson = Gson()

        val listCityType = object : TypeToken<List<CityModel>>() {}.type
        city = gson.fromJson(jsonFileString, listCityType)


        city?.let {
            for (i in 0 until city!!.size) {
                spinneril.add(city!![i].il)
            }
        }



        adapter1 =
            ArrayAdapter(view.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item,spinneril)
        adapter1.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner1.adapter = adapter1
       binding.spinner1.onItemSelectedListener = this@MainActivity

        adapter2 =
            ArrayAdapter(view.context, com.google.android.material.R.layout.support_simple_spinner_dropdown_item, spinnerilce)
        adapter2.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)
        binding.spinner2.adapter = adapter2

    }



    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
        spinnerilce.clear()
        val secilenil = p0?.getItemAtPosition(p2)
        try {
            city?.forEach {
                if (secilenil == it.il) {
                    it.ilceleri.forEach { ilce ->
                        spinnerilce.add(ilce)
                    }
                    adapter2.notifyDataSetChanged()
                }
            }

        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {

    }

}