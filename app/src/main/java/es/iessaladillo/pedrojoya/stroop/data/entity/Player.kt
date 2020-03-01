package es.iessaladillo.pedrojoya.stroop.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [
        Index(
            name = "USERS_INDEX_NAME_UNIQUE",
            value = ["username"],
            unique = true
        )
    ]
)
data class Player(
    @PrimaryKey(autoGenerate = true) val idUser: Long,
    var username: String,
    var image: Long
)