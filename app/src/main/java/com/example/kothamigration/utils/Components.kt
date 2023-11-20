package com.example.kothamigration.utils

import androidx.compose.ui.graphics.Color
import com.example.kothamigration.R



// For Uploading Video Variations
enum class FileUploadStatus {
    SELECT, PROCESSING, UPLOADING, UPLOADED, ERROR
}

// For Text Input Fields Variations
enum class TextInputType {
    SingleLineText, Number, Slot, LongText, Money, Percentage
}


// Data Class For Choice -> SellFrame 3
data class Choice(val title: String, val subtitle: String)


