package de.ass73.ebt.dms.backend.models

@NoArgs
class UploadDocFilesModel(
    var id: Long,
    var personId: Long,
    var name: String,
    var type: String,
    var filedata: ByteArray
)
