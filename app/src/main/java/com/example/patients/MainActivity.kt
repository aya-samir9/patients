package com.example.patients

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.patients.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var totalAmount = 350000
    private var totalDonors = 799
    private val goalAmount = 500000
    private var isSaved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        binding.backArrow.setOnClickListener {
            onBackPressed()
        }

        binding.saveIcon.setOnClickListener {
            isSaved = !isSaved
            binding.saveIcon.setImageResource(if (isSaved) R.drawable.ic_bookmark else R.drawable.ic_bookmark_border)
            Toast.makeText(this, if (isSaved) "Saved!" else "Removed from Saved!", Toast.LENGTH_SHORT).show()
        }

        updateUI()

        binding.donateButton.setOnClickListener {
            showDonationDialog()
        }
    }

    private fun showDonationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Enter Donation Amount")

        val input = EditText(this)
        input.hint = "Enter amount in USD"
        builder.setView(input)

        builder.setPositiveButton("Donate") { _, _ ->
            val amountStr = input.text.toString()
            if (amountStr.isNotEmpty()) {
                val amount = amountStr.toIntOrNull()
                if (amount != null && amount > 0) {
                    totalAmount += amount
                    totalDonors++
                    updateUI()
                    Toast.makeText(this, "Thank you for your donation!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
                }
            }
        }

        builder.setNegativeButton("Cancel") { dialog, _ -> dialog.cancel() }
        builder.show()
    }

    private fun updateUI() {
        binding.amountTextView.text = "$" + String.format("%,d", totalAmount) + " raised from " + totalDonors + " Donors"

        val percentage = (totalAmount.toFloat() / goalAmount * 100).toInt()
        binding.percentageTextView.text = "${100 - percentage}% (you can still donate)"

        binding.progressBar.progress = percentage
    }
}