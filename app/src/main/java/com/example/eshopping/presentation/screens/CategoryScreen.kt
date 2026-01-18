package com.example.eshopping.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.eshopping.R
import com.example.eshopping.presentation.navigation.Routes
import kotlinx.coroutines.launch

data class CategoryItem(
    val name: String,
    val iconRes: Int,
    val categoryName: String = ""
)

data class CategorySection(
    val category: CategoryItem,
    val products: List<CategoryItem>
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(navController: NavController) {
    val categories = remember {
        listOf(
            CategoryItem("Electronics", R.drawable.ic_launcher_foreground, "Electronics"),
            CategoryItem("Clothing", R.drawable.ic_launcher_foreground, "Clothing"),
            CategoryItem("Home", R.drawable.ic_launcher_foreground, "Home"),
            CategoryItem("Beauty", R.drawable.ic_launcher_foreground, "Beauty"),
            CategoryItem("Sports", R.drawable.ic_launcher_foreground, "Sports"),
            CategoryItem("Toys", R.drawable.ic_launcher_foreground, "Toys"),
            CategoryItem("Furniture", R.drawable.ic_launcher_foreground, "Furniture"),
            CategoryItem("Jewelry", R.drawable.ic_launcher_foreground, "Jewelry")
        )
    }

    val allProducts = remember {
        listOf(
            CategoryItem("TV", R.drawable.ic_launcher_foreground, "Electronics"),
            CategoryItem("Laptop", R.drawable.ic_launcher_foreground, "Electronics"),
            CategoryItem("Mobile", R.drawable.ic_launcher_foreground, "Electronics"),
            CategoryItem("Shirt", R.drawable.ic_launcher_foreground, "Clothing"),
            CategoryItem("Dress", R.drawable.ic_launcher_foreground, "Clothing"),
            CategoryItem("Jeans", R.drawable.ic_launcher_foreground, "Clothing"),
            CategoryItem("Sofa", R.drawable.ic_launcher_foreground, "Furniture"),
            CategoryItem("Table", R.drawable.ic_launcher_foreground, "Furniture"),
            CategoryItem("Chair", R.drawable.ic_launcher_foreground, "Furniture")
        )
    }

    val categoryIndexMap = remember { mutableMapOf<String, Int>() }

    val sections = remember(categories, allProducts) {
        var itemIndex = 0
        categories.map { category ->
            categoryIndexMap[category.name] = itemIndex
            val filteredProducts = allProducts.filter { it.categoryName == category.name }
            // Header + Grid
            itemIndex += 2 
            CategorySection(category = category, products = filteredProducts)
        }
    }

    var selectedCategory by remember { mutableStateOf(categories[0]) }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(selectedCategory) {
        categoryIndexMap[selectedCategory.name]?.let { index ->
            coroutineScope.launch {
                listState.animateScrollToItem(index)
            }
        }
    }

    Scaffold(
        modifier = Modifier.windowInsetsPadding(WindowInsets.statusBars),
        containerColor = Color.White,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Categories",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchScreen) }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = { navController.navigate(Routes.WishListScreen) }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_launcher_foreground),
                            contentDescription = "Wishlist",
                            tint = Color.Black,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            CategorySidebar(
                categories = categories,
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .padding(horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                sections.forEach { section ->
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp, bottom = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = section.category.name,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            HorizontalDivider(
                                modifier = Modifier.weight(1f),
                                thickness = 1.dp,
                                color = Color.LightGray.copy(alpha = 0.5f)
                            )
                            IconButton(
                                onClick = { 
                                    navController.navigate(Routes.EachCategoryItemsScreen(section.category.name)) 
                                },
                                modifier = Modifier.size(24.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                    contentDescription = "See All",
                                    modifier = Modifier.size(16.dp),
                                    tint = Color.Gray
                                )
                            }
                        }
                    }

                    item {
                        val gridHeight = if (section.products.isEmpty()) 0.dp else {
                            val rows = (section.products.size + 2) / 3
                            (rows * 120).dp // Approx height per item
                        }
                        
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(3),
                            modifier = Modifier
                                .fillMaxWidth()
                                .heightIn(max = gridHeight),
                            verticalArrangement = Arrangement.spacedBy(12.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            userScrollEnabled = false
                        ) {
                            items(section.products) { product ->
                                ProductGridItem(product = product) {
                                    // Handle product click
                                }
                            }
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

@Composable
fun ProductGridItem(product: CategoryItem, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(70.dp)
                .clip(CircleShape)
                .background(Color(0xFFF0F0F0)),
            contentAlignment = Alignment.Center
        ) {
            Image(
                painter = painterResource(id = product.iconRes),
                contentDescription = product.name,
                modifier = Modifier.size(40.dp),
                contentScale = ContentScale.Fit
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = product.name,
            fontSize = 11.sp,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Color.DarkGray
        )
    }
}

@Composable
fun CategorySidebar(
    categories: List<CategoryItem>,
    selectedCategory: CategoryItem,
    onCategorySelected: (CategoryItem) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .width(90.dp)
            .fillMaxHeight()
            .background(Color(0xFFF8F8F8))
    ) {
        items(categories) { category ->
            val isSelected = category == selectedCategory
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onCategorySelected(category) }
                    .background(if (isSelected) Color.White else Color.Transparent)
                    .padding(vertical = 14.dp, horizontal = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (isSelected) {
                    Box(
                        modifier = Modifier
                            .width(4.dp)
                            .height(24.dp)
                            .clip(RoundedCornerShape(topEnd = 4.dp, bottomEnd = 4.dp))
                            .background(MaterialTheme.colorScheme.primary)
                    )
                }
                Text(
                    text = category.name,
                    fontSize = 12.sp,
                    fontWeight = if (isSelected) FontWeight.ExtraBold else FontWeight.Medium,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Gray,
                    modifier = Modifier.padding(start = 12.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
