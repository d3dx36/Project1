package com.example.project1.view.fragments

import com.example.project1.domain.Film
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project1.R
import com.example.project1.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        @Suppress("DEPRECATION")
        val film = arguments?.getParcelable<Film>("film")

        film?.let { currentFilm ->
            // Наполняем данными
            binding.detailsToolbar.title = currentFilm.title
            binding.detailsPoster.setImageResource(currentFilm.poster)
            binding.detailsDescription.text = currentFilm.description

            // ЛОГИКА КНОПКИ НАЗАД
            binding.detailsToolbar.setNavigationOnClickListener {
                requireActivity().onBackPressedDispatcher.onBackPressed()
            }

            // Логика кнопки "Поделиться"
            binding.detailsFab.setOnClickListener {
                val intent = Intent().apply {
                    action = Intent.ACTION_SEND
                    putExtra(Intent.EXTRA_TEXT, "Check out this movie: ${currentFilm.title} \n\n ${currentFilm.description}")
                    type = "text/plain"
                }
                startActivity(Intent.createChooser(intent, "Share To:"))
            }

            // Логика кнопки "Избранное"
            binding.detailsFabFavorites.setImageResource(
                if (currentFilm.isInFavorites) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

            binding.detailsFabFavorites.setOnClickListener {
                if (!currentFilm.isInFavorites) {
                    binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_24)
                    currentFilm.isInFavorites = true
                } else {
                    binding.detailsFabFavorites.setImageResource(R.drawable.ic_baseline_favorite_border_24)
                    currentFilm.isInFavorites = false
                }
            }
        }
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null


    }


}