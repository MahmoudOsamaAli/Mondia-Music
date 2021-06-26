package com.example.mondiamusic.extensions

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.util.DisplayMetrics
import android.widget.FrameLayout
import com.example.mondiamusic.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

object ExtensionsUtils {

    fun Dialog.setupFullHeight(context: Context) {
        setOnShowListener { dialogInterface: DialogInterface ->
            val bottomSheetDialog = dialogInterface as BottomSheetDialog
            setupFullHeight(context, bottomSheetDialog)
        }
    }

    private fun setupFullHeight(context: Context, bottomSheetDialog: BottomSheetDialog) {
        val bottomSheet = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet)
        if (bottomSheet != null) {
            val behavior: BottomSheetBehavior<*> = BottomSheetBehavior.from(bottomSheet)
            val layoutParams = bottomSheet.layoutParams
            val windowHeight = getWindowHeight(context)
            if (layoutParams != null) {
                layoutParams.height = windowHeight
            }
            bottomSheet.layoutParams = layoutParams
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun getWindowHeight(context: Context): Int {
        val displayMetrics = DisplayMetrics()
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            context.display?.apply { getRealMetrics(displayMetrics) }
        } else (context as Activity).windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    fun duration(duration: Int): String =
        "${String.format("%02d", (duration * 1000) / 1000 / 60)}:${
            String.format(
                "%02d",
                (duration * 1000) / 1000 % 60
            )
        }"

}