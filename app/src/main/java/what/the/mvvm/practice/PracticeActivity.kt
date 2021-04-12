package what.the.mvvm.practice

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import what.the.mvvm.R
import what.the.mvvm.databinding.ActivityPracticeBinding
import what.the.mvvm.util.CustomEditTextWrapper
import what.the.mvvm.util.ToggleAnimation

class PracticeActivity : AppCompatActivity() {

    var isExpanded = false

    private fun toggleLayout(isExpanded: Boolean, view: View, layoutExpand: LinearLayout): Boolean {
        ToggleAnimation.toggleArrow(view, isExpanded)
        if (isExpanded) {
            ToggleAnimation.expand(layoutExpand)
        } else {
            ToggleAnimation.collapse(layoutExpand)
        }
        return isExpanded
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ///////////////////////////////////
        /////   Custom Edit Text    ///////
        ///////////////////////////////////

        binding.customEditText.bindConfig(
            CustomEditTextWrapper.Config(
                hintText = "비밀번호",
                focusedUnderlineColor = ContextCompat.getColor(this, R.color.khaki_8),
                unfocusedUnderlineColor = ContextCompat.getColor(this, R.color.login_gray),
                focusedFloatingLabelColor = ContextCompat.getColor(this, R.color.login_gray),
                unfocusedHintColor = ContextCompat.getColor(this, R.color.login_gray),
                textColor = ContextCompat.getColor(this, R.color.black),
                inputTypes = mutableListOf(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
            )
        )

        binding.customEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // nothing
            }
            override fun afterTextChanged(s: Editable?) {
                // nothing
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val msg = "s : $s\nstart : $start\nbefore : $before\ncount : $count"
                Toast.makeText(this@PracticeActivity, msg, Toast.LENGTH_LONG).show()
            }
        })


        ///////////////////////////////////
        /////   Expandable layout   ///////
        ///////////////////////////////////

        binding.imgMore1.setOnClickListener {
            if (binding.layoutExpand1.visibility == View.VISIBLE) {
                binding.layoutExpand1.visibility = View.GONE
                binding.imgMore1.animate().setDuration(200).rotation(180f)
            } else {
                binding.layoutExpand1.visibility = View.VISIBLE
                binding.imgMore1.animate().setDuration(200).rotation(0f)
            }
        }

        binding.parent2.setOnClickListener {
            val show = toggleLayout(!isExpanded, binding.imgMore2, binding.layoutExpand2)
            isExpanded = show
        }


        val sampleText = "<img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736482113_detail1-1.png\" alt=\"image\" style=\"max-width: 100%;\"><img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736487014_detail1-2.png\" alt=\"image\" style=\"max-width: 100%;\"><img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736494844_detail1-3.png\" alt=\"image\" style=\"max-width: 100%;\"><img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736502606_detail1-4.png\" alt=\"image\" style=\"max-width: 100%;\"><img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736511190_detail1-5.png\" alt=\"image\" style=\"max-width: 100%;\"><img src=\"https://sfn-static-resources.s3.ap-northeast-2.amazonaws.com/public/20210326/1616736517680_detail1-6.png\" alt=\"image\" style=\"max-width: 100%;\"><br>"

        // skydoves:expandablelayout 사용 부분
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

            webview.loadData(sampleText, "text/html;", "UTF-8")
            webview.settings.loadWithOverviewMode = true
            webview.settings.useWideViewPort = true
            webview.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                    url?.let { view?.loadUrl(it) }
                    return true
                }
            }
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}