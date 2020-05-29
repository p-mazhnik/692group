package com.pavel.a692group.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.io.Serializable

/**
 * Created by Pavel Mazhnik on 02.03.19.
 * Description:
 */
@Entity(tableName = User.TABLE)
class User : Serializable {
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id = 0

    @ColumnInfo(name = COLUMN_NAME)
    var name: String? = null

    @ColumnInfo(name = COLUMN_GROUP)
    var group: String? = null

    val idToString: String
        get() = "" + id

    fun setIdFromString(str: String) {
        id = -1
        try {
            id = str.toInt()
        } catch (nfe: NumberFormatException) {
            println("Could not parse $nfe")
        }
    }

    fun updateUser(user: User) {
        id = user.id
        name = user.name
        group = user.group
    }

    companion object {
        @Ignore
        const val TABLE = "users" // название таблицы в бд

        // названия столбцов
        @Ignore
        const val COLUMN_ID = "_id"

        @Ignore
        const val COLUMN_LINK = "_link"

        @Ignore
        const val COLUMN_SURNAME = "surname"

        @Ignore
        const val COLUMN_NAME = "name"

        @Ignore
        const val COLUMN_SECOND_NAME = "second_name"

        @Ignore
        const val COLUMN_GROUP = "_group"
    }
}