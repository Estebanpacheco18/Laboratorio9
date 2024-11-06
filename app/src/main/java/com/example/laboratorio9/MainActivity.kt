package com.example.laboratorio9

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import com.example.laboratorio9.ui.theme.Laboratorio9Theme

class MainActivity : ComponentActivity() {
    private val pokeViewModel: PostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio9Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProgPrincipal9(modifier = Modifier.padding(innerPadding), pokeViewModel)
                }
            }
        }
    }
}

@Composable
fun ProgPrincipal9(modifier: Modifier = Modifier, pokeViewModel: PostViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController) },
        content = { paddingValues -> Contenido(paddingValues, navController, pokeViewModel) }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperior() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "PokeAPI Access",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun BarraInferior(navController: NavHostController) {
    NavigationBar(
        containerColor = Color.LightGray
    ) {
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") },
            selected = navController.currentDestination?.route == "inicio",
            onClick = { navController.navigate("inicio") }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Outlined.Favorite, contentDescription = "Pokémon") },
            label = { Text("Pokémon") },
            selected = navController.currentDestination?.route == "pokemon",
            onClick = { navController.navigate("pokemon") }
        )
    }
}

@Composable
fun Contenido(
    pv: PaddingValues,
    navController: NavHostController,
    pokeViewModel: PostViewModel
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(pv)
    ) {
        NavHost(
            navController = navController,
            startDestination = "inicio"
        ) {
            composable("inicio") { ScreenInicio() }
            composable("pokemon") { ScreenPokemon(navController, pokeViewModel) }
            composable("pokemonVer/{name}", arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )) {
                val name = it.arguments!!.getString("name")!!
                pokeViewModel.fetchPokemonByName(name)
                ScreenPokemonDetail(navController, pokeViewModel)
            }
        }
    }
}

@Composable
fun ScreenInicio() {
    Text("INICIO")
}

@Composable
fun ScreenPokemon(navController: NavHostController, pokeViewModel: PostViewModel) {
    val pokemonList by pokeViewModel.pokemonList.collectAsState()

    LazyColumn {
        items(pokemonList) { item ->
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = item.name, Modifier.weight(0.7f))
                IconButton(
                    onClick = {
                        navController.navigate("pokemonVer/${item.name}")
                    },
                    Modifier.weight(0.1f)
                ) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Ver")
                }
                IconButton(
                    onClick = {
                        pokeViewModel.deletePokemon(item.name)
                    },
                    Modifier.weight(0.1f)
                ) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Eliminar")
                }
            }
        }
    }
}

@Composable
fun ScreenPokemonDetail(navController: NavHostController, pokeViewModel: PostViewModel) {
    val pokemon by pokeViewModel.selectedPokemon.collectAsState()

    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        pokemon?.let {
            OutlinedTextField(
                value = it.name,
                onValueChange = { newName -> pokeViewModel.updatePokemon(it.name, it.copy(name = newName)) },
                label = { Text("Name") }
            )
            OutlinedTextField(
                value = it.height.toString(),
                onValueChange = { newHeight -> pokeViewModel.updatePokemon(it.name, it.copy(height = newHeight.toInt())) },
                label = { Text("Height") }
            )
            OutlinedTextField(
                value = it.weight.toString(),
                onValueChange = { newWeight -> pokeViewModel.updatePokemon(it.name, it.copy(weight = newWeight.toInt())) },
                label = { Text("Weight") }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Laboratorio9Theme {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}