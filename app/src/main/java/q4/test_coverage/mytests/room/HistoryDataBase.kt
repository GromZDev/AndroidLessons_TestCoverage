package q4.test_coverage.mytests.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [HistoryEntity::class], version = 1, exportSchema = true
)
abstract class HistoryDataBase : RoomDatabase() {
    /**  Возвращаем наш DAO */
    abstract fun historyDao(): HistoryDao

}