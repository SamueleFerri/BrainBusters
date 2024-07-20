package com.example.brainbusters.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.brainbusters.data.entities.Response
import com.example.brainbusters.data.repositories.ResponseRepository
import kotlinx.coroutines.launch

class ResponseViewModel(private val responseRepository: ResponseRepository) : ViewModel() {

    fun insertResponse(response: Response) {
        viewModelScope.launch {
            responseRepository.insertResponse(response)
        }
    }

    fun updateResponse(response: Response) {
        viewModelScope.launch {
            responseRepository.updateResponse(response)
        }
    }

    fun deleteResponse(response: Response) {
        viewModelScope.launch {
            responseRepository.deleteResponse(response)
        }
    }

    fun getResponseById(responseId: Int, callback: (Response?) -> Unit) {
        viewModelScope.launch {
            val response = responseRepository.getResponseById(responseId)
            callback(response)
        }
    }

    fun getResponsesByQuestionId(questionId: Int, callback: (List<Response>) -> Unit) {
        viewModelScope.launch {
            val responses = responseRepository.getResponsesByQuestionId(questionId)
            callback(responses)
        }
    }

    fun getAllResponses(callback: (List<Response>) -> Unit) {
        viewModelScope.launch {
            val responses = responseRepository.getAllResponses()
            callback(responses)
        }
    }
}