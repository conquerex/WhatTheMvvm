package what.the.mvvm.util

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import what.the.mvvm.R

class CustomEditTextWrapper : RelativeLayout {

    lateinit var floatingLabel: TextView
    lateinit var editText: EditText
    lateinit var clearEditTextButton: ImageView
    lateinit var iconPasswordUnmasked: ImageView
    lateinit var iconPasswordMasked: ImageView
    lateinit var underline: View

    lateinit var veil: View

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context)
    }

    fun init(context: Context) {
        val li = LayoutInflater.from(context)
        val l = li.inflate(R.layout.widget_custom_edit_text_wrapper, this, false)
        addView(l)

        floatingLabel = this.findViewById(R.id.floatingLabel)
        editText = this.findViewById(R.id.editText)
        clearEditTextButton = this.findViewById(R.id.clearEditTextButton)
        iconPasswordUnmasked = this.findViewById(R.id.iconPasswordUnmasked)
        iconPasswordMasked = this.findViewById(R.id.iconPasswordMasked)

        underline = this.findViewById(R.id.underline)
        veil = this.findViewById(R.id.veil)

        clearEditTextButton.setOnClickListener {
            editText.requestFocus()
            editText.setText("")
        }

        iconPasswordUnmasked.setOnClickListener {
            val selectionEnd = editText.selectionStart

            // mask password on click
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            iconPasswordUnmasked.visibility = View.GONE
            iconPasswordMasked.visibility = View.VISIBLE
            editText.setSelection(selectionEnd)
        }

        iconPasswordMasked.setOnClickListener {
            val selectionEnd = editText.selectionStart

            // unmask password on click
            editText.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            iconPasswordMasked.visibility = View.GONE
            iconPasswordUnmasked.visibility = View.VISIBLE
            editText.setSelection(selectionEnd)
        }

        editText.setOnFocusChangeListener { v, hasFocus ->
            // internal logic
            if (hasFocus) {
                animateOnFocus()
                showKeyboard()
            } else {
                animateOnUnfocus()
                hideKeyboard()
            }

            // other callbacks
            onFocusChangeListeners.forEach { it.onFocusChange(v, hasFocus) }
        }

        editText.addTextChangedListener { editable: Editable? ->

        }

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                animateTextChanged(s)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    //================================================================================
    // Access - External
    //================================================================================
    fun backingEditText(): EditText = editText
    fun underline(): View = underline
    fun veil(): View = veil

    //================================================================================
    // Config
    //================================================================================
    class Config(
        val hintText: String,
        val floatingLabelText: String = hintText,
        val focusedUnderlineColor: Int, val unfocusedUnderlineColor: Int,
        val focusedFloatingLabelColor: Int, val unfocusedHintColor: Int,
        val textColor: Int,
        val inputTypes: MutableList<Int> = mutableListOf()
    ) {
        fun mergedInputTypes(): Int {
            var inputType: Int =
                if (inputTypes.size > 0) inputTypes.first() else InputType.TYPE_CLASS_TEXT
            inputTypes.forEach { inputType = inputType or it }
            return inputType
        }

        fun hasInputType(inputType: Int): Boolean {
            val mergedInputTypes = mergedInputTypes()
            if (mergedInputTypes == 0 && inputType == 0) return true
            return mergedInputTypes and inputType > 0
        }
    }

    var config: Config? = null
    fun bindConfig(config: Config?) {
        this.config = config

        this.config?.let { conf ->
            editText.hint = conf.hintText
            editText.setHintTextColor(conf.unfocusedHintColor)
            editText.setTextColor(conf.textColor)

            underline.setBackgroundColor(conf.unfocusedUnderlineColor)

            floatingLabel.setTextColor(conf.focusedFloatingLabelColor)
            floatingLabel.text =
                if (conf.floatingLabelText.isEmpty()) conf.hintText else conf.floatingLabelText

            var inputType: Int =
                if (conf.inputTypes.size > 0) conf.inputTypes.first() else InputType.TYPE_CLASS_TEXT
            conf.inputTypes.forEach { inputType = inputType or it }
            editText.inputType = inputType

            if (conf.hasInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)) {
                iconPasswordMasked.visibility = View.VISIBLE
                iconPasswordUnmasked.visibility = View.GONE
            } else if (conf.hasInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                iconPasswordMasked.visibility = View.GONE
                iconPasswordUnmasked.visibility = View.VISIBLE
            }
        }
    }

    //================================================================================
    // Control - External - enable
    //================================================================================
    var enableInput = true

    fun disableAll() {
        post {
            // already disabled
            if (!enableInput) return@post

            // internal - custom state values
            enableInput = false
            // internal - custom state
            this.config?.let { conf ->
                editText.hint = conf.hintText // hint appears, floating text is hidden
            }

            editText.isClickable = false
            editText.isFocusable = false
            editText.isFocusableInTouchMode = false

            // input type
            editText.inputType = InputType.TYPE_NULL
            // veil
            veil.visibility = View.VISIBLE

            editText.clearFocus()
            hideKeyboard()
        }
    }

    fun enableAll() {
        post {
            // already enabled
            if (enableInput) return@post
            // internal - custom state values
            enableInput = true
            // internal - custom state
            editText.hint = "" // hint disappears, floating text shows

            editText.isClickable = true
            editText.isFocusable = true
            editText.isFocusableInTouchMode = true
            editText.inputType = InputType.TYPE_CLASS_TEXT
            editText.requestFocus()

            // input type
            editText.inputType = InputType.TYPE_NULL
            this.config?.let { conf ->
                var inputType: Int =
                    if (conf.inputTypes.size > 0) conf.inputTypes.first() else InputType.TYPE_CLASS_TEXT
                conf.inputTypes.forEach { inputType = inputType or it }
                editText.inputType = inputType
            }

            // veil
            veil.visibility = View.GONE

            // keyboard
            showKeyboard()
            editText.clearFocus()
            editText.requestFocus()
        }
    }

    fun hideFloatingLabel() {
        floatingLabel.visibility = View.GONE
    }

    //================================================================================
    // Animation - Focus
    //================================================================================
    private fun animateOnFocus() {
        // floating label
        if (editText.text.toString().isEmpty()) {
            // empty => gain focus => animate floating label appear
            floatingLabel.translationY = floatingLabel.height.toFloat()
            floatingLabel.alpha = 0F
            floatingLabel.animate().alpha(1F).translationY(0F)

            editText.hint = ""
        }
        // underline
        underline.setBackgroundColor(config?.focusedUnderlineColor ?: Color.BLACK)
    }

    private fun animateOnUnfocus() {
        // floating label
        if (editText.text.toString().isEmpty()) {
            // empty => lose focus => animate floating label disappear
            floatingLabel.translationY = 0F
            floatingLabel.alpha = 1F
            floatingLabel
                .animate()
                .alpha(0F)
                .translationY(floatingLabel.height.toFloat())
                .start()

            editText.hint = config?.hintText ?: ""
        }
        // underline
        underline.setBackgroundColor(config?.unfocusedUnderlineColor ?: Color.BLACK)
    }

    private fun animateTextChanged(editable: CharSequence?) {
        editText.hasFocus()
        editText.text.toString()
        if (editText.text.toString().isNotEmpty()) {
            if (config?.hasInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD) == true ||
                config?.hasInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD) == true
            ) {
                clearEditTextButton.visibility = View.GONE
            } else {
                // clear text icon: show
                clearEditTextButton.visibility = View.VISIBLE
            }
        } else {
            if (config?.hasInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD) == true) {
                // empty edit text, hide password visibility toggle
                clearEditTextButton.visibility = View.GONE
            } else {
                // clear text icon: hide
                clearEditTextButton.visibility = View.GONE
            }
        }
    }

    //================================================================================
    // Keyboard
    //================================================================================
    fun showKeyboard() {
        editText.requestFocus()
        // keyboard flickers, just use request focus
        // rootView.postDelayed({
        //    val imm =
        //        context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        //    imm?.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
        // }, 200)
    }

    fun hideKeyboard() {
        editText.clearFocus()
        // keyboard flickers, just use clear focus
        // val imm = context?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        // imm?.hideSoftInputFromWindow(editText.windowToken, 0)
    }

    //================================================================================
    // Listener
    //================================================================================
    val onFocusChangeListeners: MutableList<OnFocusChangeListener> = mutableListOf()
    fun addOnFocusChangeListener(fcl: OnFocusChangeListener) {
        onFocusChangeListeners.add(fcl)
    }

    fun addTextChangedListener(textWatcher: TextWatcher) {
        editText.addTextChangedListener(textWatcher)
    }
}