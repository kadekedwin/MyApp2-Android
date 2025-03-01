package com.example.myapp2.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.myapp2.model.entity.Quest
import com.example.myapp2.model.entity.QuestOption
import com.example.myapp2.model.entity.QuestWithOptions
import com.example.myapp2.model.entity.Quiz
import com.example.myapp2.model.entity.QuizWithQuests
import com.example.myapp2.model.entity.QuizWithQuestsAndOptions
import com.example.myapp2.model.repository.QuestOptionRepository
import com.example.myapp2.model.repository.QuestRepository
import com.example.myapp2.model.repository.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class QuizViewModel(private val quizRepository: QuizRepository) : ViewModel() {
    private val _getQuizWithQuestsAndOptionsId = MutableStateFlow<Long?>(null)
    fun getQuizWithQuestsAndOptionsId(quizId: Long) {
        _getQuizWithQuestsAndOptionsId.value = quizId
    }


    val allQuiz: StateFlow<List<Quiz>> = quizRepository.allQuiz
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    fun insertQuiz(quiz: Quiz, onRetreived: (Long) -> Unit) {
        viewModelScope.launch {
            onRetreived(quizRepository.insertQuiz(quiz))
        }
    }

    fun deleteQuiz(quiz: Quiz) {
        viewModelScope.launch {
            quizRepository.deleteQuiz(quiz)
        }
    }

    val allQuizWithQuestsAndOptions: StateFlow<List<QuizWithQuestsAndOptions>> = quizRepository.allQuizWithQuestsAndOptions
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = emptyList())

    val getQuizWithQuestsAndOptions: StateFlow<QuizWithQuestsAndOptions?> = _getQuizWithQuestsAndOptionsId
        .flatMapLatest { id ->
            if (id != null) quizRepository.getQuizWithQuestsAndOptions(quizId = id)
            else flowOf(null)
        }
        .stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = null)

}


class QuizViewModelFactory(private val quizRepository: QuizRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(QuizViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return QuizViewModel(quizRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}