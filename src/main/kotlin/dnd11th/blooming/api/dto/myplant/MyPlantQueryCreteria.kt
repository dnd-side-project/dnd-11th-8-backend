package dnd11th.blooming.api.dto.myplant

enum class MyPlantQueryCreteria {
    CreatedDesc,
    CreatedAsc,
    NoLocation,
    ;

    companion object {
        fun from(sort: String): MyPlantQueryCreteria {
            return when (sort) {
                "created_desc" -> CreatedDesc
                "created_asc" -> CreatedAsc
                "no_location" -> NoLocation
                else -> CreatedDesc
            }
        }
    }
}
