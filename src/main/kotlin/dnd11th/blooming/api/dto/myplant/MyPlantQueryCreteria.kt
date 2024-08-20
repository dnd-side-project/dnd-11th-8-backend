package dnd11th.blooming.api.dto.myplant

enum class MyPlantQueryCreteria {
    CreatedDesc,
    CreatedAsc,
    WateredDesc,
    WateredAsc,
    ;

    companion object {
        fun from(
            sort: MyPlantSortParam,
            direction: MyPlantDirectionParam,
        ): MyPlantQueryCreteria =
            when (Pair(sort, direction)) {
                Pair(MyPlantSortParam.CREATED, MyPlantDirectionParam.DESC) -> CreatedDesc
                Pair(MyPlantSortParam.CREATED, MyPlantDirectionParam.ASC) -> CreatedAsc
                Pair(MyPlantSortParam.WATERED, MyPlantDirectionParam.DESC) -> WateredDesc
                Pair(MyPlantSortParam.WATERED, MyPlantDirectionParam.ASC) -> WateredAsc
                else -> CreatedDesc
            }
    }
}
