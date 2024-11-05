package com.example.laboratorio9

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import coil.compose.rememberImagePainter
import com.example.laboratorio9.ui.theme.Laboratorio9Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Laboratorio9Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    ProgPrincipal9(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun ProgPrincipal9(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    Scaffold(
        topBar = { BarraSuperior() },
        bottomBar = { BarraInferior(navController) },
        content = { paddingValues -> Contenido(paddingValues, navController) }
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
            icon = { Icon(Icons.Outlined.Favorite, contentDescription = "Pokemon") },
            label = { Text("Pokemon") },
            selected = navController.currentDestination?.route == "pokemon",
            onClick = { navController.navigate("pokemon") }
        )
    }
}

@Composable
fun Contenido(
    pv: PaddingValues,
    navController: NavHostController
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
            composable("pokemon") { ScreenPokemon(navController) }
            composable("pokemonVer/{name}", arguments = listOf(
                navArgument("name") { type = NavType.StringType }
            )) {
                ScreenPokemonDetail(navController, it.arguments!!.getString("name")!!)
            }
        }
    }
}

@Composable
fun ScreenInicio() {
    Text("INICIO")
}

@Composable
fun ScreenPokemon(navController: NavHostController) {
    val viewModel: PokeViewModel = viewModel()
    val pokemonList by remember { mutableStateOf(viewModel.pokemonList) }

    LazyColumn {
        items(pokemonList) { item ->
            Row(modifier = Modifier.padding(8.dp)) {
                Text(text = item.name, Modifier.weight(0.7f))
                IconButton(
                    onClick = {
                        navController.navigate("pokemonVer/${item.name}")
                        Log.e("POKEMON", "Name = ${item.name}")
                    },
                    Modifier.weight(0.1f)
                ) {
                    Icon(imageVector = Icons.Outlined.Search, contentDescription = "Ver")
                }
            }
        }
    }
}

@Composable
fun ScreenPokemonDetail(navController: NavHostController, name: String) {
    val viewModel: PokeViewModel = viewModel()
    var pokemon by remember { mutableStateOf<Pokemon?>(null) }

    LaunchedEffect(name) {
        viewModel.fetchPokemonByName(name)
        pokemon = viewModel.selectedPokemon
    }

    Column(
        Modifier
            .padding(8.dp)
            .fillMaxSize()
    ) {
        if (pokemon != null) {
            Text(text = "Name: ${pokemon!!.name}")
            Text(text = "Height: ${pokemon!!.height}")
            Text(text = "Weight: ${pokemon!!.weight}")
            pokemon!!.sprites.frontDefault?.let {
                Image(painter = rememberImagePainter(it), contentDescription = "Pokemon Image")
            }
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