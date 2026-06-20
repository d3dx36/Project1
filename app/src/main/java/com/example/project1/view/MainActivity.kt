package com.example.project1.view

import com.example.project1.domain.Film
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.project1.view.fragments.DetailsFragment
import com.example.project1.view.fragments.FavoritesFragment
import com.example.project1.view.fragments.HomeFragment
import com.example.project1.R
import com.example.project1.databinding.ActivityMainBinding

class MainActivity   : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // База данных фильмов


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        initNavigation()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_placeholder, HomeFragment())
                .commit()
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Проверяем количество фрагментов в стеке
                if (supportFragmentManager.backStackEntryCount > 0) {
                    // Если в стеке есть фрагменты (например, открыты детали), просто идем назад
                    supportFragmentManager.popBackStack()
                } else {
                    // Если мы на главном экране — показываем диалог
                    showExitDialog()
                }
            }
        }
// Регистрируем колбэк
        onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initNavigation() {
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.settings -> {
                    Toast.makeText(this, "Настройки", Toast.LENGTH_SHORT).show()
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.setOnItemSelectedListener {

            when (it.itemId) {
                R.id.favorites -> {
                    supportFragmentManager
                        .beginTransaction()
                        .replace(R.id.fragment_placeholder, FavoritesFragment())
                        .addToBackStack(null)
                        .commit()
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
    }

    fun launchDetailsFragment(film: Film) {
        val bundle = Bundle()
        bundle.putParcelable("film", film)
        val fragment = DetailsFragment()
        fragment.arguments = bundle

        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_placeholder, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun showExitDialog() {
        AlertDialog.Builder(this)
            .setTitle("Выход")
            .setMessage("Вы действительно хотите покинуть приложение?")
            .setPositiveButton("Да") { _, _ ->
                finish() // Закрывает активити и выходит из приложения
            }
            .setNegativeButton("Нет") { dialog, _ ->
                dialog.dismiss() // Просто закрывает окно
            }
            .show()
    }


}