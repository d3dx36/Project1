package com.example.project1.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.project1.App
import com.example.project1.data.MainRepository
import com.example.project1.domain.Film
import com.example.project1.domain.Interactor

class HomeFragmentViewModel : ViewModel() {
    val filmsListLiveData:  MutableLiveData<List<Film>> = MutableLiveData()
    //Инициализируем интерактор
    private var interactor: Interactor = App.instance.interactor
    init {
        val films = interactor.getFilmsDB()
        filmsListLiveData.postValue(films)
    }
}