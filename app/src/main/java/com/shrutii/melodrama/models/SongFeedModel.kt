package com.shrutii.melodrama.models

data class SongFeedModel (
    val categoryName: String?=null,
    val songList : ArrayList<SongModel>?=null,
    val isCategoryList:Boolean=false

        )