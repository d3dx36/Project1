package com.example.project1

import Film
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project1.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Здесь тоже меняем на FragmentDetailsBinding
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Достаем наш фильм из "посылки" (arguments)
        @Suppress("DEPRECATION")
        val film = arguments?.getParcelable<Film>("film")

        // 2. Если фильм доехал успешно, привязываем данные к View
        film?.let {
            binding.detailsToolbar.title = it.title
            binding.detailsPoster.setImageResource(it.poster)
            binding.detailsDescription.text = it.description
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}