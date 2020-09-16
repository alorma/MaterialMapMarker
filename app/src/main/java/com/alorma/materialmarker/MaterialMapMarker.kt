package com.alorma.materialmarker

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.ViewCompat
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils
import com.google.android.material.shape.ShapeAppearanceModel
import com.google.android.material.shape.TriangleEdgeTreatment

class MaterialMapMarker @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    @AttrRes defStyleAttr: Int = R.attr.materialMapMarkerStyle,
    @StyleRes defStyleRes: Int = R.style.Widget_Demo_MaterialMarker
) : AppCompatTextView(context, attributeSet, defStyleAttr) {

    private var strokeWidth: Float = 2f
    private var strokeColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorOnPrimary)
    )

    private val fillColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorPrimary)
    )

    init {
        setTextColor(strokeColor)
        initBackground(context, attributeSet, defStyleAttr, defStyleRes)
    }

    private fun initBackground(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {
        val shapeAppearanceModel = ShapeAppearanceModel.builder(
            context,
            attributeSet,
            defStyleAttr,
            defStyleRes
        ).setBottomEdge(TriangleEdgeTreatment(12.dp.toFloat(), false))
            .build()

        val materialDrawable = MaterialShapeDrawable(shapeAppearanceModel)

        materialDrawable.initializeElevationOverlay(context)
        materialDrawable.elevation = ViewCompat.getElevation(this)

        materialDrawable.fillColor = fillColor

        materialDrawable.strokeWidth = strokeWidth
        materialDrawable.strokeColor = strokeColor

        ViewCompat.setBackground(this, materialDrawable)
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        MaterialShapeUtils.setElevation(this, elevation)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        MaterialShapeUtils.setParentAbsoluteElevation(this)
    }
}