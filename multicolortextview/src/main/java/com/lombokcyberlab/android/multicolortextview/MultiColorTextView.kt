package com.lombokcyberlab.android.multicolortextview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.TypedValue
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.content.res.getStringOrThrow

/**
 * Created by deLaCious on 11/6/2017.
 */
class MultiColorTextView : LinearLayoutCompat {
    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MultiColorTextView, 0, 0)
        try {
            colors = typedArray.getTextArray(R.styleable.MultiColorTextView_colors)?: resources.getTextArray(R.array.lively)
            val scheme = typedArray.getInt(R.styleable.MultiColorTextView_colorScheme, 0)
            if (scheme != 0) {
                colors = resources.getTextArray(when (scheme) {
                    1 -> R.array.lively
                    2 -> R.array.google
                    3 -> R.array.bootstrap
                    4 -> R.array.metro
                    5 -> R.array.dark_red
                    else -> R.array.lively
                })
            }
            text = typedArray.getStringOrThrow(R.styleable.MultiColorTextView_text)
            size = typedArray.getFloat(R.styleable.MultiColorTextView_textSize, 12f)
            textStyle = typedArray.getString(R.styleable.MultiColorTextView_textStyle) ?: ""
            typedArray.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    init {
        orientation = HORIZONTAL
    }

    private var textStyle: String = ""
    private var colors = Array<CharSequence>(0) { i -> i.toString() }
    private var size: Float = dp(12)
    private var _text: String = ""
    private var text: String
        set(value) {
            _text = value
            renderText()
        }
        get() = _text

    private fun renderText() {
        var ctr = 0
        _text.split("").forEachIndexed { _, s ->
            if (s.isNotEmpty()) {
                val tv = TextView(context)
                tv.text = s
                val col: String = colors[ctr % colors.size].toString()
                ctr++
                tv.setTextColor(Color.parseColor(col))
                tv.textSize = size
                when (textStyle) {
                    "bold" -> tv.typeface = Typeface.DEFAULT_BOLD
                    else -> tv.typeface = Typeface.DEFAULT
                }

                addView(tv)
            }
        }
    }

    private fun dp(d: Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d.toFloat(), resources.displayMetrics)
}