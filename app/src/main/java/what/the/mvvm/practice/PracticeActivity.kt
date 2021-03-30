package what.the.mvvm.practice

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import what.the.mvvm.databinding.ActivityPracticeBinding

class PracticeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            expandable.setOnExpandListener {
                if (it) {
                    toast("expanded")
                    expandable.spinnerColor = Color.YELLOW
                } else {
                    toast("collapse")
                }
            }

            expandable.parentLayout.setOnClickListener {
                if (expandable.isExpanded) expandable.collapse()
                else expandable.expand()
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}