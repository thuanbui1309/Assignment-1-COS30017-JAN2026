package com.example.assignment_1_cos30017_jan2026

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.example.assignment_1_cos30017_jan2026.databinding.ActivityMainBinding
import com.example.assignment_1_cos30017_jan2026.viewmodel.GymnasticsViewModel
import com.example.assignment_1_cos30017_jan2026.model.Zone
import com.example.assignment_1_cos30017_jan2026.utils.SoundManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: GymnasticsViewModel by viewModels()
    private var currentDialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bind ViewModel state to UI updates
        viewModel.state.observe(this) { state ->
            binding.tvScore.text = state.score.toString()
            binding.tvElement.text = "${state.currentElement} / 10"
            updateProgressBar(state.currentElement)
            updateScoreColor(viewModel.getCurrentZone())

            // Manage button availability
            val hasProgress = state.currentElement >= 1
            binding.btnDeduction.isEnabled = hasProgress
            binding.btnReset.isEnabled = hasProgress

            // Handle dialog restoration for finished/deducted states
            if (state.isFinished) {
                 showCompletionDialog(state.score, isSuccess = true)
            } else if (state.hasDeducted) {
                 showCompletionDialog(state.score, isSuccess = false)
            } else {
                currentDialog?.dismiss()
                currentDialog = null
            }
        }

        // Subscribe to events for side effects (sounds, dialogs)
        viewModel.event.observe(this) { event ->
            event?.let { handleFeedbackEvent(it) }
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

    // Process one-off feedback events from ViewModel
    private fun handleFeedbackEvent(event: GymnasticsViewModel.FeedbackEvent) {
        when (event) {
            is GymnasticsViewModel.FeedbackEvent.PerformSuccess -> {
                SoundManager.playPerformSound(this)
            }
            is GymnasticsViewModel.FeedbackEvent.Deduction -> {
                SoundManager.playDeductionSound(this)
            }
            is GymnasticsViewModel.FeedbackEvent.RoutineComplete -> {
                showCompletionDialog(event.finalScore, isSuccess = true)
                SoundManager.playFinishSound(this)
            }
            is GymnasticsViewModel.FeedbackEvent.RoutineEndedEarly -> {
                showCompletionDialog(event.finalScore, isSuccess = false)
                SoundManager.playDeductionSound(this)
            }
            is GymnasticsViewModel.FeedbackEvent.Reset -> {} // No action needed
        }
        viewModel.eventHandled()
    }

    // Display the completion or failure dialog
    private fun showCompletionDialog(score: Int, isSuccess: Boolean) {
        if (currentDialog?.isShowing == true) return

        val dialogView = layoutInflater.inflate(R.layout.dialog_completion, null)
        val builder = AlertDialog.Builder(this).apply { setView(dialogView) }

        val dialog = builder.create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        currentDialog = dialog

        val imgIconBg = dialogView.findViewById<android.widget.ImageView>(R.id.imgDialogIconBg)
        val tvTitle = dialogView.findViewById<android.widget.TextView>(R.id.tvDialogTitle)
        val tvScore = dialogView.findViewById<android.widget.TextView>(R.id.tvDialogScore)
        val btnRestart = dialogView.findViewById<android.widget.Button>(R.id.btnDialogRestart)

        if (isSuccess) {
            imgIconBg.setBackgroundResource(R.drawable.bg_circle_success)
            tvTitle.text = getString(R.string.dialog_title_success)
        } else {
            imgIconBg.setBackgroundResource(R.drawable.bg_circle_failure)
            tvTitle.text = getString(R.string.dialog_title_failure)
        }
        
        tvScore.text = score.toString()

        btnRestart.setOnClickListener {
            viewModel.reset()
            dialog.dismiss()
            currentDialog = null
        }

        dialog.setCancelable(false)
        dialog.show()
    }

    // Update progress bar segment colors based on current element index
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

    // Reflect current zone difficulty in score text color
    private fun updateScoreColor(zone: Zone) {
        val color = when (zone) {
            Zone.BASIC -> R.color.zone_basic
            Zone.INTERMEDIATE -> R.color.zone_intermediate
            Zone.ADVANCED -> R.color.zone_advanced
        }
        binding.tvScore.setTextColor(getColor(color))
    }
}