package com.example.project1

import Film
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.project1.databinding.ActivityDetailsBinding // Предполагаем, что ваш layout называется activity_details.xml

class DetailsActivity : ComponentActivity() {

    // Объявляем переменную для View Binding
    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Инициализация View Binding и установка макета
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Получаем объект Film
        val film = getFilmFromIntent()

        // 4. Вызываем функцию привязки, если фильм получен успешно
        if (film != null) {
            bind(film)
        } else {
            // Если фильм не получен, сообщаем об ошибке и завершаем Activity
            Toast.makeText(this, "Ошибка: данные фильма отсутствуют", Toast.LENGTH_LONG).show()
            finish()
        }
    }

    /**
     * Получает объект Film из Intent, используя безопасный метод.
     */
    private fun getFilmFromIntent(): Film? {
        val extras = intent.extras
        if (extras == null) {
            Log.e("DetailsActivity", "Extras в Intent отсутствуют.")
            return null
        }

        // Безопасное получение Parcelable.
        // Используем метод, который подходит для разных версий Android.
        @Suppress("DEPRECATION")
        val film = extras.getParcelable<Film>("film")

        // Если вы используете Kotlin Extension (например, Android KTX) и API 33+,
        // вы могли бы использовать:
        // val film = intent.getParcelableExtra("film", Film::class.java)

        if (film == null) {
            Log.e("DetailsActivity", "Объект 'film' не найден или имеет неверный тип.")
        }
        return film
    }

    /**
     * Привязывает данные фильма к элементам UI.
     * @param film Объект Film для отображения.
     */
    private fun bind(film: Film) {
        // 3. Используем View Binding для доступа к элементам
        // Предполагается, что id элементов в activity_details.xml
        // соответствуют названиям в вашем исходном коде:

        // Устанавливаем заголовок в Toolbar
        // Если у вас Material Toolbar (com.google.android.material.appbar.MaterialToolbar):
        binding.detailsToolbar.title = film.title

        // Устанавливаем картинку в ImageView
        binding.detailsPoster.setImageResource(film.poster)

        // Устанавливаем описание в TextView
        binding.detailsDescription.text = film.description
    }
}