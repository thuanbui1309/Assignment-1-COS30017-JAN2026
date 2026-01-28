package com.example.assignment_1_cos30017_jan2026

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.activity.viewModels
import com.example.assignment_1_cos30017_jan2026.databinding.ActivityMainBinding
import com.example.assignment_1_cos30017_jan2026.viewmodel.GymnasticsViewModel
import com.example.assignment_1_cos30017_jan2026.model.Zone

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: GymnasticsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.state.observe(this) { state ->
            // Update UI based on state
            binding.tvScore.text = state.score.toString()
            binding.tvElement.text = "${state.currentElement} / 10"
            updateProgressBar(state.currentElement)
            updateScoreColor(viewModel.getCurrentZone())
        }

        binding.btnPerform.setOnClickListener {
            Log.d("GymnasticsApp", "Perform button clicked")
            viewModel.perform()
        }

        binding.btnDeduction.setOnClickListener {
            Log.d("GymnasticsApp", "Deduction button clicked")
            viewModel.deduct()
        }

        binding.btnReset.setOnClickListener {
            Log.d("GymnasticsApp", "Reset button clicked")
            viewModel.reset()
        }

    }

    /**
     * Updates the progress bar segments based on current element.
     * Colors segments according to their zone (Basic, Intermediate, Advanced).
     */
    private fun updateProgressBar(currentElement: Int) {
        val segments = listOf(
            binding.seg1, binding.seg2, binding.seg3,
            binding.seg4, binding.seg5, binding.seg6, binding.seg7,
            binding.seg8, binding.seg9, binding.seg10
        )

        segments.forEachIndexed { index, segment ->
            val elementNumber = index + 1
            val color = when {
                elementNumber > currentElement -> R.color.zone_inactive
                elementNumber <= 3 -> R.color.zone_basic
                elementNumber <= 7 -> R.color.zone_intermediate
                else -> R.color.zone_advanced
            }
            segment.setBackgroundColor(getColor(color))
        }
    }

    /**
     * Updates the score text color based on the current zone.
     */
    private fun updateScoreColor(zone: Zone) {
        val color = when (zone) {
            Zone.BASIC -> R.color.zone_basic
            Zone.INTERMEDIATE -> R.color.zone_intermediate
            Zone.ADVANCED -> R.color.zone_advanced
        }
        binding.tvScore.setTextColor(getColor(color))
    }
}