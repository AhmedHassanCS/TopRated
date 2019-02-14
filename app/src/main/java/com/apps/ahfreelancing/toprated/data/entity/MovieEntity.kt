package com.apps.ahfreelancing.toprated.data.entity

import com.google.gson.annotations.SerializedName

/**
 * Created by Ahmed Hassan on 2/12/2019.
 */
class MovieEntity {

    @SerializedName("id") var id : Int? = null

    @SerializedName("title") var name : String? = null

    @SerializedName("poster_path") var posterURL : String? = null

    @SerializedName("vote_average") var averageVote : Double? = null

    @SerializedName("vote_count") var voteCount : Int? = null

    @SerializedName("release_date") var releaseDate : String? = null

    @SerializedName("original_language") var language : String? = null

    @SerializedName("genres") var genres : ArrayList<GenreEntity>? = null
}