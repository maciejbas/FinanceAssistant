package com.example.financeassistant.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.financeassistant.R
import com.example.financeassistant.ViewPageAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class BudgetFragment : Fragment(R.layout.fragment_budget) {

    private lateinit var viewPager: ViewPager2
    private lateinit  var tabLayout: TabLayout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewPager = view.findViewById(R.id.view_pager)
        viewPager.adapter = ViewPageAdapter(this, getFragmentList())

        tabLayout = view.findViewById(R.id.tab_layout)
        setupTabLayout()

    }

    private fun getFragmentList() = listOf(
        IncomeFragment(),
        ExpensesFragment(),
        BalanceSheetFragment()
    )

    private fun setupTabLayout(){
        TabLayoutMediator(tabLayout, viewPager){
            tab, position ->
                when(position){
                    0 -> tab.text = "Przychody"
                    1 -> tab.text = "Wydatki"
                    2 -> tab.text = "Bilans"
                }
        }.attach()
    }

}