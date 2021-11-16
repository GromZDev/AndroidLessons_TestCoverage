package q4.test_coverage.mytests.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    indices = [Index(value = arrayOf("word", "description"), unique = true)]
)
class HistoryEntity(

    @field:PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @field:ColumnInfo(name = "word")
    var word: String,
    @field:ColumnInfo(name = "description")
    var description: String?
)
