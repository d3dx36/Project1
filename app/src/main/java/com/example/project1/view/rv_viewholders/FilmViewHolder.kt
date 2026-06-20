package com.example.project1.view.rv_viewholders

import androidx.recyclerview.widget.RecyclerView
import com.example.project1.domain.Film
import com.example.project1.databinding.FilmItemBinding

class FilmViewHolder(private val binding: FilmItemBinding) : RecyclerView.ViewHolder(binding.root) {

    // Теперь нам не нужно искать View вручную, они уже есть в объекте binding
    val itemContainer = binding.itemContainer

    fun bind(film: Film) {
        // Доступ к элементам осуществляется через binding
        binding.title.text = film.title
        binding.poster.setImageResource(film.poster)
        binding.description.text = film.description
    }
}