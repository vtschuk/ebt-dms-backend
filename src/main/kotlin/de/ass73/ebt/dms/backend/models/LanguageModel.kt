package de.ass73.ebt.dms.backend.models

@NoArgs
data class LanguageModel(
    var id: Long,
    var name: String,
    var aktiv: Boolean
)
