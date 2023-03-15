package com.chami.newsapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.chami.newsapplication.databinding.ActivityMainBinding
import com.chami.newsapplication.presentation.adapter.NewsAdapter
import com.chami.newsapplication.presentation.viewmodel.NewsViewModel
import com.chami.newsapplication.presentation.viewmodel.NewsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: NewsViewModelFactory
    lateinit var viewModel: NewsViewModel

    @Inject
    lateinit var newsAdapter: NewsAdapter
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        val mainView = findViewById<View>(android.R.id.content)
//        mainView.viewTreeObserver.addOnPreDrawListener {
//            return@addOnPreDrawListener false
//        }

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bnvNews.setupWithNavController(navController)

        viewModel = ViewModelProvider(this, factory)[NewsViewModel::class.java]

        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (destination.id == R.id.infoFragment) {
                showBottomNavigationView(type = 8)
            } else {
                showBottomNavigationView(type = 0)
            }
        }
    }

    private fun showBottomNavigationView(type: Int) {
        binding.bnvNews.visibility = type
    }
}