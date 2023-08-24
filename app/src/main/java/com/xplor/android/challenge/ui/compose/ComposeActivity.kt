package com.xplor.android.challenge.ui.compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import com.xplor.android.challenge.App
import com.xplor.android.challenge.R
import com.xplor.android.challenge.repository.models.Pokemon
import com.xplor.android.challenge.ui.MainViewModel
import com.xplor.android.challenge.utils.ApiState
import javax.inject.Inject

class ComposeActivity : ComponentActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as App).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContent {
            //Text(text = "Hello World from compose")

            PokemonListScreen(viewModel)
        }

    }
}
@Composable
fun PokemonListScreen(viewModel: MainViewModel) {
    val pokemonListState by viewModel.pokedex.collectAsState()

    when (pokemonListState) {
        is ApiState.Loading -> {
            // Show loading indicator
        }
        is ApiState.Success -> {
            val pokemonList = (pokemonListState as ApiState.Success<List<Pokemon>>).data
            LazyColumn {
                items(pokemonList) { pokemon ->
                    PokemonListItem(pokemon)
                }
            }
        }
        is ApiState.Error -> {
            val errorState = pokemonListState as ApiState.Error
            // Show error message
        }
    }
}

@Composable
fun PokemonListItem(pokemon: Pokemon) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
       // elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_star_24), // Replace with actual image resource
                contentDescription = "Pokemon Image",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = pokemon.name, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Entry Number: ${pokemon.entryNumber}")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (pokemon.isFavorite) "Favorite" else "Not Favorite",
                    color = if (pokemon.isFavorite) Color.Green else Color.Red,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

