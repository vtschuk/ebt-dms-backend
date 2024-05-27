package de.ass73.ebt.efile.backend.models

import de.ass73.ebt.dms.backend.models.NoArgs

@NoArgs
class RoleModel(
    var id: Long,
    var name: String
)