package com.alorma.materialmarker

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.graphics.drawable.RippleDrawable
import android.util.AttributeSet
import androidx.annotation.AttrRes
import androidx.annotation.StyleRes
import androidx.appcompat.widget.AppCompatCheckedTextView
import androidx.core.content.withStyledAttributes
import androidx.core.view.ViewCompat
import androidx.core.view.updatePadding
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
) : AppCompatCheckedTextView(context, attributeSet, defStyleAttr) {

    private var strokeWidth: Float = 2f

    private var pointSize: Int = 8.dp

    private var strokeColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorOnSurface)
    )

    private var fillColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorSurface)
    )

    private var rippleColor: ColorStateList = ColorStateList.valueOf(
        MaterialColors.getColor(this, R.attr.colorOnPrimary)
    )

    init {
        setTextColor(strokeColor)

        updatePadding(
            left = paddingLeft.coerceAtLeast(8.dp),
            top = paddingLeft.coerceAtLeast(8.dp),
            right = paddingLeft.coerceAtLeast(8.dp),
            bottom = paddingLeft.coerceAtLeast(8.dp),
        )

        context.withStyledAttributes(
            attributeSet,
            R.styleable.MaterialMapMarker,
            defStyleAttr,
            defStyleRes
        ) {
            readAttributes()
            initBackground(context, attributeSet, defStyleAttr, defStyleRes)
        }
    }

    private fun TypedArray.readAttributes() {
        pointSize = getDimensionPixelOffset(R.styleable.MaterialMapMarker_pointSize, pointSize)

        rippleColor = getColorStateList(
            R.styleable.MaterialMapMarker_rippleColor
        ) ?: rippleColor

        fillColor = getColorStateList(
            R.styleable.MaterialMapMarker_fillColor
        ) ?: fillColor

        strokeColor = getColorStateList(
            R.styleable.MaterialMapMarker_strokeColor
        ) ?: strokeColor
    }

    private fun initBackground(
        context: Context,
        attributeSet: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) {

        val pointEdge = TriangleEdgeTreatment(pointSize.toFloat(), false)
        val shapeAppearanceModel = ShapeAppearanceModel.builder(
            context,
            attributeSet,
            defStyleAttr,
            defStyleRes
        ).setBottomEdge(pointEdge)
            .build()

        val materialDrawable = MaterialShapeDrawable(shapeAppearanceModel)

        materialDrawable.initializeElevationOverlay(context)
        materialDrawable.elevation = ViewCompat.getElevation(this@MaterialMapMarker)

        materialDrawable.fillColor = fillColor

        materialDrawable.strokeWidth = strokeWidth
        materialDrawable.strokeColor = strokeColor

        materialDrawable.setPadding(
            paddingLeft,
            paddingTop,
            paddingRight,
            paddingBottom
        )

        val maskDrawable = MaterialShapeDrawable(shapeAppearanceModel)

        val rippleDrawable = RippleDrawable(
            rippleColor,
            wrapDrawableWithInset(materialDrawable),
            maskDrawable
        )

        ViewCompat.setBackground(this@MaterialMapMarker, rippleDrawable)
    }

    private fun wrapDrawableWithInset(drawable: Drawable): InsetDrawable {
        return InsetDrawable(drawable, 0.dp, 0.dp, 0.dp, pointSize)
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