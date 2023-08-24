package com.xplor.android.challenge.ui.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.xplor.android.challenge.R
import com.xplor.android.challenge.databinding.ItemFavoritePokemonBinding
import com.xplor.android.challenge.repository.models.Pokemon
import com.xplor.android.challenge.utils.toImageUrlById

class FavoriteAdapter() : ListAdapter<Pokemon, FavoritePokemonViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritePokemonViewHolder {
        return FavoritePokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: FavoritePokemonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private companion object {
        private val DIFF_UTIL = object : DiffUtil.ItemCallback<Pokemon>() {
            override fun areItemsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: Pokemon, newItem: Pokemon): Boolean {
                return oldItem.name == newItem.name
            }
        }
    }
}

class FavoritePokemonViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    private val binding: ItemFavoritePokemonBinding by lazy {
        ItemFavoritePokemonBinding.bind(item)
    }

    fun bind(pokemon: Pokemon) {
        binding.image.load(pokemon.entryNumber.toImageUrlById()) {
            transformations(CircleCropTransformation())
        }
    }

    companion object {
        fun from(parent: ViewGroup): FavoritePokemonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_favorite_pokemon, parent, false)
            return FavoritePokemonViewHolder(view)
        }
    }
}
