package com.example.project1.view.fragments

import com.example.project1.domain.Film
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.view.rv_adapters.FilmListRecyclerAdapter
import com.example.project1.view.rv_adapters.TopSpacingItemDecoration
import com.example.project1.databinding.FragmentHomeBinding
import com.example.project1.view.MainActivity
import com.example.project1.viewmodel.HomeFragmentViewModel
import java.util.Locale

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    private val viewModel by lazy {
        ViewModelProvider.NewInstanceFactory().create(HomeFragmentViewModel::class.java)
    }

    // Сюда автоматически прилетают данные из ViewModel
    private var filmsDataBase = listOf<Film>()
        set(value) {
            if (field == value) return
            field = value
            filmsAdapter.addItems(field)
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mainActivity = activity as? MainActivity

        // ИСПРАВЛЕНО: Сначала создаем адаптер, сохраняя его в свойство класса
        filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                mainActivity?.launchDetailsFragment(film)
            }
        })

        // Настраиваем RecyclerView
        binding.mainRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            if (itemDecorationCount == 0) {
                addItemDecoration(TopSpacingItemDecoration(8))
            }
        }

        // Настраиваем SearchView
        val searchView = binding.searchView
        searchView.isIconified = false // Исправлена опечатка в имени метода
        searchView.clearFocus()

        searchView.setOnClickListener {
            searchView.isIconified = false
            searchView.requestFocus()
        }

        // ИСПРАВЛЕНО: Оставлен только один, рабочий слушатель текста
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                // ИСПРАВЛЕНО: Если поиск пустой, возвращаем весь список, полученный из ViewModel
                if (newText.isEmpty()) {
                    filmsAdapter.addItems(filmsDataBase)
                    return true
                }

                // Фильтруем локальный список, который хранит актуальную БД
                val result = filmsDataBase.filter {
                    it.title.lowercase(Locale.getDefault())
                        .contains(newText.lowercase(Locale.getDefault()))
                }

                filmsAdapter.addItems(result)
                return true
            }
        })

        // Подписываемся на данные из ViewModel
        viewModel.filmsListLiveData.observe(viewLifecycleOwner, Observer<List<Film>> {
            filmsDataBase = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}