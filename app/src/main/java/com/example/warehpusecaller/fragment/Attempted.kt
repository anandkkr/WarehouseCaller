package com.example.warehpusecaller.fragment

import android.os.Bundle
import android.os.FileUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.warehpusecaller.R


class Attempted : Fragment() {

    /*lateinit var layoutManager: LinearLayoutManager
    lateinit var layoutManager2: GridLayoutManager
    lateinit var adapterNameCategoryRV: AdapterNameCategoryRV
    lateinit var adapterNameBrandRV: AdapterNameBrandsRV
    var categoryList: List<Category> = ArrayList()
    var brandList: List<Brands> = ArrayList()*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_attempted, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.setOnTouchListener { _, _ -> return@setOnTouchListener true }

        /*categoryList = com.rdsp_direct.onlineshoppingkotlin.utils.FileUtils.categoryList
        brandList = com.rdsp_direct.onlineshoppingkotlin.utils.FileUtils.brandList

        rv_category.isNestedScrollingEnabled = true

        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        layoutManager2 = GridLayoutManager(context, 2)

        rv_category.layoutManager = layoutManager

        adapterNameCategoryRV = AdapterNameCategoryRV(requireContext(), categoryList)
        adapterNameBrandRV = AdapterNameBrandsRV(requireContext(), brandList)

        rv_category.adapter = adapterNameCategoryRV

        iv_close_filter.setOnClickListener {

            activity?.supportFragmentManager?.popBackStack()

        }
*/
    }
}