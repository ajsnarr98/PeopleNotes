package com.github.ajsnarr98.linknotes.util.markdown

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.github.ajsnarr98.linknotes.R
import com.github.ajsnarr98.linknotes.databinding.ViewMarkdownEditHelperBinding
import com.github.ajsnarr98.linknotes.util.getActivity
import com.github.ajsnarr98.linknotes.util.softkeyboard.SoftKeyboardAccessoryBehavior
import kotlin.math.roundToInt

class MarkdownEditHelperView : ConstraintLayout, CoordinatorLayout.AttachedBehavior {
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private val actionListener: ActionListener = ActionListener(context)
    private val binding: ViewMarkdownEditHelperBinding
        = ViewMarkdownEditHelperBinding.bind(inflate(context, R.layout.view_markdown_edit_helper, this)).apply {

        indent.setOnClickListener { actionListener.onIndent() }
        bulletList.setOnClickListener { actionListener.onBulletList() }
        numberedList.setOnClickListener { actionListener.onNumberedList() }
        bold.setOnClickListener { actionListener.onBold() }
        italic.setOnClickListener { actionListener.onItalic() }
        underline.setOnClickListener { actionListener.onUnderline() }
        strikethrough.setOnClickListener { actionListener.onStrikethrough() }
        addLink.setOnClickListener { actionListener.onLink() }
        addPhoto.setOnClickListener { actionListener.onPhoto() }
    }

    override fun getBehavior(): CoordinatorLayout.Behavior<*>
        = SoftKeyboardAccessoryBehavior<MarkdownEditHelperView>(
            defaultVisibility = View.INVISIBLE,
            scrollAmount = context.resources.getDimension(R.dimen.markdown_edit_helper_height).roundToInt(),
            topPadding = context.resources.getDimension(R.dimen.markdown_edit_helper_cursor_scroll_padding).roundToInt(),
            // make sure only show as visible if the selected view supports markdown edit
            shouldMakeVisible = { focusedView -> focusedView is MarkdownEditText },
        )

    class ActionListener(val context: Context) {
        val focusedField: MarkdownEditText?
            get() = getActivity(context)?.currentFocus as? MarkdownEditText

        fun onIndent() = focusedField?.addListIndent()
        fun onBulletList() = focusedField?.toggleBulletList()
        fun onNumberedList() = focusedField?.toggleNumberedList()
        fun onBold() = focusedField?.toggleBold()
        fun onItalic() = focusedField?.toggleItalic()
        fun onUnderline() = focusedField?.toggleUnderline()
        fun onStrikethrough() = focusedField?.toggleStrikethrough()
        fun onLink() = focusedField?.addLink()
        fun onPhoto() = focusedField?.addPhoto()
    }
}