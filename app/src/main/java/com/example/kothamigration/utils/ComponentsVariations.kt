package com.example.kothamigration.utils


// For Uploading Video Variations
enum class FileUploadStatus {
    SELECT, PROCESSING, UPLOADING, UPLOADED, ERROR
}

// For Text Input Fields Variations
enum class TextInputType {
    SingleLineText, Number, Slot, LongText, Money, Percentage
}


// Data Class For Choice -> SellFrame 3
data class Choice(val title: String, val subtitle: String, val isEnabled: Boolean)


