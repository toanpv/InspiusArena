package vn.toanpv.sample.arena.core.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.util.*

fun EditText.addSubmittedText(
    delayMillis: Long = 400, minLength: Int = 3, input: (String) -> Unit
) {
    var timer = Timer()
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            if (editable != null && editable.count() >= minLength) {
                val newtInput = editable.toString()
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        input.invoke(newtInput)
                    }
                }, delayMillis)
            }
        }

        override fun beforeTextChanged(cs: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(cs: CharSequence?, start: Int, before: Int, count: Int) {}
    })
}