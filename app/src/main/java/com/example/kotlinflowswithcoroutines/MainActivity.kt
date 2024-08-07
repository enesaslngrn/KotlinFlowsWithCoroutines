package com.example.kotlinflowswithcoroutines

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.kotlinflowswithcoroutines.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Flow? Livedata? StateFlow? SharedFlow?
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.triggerFlow().collectLatest {
                    binding.tvFlow.text = it
                }
            }
        }
        binding.btnLivedata.setOnClickListener {
            mainViewModel.triggerLiveData()
        }
        binding.btnStateFlow.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }
        binding.btnSharedFlow.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }

        subscribeToObservables()

    }
    private fun subscribeToObservables(){
        mainViewModel.liveData.observe(this, Observer {
            binding.tvLiveData.text = it
        })
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.stateFlow.collectLatest {
                    binding.tvStateFlow.text = it

                }
            }
        }
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.sharedFlow.collectLatest {
                    binding.tvSharedFlow.text = it
                    Snackbar.make(binding.root,it,Snackbar.LENGTH_LONG).show()
                }
            }
        }
    }
}