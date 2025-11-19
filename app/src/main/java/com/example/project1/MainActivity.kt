package com.example.project1


import FilmListRecyclerAdapter
import TopSpacingItemDecoration
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.ActionMode
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project1.databinding.ActivityMainBinding

class MainActivity : ComponentActivity() {
    private lateinit var filmsAdapter: FilmListRecyclerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation()
    }

    fun initNavigation() {
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
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

        binding.mainRecycler.apply { // Обращаемся через binding
            filmsAdapter = FilmListRecyclerAdapter(object : FilmListRecyclerAdapter.OnItemClickListener{
                override fun click(film: Film) {
                    TODO("Not yet implemented")
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

    val filmsDataBase = listOf(
        Film("Воображаемый друг", R.drawable.ffmqt, "This should be a description"),
        Film("Пила Наследие", R.drawable.lgncz, "This should be a description"),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."), Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."), Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."), Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."), Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son."),
        Film("The Godfather", R.drawable.the_godfather, "The aging patriarch of an organized crime dynasty transfers control of his clandestine empire to his reluctant son.")

    )
}