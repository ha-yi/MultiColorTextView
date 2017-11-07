package com.lombokcyberlab.android.multicolortextview

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface

import android.support.v7.widget.LinearLayoutCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.widget.TextView

/**
 * Created by deLaCious on 11/6/2017.
 */
class MultiColorTextView: LinearLayoutCompat {
    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        val arr = context.theme.obtainStyledAttributes(attrs, R.styleable.MultiColorTextView, 0, 0)
        try {
            colors =  arr.getTextArray(R.styleable.MultiColorTextView_colors)
        } catch (e:Exception) {
            e.printStackTrace()
        }
        try {
            val scheme = arr.getInt(R.styleable.MultiColorTextView_colorScheme, 0)
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
//            size = arr.getDimensionPixelSize(R.styleable.MultiColorTextView_textSize, 12).toFloat()
        } catch (e:Exception) {
            e.printStackTrace()
            try {
                colors =  arr.getTextArray(R.styleable.MultiColorTextView_colors)
            } catch (e:Exception) {
                e.printStackTrace()
            }
        }

        try {
            size = arr.getFloat(R.styleable.MultiColorTextView_textSize, 12f)
        } catch (e:Exception) {
            e.printStackTrace()
        }
        try {
            textStyle = arr.getString(R.styleable.MultiColorTextView_textStyle)

        } catch (e:Exception) {
            e.printStackTrace()
        }

        try {
            text = arr.getString(R.styleable.MultiColorTextView_text)
        } catch (e:Exception) {
            e.printStackTrace()
        }


    }

    init {
        orientation = HORIZONTAL
    }

    var textStyle:String = ""
    var colors = Array<CharSequence>(0,{ i -> i.toString()})
    var size:Float = dp(12)
    var _text:String = ""
    var text:String
        set(value) {
            _text = value
            renderText()
        }
        get() = _text

    private fun renderText() {
        var ctr = 0
        _text.split("").forEachIndexed { index, s ->
            if (!s.isEmpty()) {
                val tv = TextView(context)
                tv.setText(s)
                val col: String = colors[ctr % colors.size].toString()
                ctr++
                tv.setTextColor(Color.parseColor(col))
                tv.textSize = size
                when (textStyle) {
                    "bold" -> tv.setTypeface(Typeface.DEFAULT_BOLD)
                    else -> tv.setTypeface(Typeface.DEFAULT)
                }

                addView(tv)
            }
        }
    }

    private fun dp(d:Int) = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, d.toFloat(), resources.displayMetrics)
}