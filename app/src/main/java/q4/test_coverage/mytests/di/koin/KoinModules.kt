package q4.test_coverage.mytests.di.koin

import androidx.room.Room
import org.koin.dsl.module
import q4.test_coverage.mytests.interactor.MainInterActor
import q4.test_coverage.mytests.model.DataModel
import q4.test_coverage.mytests.model.datasource.roomlocal.RepositoryImplementationLocal
import q4.test_coverage.mytests.model.datasource.roomlocal.RepositoryLocal
import q4.test_coverage.mytests.model.datasource.retrofitremote.RetrofitImplementation
import q4.test_coverage.mytests.model.datasource.roomlocal.RoomDataBaseImplementation
import q4.test_coverage.mytests.repository.Repository
import q4.test_coverage.mytests.repository.RepositoryImplementation
import q4.test_coverage.mytests.room.HistoryDataBase
import q4.test_coverage.mytests.interactor.HistoryInterActor
import q4.test_coverage.mytests.viewmodel.HistoryViewModel
import q4.test_coverage.mytests.viewmodel.MainViewModel


/**
 * module { } — создание модуля, это контейнер для коллекции зависимостей
 * single { } — генерация синглтона
 * factory { } — генерация зависимости каждый раз заново
 * get() — создание экземпляра класса
 * */

/** application - тут хранятся зависимости, используемые во всем приложении */
val application = module {

    /** БД должна быть в единственном экземпляре */
    single { Room.databaseBuilder(get(), HistoryDataBase::class.java, "HistoryDB").build() }
    /** Получаем DAO */
    single { get<HistoryDataBase>().historyDao() }
    single<Repository<List<DataModel>>> {
        RepositoryImplementation(RetrofitImplementation())
    }
    single<RepositoryLocal<List<DataModel>>> {
        RepositoryImplementationLocal(RoomDataBaseImplementation(get()))
    }
}

val mainScreen = module {
    factory { MainViewModel(get()) }
    factory { MainInterActor(get(), get()) }
}

val historyScreen = module {
    factory { HistoryViewModel(get()) }
    factory { HistoryInterActor(get(), get()) }
}

