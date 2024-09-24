package io.github.tledkov.hikelists

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.github.tledkov.hikelists.databinding.ActivityMainBinding
import io.github.tledkov.hikelists.ui.inventory.InventoryViewModel
import io.github.tledkov.hikelists.ui.inventory.InventoryViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: InventoryViewModel by viewModels {
        InventoryViewModelFactory(
            application,
            (application as App).categoryRepository,
            (application as App).inventoryItemRepository,
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)

        navView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _: NavController,
                                                        destination: NavDestination,
                                                        _: Bundle? ->

            when (destination.id) {
                R.id.navigation_lists -> binding.navView.visibility = View.VISIBLE
                R.id.navigation_inventory -> binding.navView.visibility = View.VISIBLE
                R.id.navigation_settings -> binding.navView.visibility = View.VISIBLE
                else -> binding.navView.visibility = View.GONE
            }
        }
    }
}