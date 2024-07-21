import androidx.room.Room
import com.example.brainbusters.data.BrainBusterDatabase
import com.example.brainbusters.data.repositories.BadgeRepository
import com.example.brainbusters.data.repositories.CareerRepository
import com.example.brainbusters.data.repositories.QuestionRepository
import com.example.brainbusters.data.repositories.QuizDoneRepository
import com.example.brainbusters.data.repositories.QuizRepository
import com.example.brainbusters.data.repositories.ResponseRepository
import com.example.brainbusters.data.repositories.UsersRepository
import com.example.brainbusters.ui.viewModels.QuestionViewModel
import com.example.brainbusters.ui.viewModels.CareerViewModel
import com.example.brainbusters.ui.viewModels.QuizDoneViewModel
import com.example.brainbusters.ui.viewModels.QuizViewModel
import com.example.brainbusters.ui.viewModels.ResponseViewModel
import com.example.brainbusters.ui.viewModels.ScoreboardViewModel
import com.example.brainbusters.ui.viewModels.UserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val dataModule = module {
    single {
        CoroutineScope(SupervisorJob())
    }

    single {
        Room.databaseBuilder(
            androidContext(),
            BrainBusterDatabase::class.java,
            "database"
        )
            .addCallback(BrainBusterDatabase.Callback(get()))
            .createFromAsset("database/seed.db")
            .build()
    }

    single { get<BrainBusterDatabase>().usersDAO() }
    single { get<BrainBusterDatabase>().careersDAO() }
    single { get<BrainBusterDatabase>().badgesDAO() }
    single { get<BrainBusterDatabase>().quizzesDAO() }
    single { get<BrainBusterDatabase>().questionsDAO() }
    single { get<BrainBusterDatabase>().responsesDAO() }
    single { get<BrainBusterDatabase>().quizzesDoneDAO() }

    single { UsersRepository(get()) }
    single { BadgeRepository(get()) }
    single { QuizRepository(get()) }
    single { CareerRepository(get()) }
    single { QuestionRepository(get()) }
    single { ResponseRepository(get()) }
    single { QuizDoneRepository(get()) }

    viewModel { UserViewModel(get(), get(), get()) }
    viewModel { QuizViewModel(get()) }
    viewModel { QuestionViewModel(get()) }
    viewModel { CareerViewModel(get()) }
    viewModel { ResponseViewModel(get()) }
    viewModel { QuizDoneViewModel(get()) }
    viewModel { ScoreboardViewModel(get(), get(), get(), get()) }

}