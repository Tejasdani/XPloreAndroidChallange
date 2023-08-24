package com.xplor.android.challenge.ui.xml

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.xplor.android.challenge.R
import com.xplor.android.challenge.databinding.ItemPokemonBinding
import com.xplor.android.challenge.repository.models.Pokemon
import com.xplor.android.challenge.utils.toEntryNumber
import com.xplor.android.challenge.utils.toImageUrlById

class PokedexAdapter(
    private val listener: OnClickListener
) : ListAdapter<Pokemon, PokemonViewHolder>(DIFF_UTIL) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        return PokemonViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(getItem(position), listener)
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

    interface OnClickListener {
        fun onPokemonClicked(pokemon: Pokemon)
    }
}

class PokemonViewHolder(private val item: View) : RecyclerView.ViewHolder(item) {

    private val binding: ItemPokemonBinding by lazy {
        ItemPokemonBinding.bind(item)
    }

    fun bind(pokemon: Pokemon, listener: PokedexAdapter.OnClickListener) {
        binding.name.text = pokemon.name
        binding.entryNumber.text = pokemon.entryNumber.toEntryNumber()
        binding.favorite.visibility = if (pokemon.isFavorite) View.VISIBLE else View.GONE
        binding.image.load(pokemon.entryNumber.toImageUrlById()) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }
        binding.root.setOnClickListener {
            listener.onPokemonClicked(pokemon)
        }
    }

    companion object {
        fun from(parent: ViewGroup): PokemonViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.item_pokemon, parent, false)
            return PokemonViewHolder(view)
        }
    }
}
