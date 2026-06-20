package com.example.project1.view.fragments

// ИСПРАВЛЕНО: проверяйте имя пакета (domain вместо damain)
import com.example.project1.domain.Film
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.view.rv_adapters.FilmListRecyclerAdapter
import com.example.project1.view.rv_adapters.TopSpacingItemDecoration
import com.example.project1.databinding.FragmentFavoritesBinding
import com.example.project1.view.MainActivity
import com.example.project1.data.MainRepository

class FavoritesFragment : Fragment() {
    val repository = MainRepository()
    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!
    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Безопасное получение MainActivity
        val mainActivity = activity as? MainActivity ?: return

        // Получаем отфильтрованный список избранного
        val favoritesList = repository.filmsDataBase.filter { it.isInFavorites }

        // ИСПРАВЛЕНО: Сначала создаем и инициализируем адаптер в свойство класса
        filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener {
            override fun click(film: Film) {
                mainActivity.launchDetailsFragment(film)
            }
        })

        // Теперь настраиваем RecyclerView
        binding.favoritesRecycler.apply {
            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(requireContext())

            // Чтобы декораторы не плодились при пересоздании view,
            // лучше проверить, нет ли их уже, но для старта пойдет так:
            if (itemDecorationCount == 0) {
                addItemDecoration(TopSpacingItemDecoration(8))
            }
        }

        // Передаем данные в адаптер
        filmsAdapter.addItems(favoritesList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}