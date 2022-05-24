package com.example.model.local.util

enum class ArchiveFormat(
    val extension: String,
    val archiveFormat: String
) {
    ZIP("zip", "ZIP"),
    RAR("rar", "RAR"),
    TAR("tar", "TAR"),
    SEVEN_ZIP("7z", "7ZIP");
}