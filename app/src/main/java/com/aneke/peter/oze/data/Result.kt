package com.aneke.peter.oze.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "results_table")
data class Result(

    @PrimaryKey(autoGenerate = true)
    var aid : Int = 0,

    val id: Int = 0,
    val avatar_url: String = "",
    val events_url: String = "",
    val followers_url: String = "",
    val following_url: String = "",
    val gists_url: String = "",
    val gravatar_id: String = "",
    val html_url: String = "",
    val login: String = "",
    val node_id: String = "",
    val organizations_url: String = "",
    val received_events_url: String = "",
    val repos_url: String = "",
    val score: Double = 0.0,
    val site_admin: Boolean = false,
    val starred_url: String = "",
    val subscriptions_url: String = "",
    val type: String = "",
    val url: String = "",
    var isFavorite : Boolean = false
) :Parcelable {

    fun toFavorite() : Favorite {
        return Favorite(
            aid, id, avatar_url, events_url, followers_url, following_url, gists_url, gravatar_id, html_url, login, node_id, organizations_url, received_events_url, repos_url, score, site_admin, starred_url, subscriptions_url, type, url, true
        )
    }

}