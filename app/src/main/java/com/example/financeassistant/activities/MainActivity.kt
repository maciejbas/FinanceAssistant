package com.example.financeassistant.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.financeassistant.R
import com.example.financeassistant.fragment.BudgetFragment
import com.example.financeassistant.fragment.CalculatorFragment
import com.example.financeassistant.fragment.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
//    private val newIncomeActivityRequestCode = 1
//    private val incomeViewModel: IncomeViewModel by viewModels {
//        IncomeViewModelFactory((application as OperationApplication).repository)
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadFragment(BudgetFragment())

        setNavigationGraph()

        val bottomNavigationView = findViewById<View>(R.id.bottom_navigation) as BottomNavigationView

        bottomNavigationView.setOnItemSelectedListener { item ->
            val fragment: Fragment
            when (item.itemId) {
                R.id.budget_tab -> {
                    fragment = BudgetFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.currency_tab -> {
                    fragment = CalculatorFragment()
                    loadFragment(fragment)
                    true
                }
                R.id.settings_tab -> {
                    fragment = SettingsFragment()
                    loadFragment(fragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setNavigationGraph() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container_view, fragment)
            .commit()
    }
}