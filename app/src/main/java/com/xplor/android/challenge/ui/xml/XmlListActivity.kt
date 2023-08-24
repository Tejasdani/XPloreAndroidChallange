package com.xplor.android.challenge.ui.xml

import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.xplor.android.challenge.App
import com.xplor.android.challenge.databinding.ActivityXmlListBinding
import com.xplor.android.challenge.repository.models.Pokemon
import com.xplor.android.challenge.ui.MainViewModel
import com.xplor.android.challenge.utils.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class XmlListActivity : AppCompatActivity(), PokedexAdapter.OnClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private lateinit var binding: ActivityXmlListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityXmlListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = PokedexAdapter(this)
        val layoutManager =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                GridLayoutManager(this, 2)
            } else {
                GridLayoutManager(this, 4)
            }

        binding.recyclerView.layoutManager = layoutManager

        binding.recyclerView.adapter = adapter

        val favoriteAdapter = FavoriteAdapter()
        val favoriteLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.favoriteRecyclerview.layoutManager = favoriteLayoutManager
        binding.favoriteRecyclerview.adapter = favoriteAdapter
        binding.favoriteRecyclerview.visibility = View.GONE
        lifecycleScope.launchWhenStarted {
            viewModel.pokedex.collect {
                when (it) {
                    is ApiState.Success -> {
                        Timber.d("Success: ${it.data}")
                        binding.progressbar.visibility = View.GONE
                        adapter.submitList(it.data)
                    }

                    is ApiState.Loading -> {
                        binding.progressbar.visibility = View.VISIBLE
                    }

                    is ApiState.Error -> {
                        Timber.e(it.e)
                        binding.progressbar.visibility = View.GONE
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {

            //todo previously we have Dispatchers.IO and I changed it to Dispatchers.Main
            //  as it will give us runtime exception " Only the original thread that created
            // a view hierarchy can touch its views." Because we cant perform heavy task in UI thread

            withContext(Dispatchers.Main) {
                viewModel.favoritePokemon.collect {
                    when (it) {
                        is ApiState.Success -> {
                            Timber.d("Success Favorite Pokemon: ${it.data}")
                            if (it.data.isEmpty()) {
                                binding.favoriteRecyclerview.visibility = View.GONE
                            } else {
                                binding.favoriteRecyclerview.visibility = View.VISIBLE
                                favoriteAdapter.submitList(it.data)
                            }
                        }

                        is ApiState.Loading -> {
                            Timber.d("Favorite Pokemon loading")
                        }

                        is ApiState.Error -> {
                            Timber.e("Favorite Pokemon error")
                        }
                    }
                }
            }
        }
    }

    fun registerDataObserved() {
        binding.favoriteRecyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val itemCount = layoutManager.itemCount

                // Check if the last visible item is near the end of the list
                if (lastVisibleItemPosition >= itemCount - 2) {
                    recyclerView.scrollToPosition(itemCount - 1)
                }
            }
        })
    }

    override fun onPokemonClicked(pokemon: Pokemon) {
        if (pokemon.isFavorite) {
            viewModel.removePokemonFromFavorite(pokemon)
        } else {
            viewModel.setPokemonAsFavorite(pokemon)
        }

        //todo Here I have added some code for this issue:-
      //  Whenever an item in the list is selected, the UI is not refreshed to reflect the `isFavorite`
      //  - state of the item; you will need to scroll away of the item and back to it to see the UI changes reflected.

        binding.favoriteRecyclerview.adapter!!.notifyItemInserted(binding.favoriteRecyclerview.adapter!!.itemCount)
        val lastPosition = binding.favoriteRecyclerview.adapter!!.itemCount - 1
        println("lastPosition ::::: $lastPosition")
        binding.favoriteRecyclerview.postDelayed({
            binding.favoriteRecyclerview.scrollToPosition(lastPosition)
        }, 100)

    }

}
