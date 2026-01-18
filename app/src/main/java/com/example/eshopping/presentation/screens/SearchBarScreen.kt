package com.example.eshopping.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBarScreen(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    var query by remember { mutableStateOf("") }
    var active by remember { mutableStateOf(true) }
    
    val productState by viewModel.getAllProductsState.collectAsState()
    val allProducts = productState.userData ?: emptyList()

    // Simple filtering logic based on the query
    val filteredProducts = if (query.isEmpty()) {
        emptyList()
    } else {
        allProducts.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.category.contains(query, ignoreCase = true) 
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            SearchBar(
                modifier = Modifier.fillMaxWidth(),
                query = query,
                onQueryChange = { query = it },
                onSearch = { active = false },
                active = active,
                onActiveChange = { active = it },
                placeholder = { Text("Search products, brands and more") },
                leadingIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                trailingIcon = {
                    if (query.isNotEmpty()) {
                        IconButton(onClick = { query = "" }) {
                            Icon(Icons.Default.Clear, contentDescription = "Clear")
                        }
                    } else {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            ) {
                if (productState.isLoading) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp)
                    ) {
                        items(filteredProducts) { product ->
                            SearchSuggestionItem(
                                title = product.name,
                                category = product.category
                            ) {
                                navController.navigate(Routes.EachProductDetailScreen(product.productId))
                            }
                            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp), color = Color.LightGray.copy(alpha = 0.4f))
                        }
                        
                        if (query.isNotEmpty() && filteredProducts.isEmpty()) {
                            item {
                                Text(
                                    text = "No results found for \"$query\"",
                                    modifier = Modifier.padding(16.dp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SearchSuggestionItem(
    title: String,
    category: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.padding(end = 16.dp)
        )
        Column {
            Text(text = title, fontSize = 16.sp)
            Text(text = "in $category", fontSize = 12.sp, color = Color.Gray)
        }
    }
}
