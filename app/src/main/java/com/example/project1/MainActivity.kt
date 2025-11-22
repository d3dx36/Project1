package com.example.project1

// ДОБАВЬТЕ ЭТУ СТРОКУ, ЕСЛИ DetailsActivity находится в этом же пакете,
// но не был импортирован.
import Film
import FilmListRecyclerAdapter
import TopSpacingItemDecoration
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.databinding.ActivityMainBinding


class MainActivity : ComponentActivity() {

    private val filmsDataBase = listOf(
        Film("Воображаемый друг", R.drawable.ffmqt, "This should be a description"),
        Film("Пила Наследие", R.drawable.lgncz, "This should be a description"),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to its reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to its reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to its reluctant son.")
    )

    private lateinit var filmsAdapter: FilmListRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initNavigation()
    }

    private fun initNavigation() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // --- Обработчики меню TopAppBar ---
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.topAppBar -> {
                    Toast.makeText(this, "Меню", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // --- Обработчики BottomNavigationView ---
        binding.bottomNavigation.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.favorites -> {
                    Toast.makeText(this, "Избранное", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.watch_later -> {
                    Toast.makeText(this, "Посмотреть позже", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.selections -> {
                    Toast.makeText(this, "Подборки", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        // --- Инициализация RecyclerView ---
        binding.mainRecycler.apply {
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    //Создаем бандл и кладем туда объект с данными фильма
                    val bundle = Bundle()
                    bundle.putParcelable("film", film)

                    // Запускаем наше активити
                    val intent = Intent(this@MainActivity, DetailsActivity::class.java)

                    // Прикрепляем бандл к интенту
                    // putExtras - это правильный метод для Intent
                    intent.putExtras(bundle)

                    // Запускаем активити через интент
                    startActivity(intent)
                }
            })

            adapter = filmsAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            val decorator = TopSpacingItemDecoration(8)
            addItemDecoration(decorator)
        }

        // Кладем нашу БД в RV
        filmsAdapter.addItems(filmsDataBase)
    }
}