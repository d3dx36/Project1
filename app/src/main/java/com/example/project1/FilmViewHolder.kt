import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.example.project1.R

//В конструктор класс передается layout, который мы создали(film_item.xml)
class FilmViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    //Привязываем View из layout к переменным
    private val title: TextView = itemView.findViewById(R.id.title)
    private val poster: ImageView = itemView.findViewById(R.id.poster)
    private val description: TextView = itemView.findViewById(R.id.description)
    val itemContainer: View = itemView.findViewById(R.id.item_container)
    //В этом методе кладем данные из Film в наши View
    fun bind(film: Film) {
        // Устанавливаем заголовок из объекта film
        title.text = film.title

        // Устанавливаем постер из объекта film (должно быть Int-ID ресурса)
        poster.setImageResource(film.poster)

        // Устанавливаем описание из объекта film
        description.text = film.description
    }
}
