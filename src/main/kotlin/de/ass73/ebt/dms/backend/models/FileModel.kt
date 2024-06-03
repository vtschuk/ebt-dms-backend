package de.ass73.ebt.dms.backend.models


import java.sql.Date

@NoArgs
data class FileModel(
    val id: Long,
    var name: String,
    var date: Date,
    var issue: String,
    var description: String,
    var archive: Boolean,
    var encrypted: Boolean
)

