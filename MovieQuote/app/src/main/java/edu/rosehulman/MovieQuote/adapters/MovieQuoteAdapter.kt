package edu.rosehulman.MovieQuote.adapters

import android.graphics.Color
import android.service.autofill.TextValueSanitizer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ReportFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import edu.rosehulman.MovieQuote.R
import edu.rosehulman.MovieQuote.models.MovieQuote
import edu.rosehulman.MovieQuote.models.MovieQuoteViewModel
import edu.rosehulman.MovieQuote.ui.QuotesListFragment


class MovieQuoteAdapter(val fragment: QuotesListFragment):RecyclerView.Adapter<MovieQuoteAdapter.MovieQuoteViewHolder>() {

    val model = ViewModelProvider(fragment.requireActivity()).get(MovieQuoteViewModel::class.java)

    init{
        model.addListener()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieQuoteViewHolder {
        val view  = LayoutInflater.from(parent.context).inflate(R.layout.row_quote,parent,false)
        return MovieQuoteViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieQuoteViewHolder, position: Int) {
        holder.bind(model.getQuoteAt(position))
    }

    override fun getItemCount() = model.size()
    fun addQuote(movieQuote: MovieQuote?) {
        model.addQuote(movieQuote)
        notifyDataSetChanged()
    }


    inner class MovieQuoteViewHolder(itemview: View): RecyclerView.ViewHolder(itemview){
        val quoteTextView: TextView = itemView.findViewById<TextView>(R.id.row_quote_text_view)
        var movieTextView: TextView = itemView.findViewById<TextView>(R.id.row_quote_movie_view)

        init {
            itemView.setOnClickListener{
                model.updatePos(adapterPosition)
                fragment.findNavController().navigate(R.id.navigation_quote_edit)
            }
            itemView.setOnLongClickListener{
                model.updatePos(adapterPosition)
                model.toggleCurrentQuote()
                notifyDataSetChanged()
                true
            }
        }

        fun bind(movieQuote: MovieQuote){
            quoteTextView.text = movieQuote.quote
            movieTextView.text = movieQuote.moive
            itemView.setBackgroundColor(
                if(movieQuote.isSelected){
                    //Color.GREEN
                    MaterialColors.getColor(
                        fragment.requireContext(),
                        R.attr.colorAccent,
                        Color.WHITE
                    )
                }else{
                    //Color.WHITE
                    MaterialColors.getColor(
                        fragment.requireContext(),
                        R.attr.colorSurface,
                        Color.WHITE
                    )
                }
            )
        }
    }

}