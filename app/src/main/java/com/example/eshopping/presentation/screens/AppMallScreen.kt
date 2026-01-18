package com.example.eshopping.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.example.eshopping.domain.models.ProductDataModel
import com.example.eshopping.presentation.navigation.Routes
import com.example.eshopping.presentation.viewModel.ShoppingAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppMallScreenUi(
    navController: NavController,
    viewModel: ShoppingAppViewModel = hiltViewModel()
) {
    val homeState by viewModel.homeScreen.collectAsState()
    val suggestedProductsState by viewModel.getAllSuggestedProductsState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadHomeScreenData()
        viewModel.getAllSuggestedProducts()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Storefront,
                            contentDescription = null,
                            tint = Color(0xFF6200EE)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Mall", fontWeight = FontWeight.ExtraBold)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate(Routes.SearchScreen) }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White)
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFF8F8F8))
        ) {
            if (homeState.isLoading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    // Exclusive Mall Banner
                    MallFeaturedBanner()

                    // Shop by Brand / Suggested Brands
                    SectionHeader(
                        "Featured Products",
                        onSeeAll = { navController.navigate(Routes.SeeAllProductsScreen) })
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
//                        items(homeState.products) { product ->
//                            MallProductCard(product) {
//                                navController.navigate(Routes.EachProductDetailScreen(product.))
//                            }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Mall Categories Shortcut
                SectionHeader(
                    "Mall Categories",
                    onSeeAll = { navController.navigate(Routes.AllCategoriesScreen) })
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
//                    items(homeState.categories = emptyList() { category ->
//                        MallCategoryItem(
//                            name ="Add name here",
//                            image = " Add Image here"
//                        )
//                    }
//                    )
                }
            }

                Spacer(modifier = Modifier.height(24.dp))

                // Premium / Suggested Section
                SectionHeader("Premium Selection", onSeeAll = {})
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    suggestedProductsState.userData?.take(4)?.forEach { product ->
                        PremiumProductItem(product) {
                            navController.navigate(Routes.EachProductDetailScreen(product.productId))
                        }
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))
            }
        }
    }



@Composable
fun MallFeaturedBanner() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(180.dp),
        shape = RoundedCornerShape(16.dp)
    ) {
        Box {
            // Placeholder for Mall Banner Image
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.horizontalGradient(
                            listOf(
                                Color(0xFF6200EE),
                                Color(0xFF03DAC5)
                            )
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .align(Alignment.CenterStart)
            ) {
                Text(
                    "GRAND MALL",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
                Text(
                    "Premium Brands\nUp to 70% Off",
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 24.sp
                )
                Spacer(modifier = Modifier.height(8.dp))
                Surface(color = Color.White, shape = RoundedCornerShape(4.dp)) {
                    Text(
                        "SHOP NOW",
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun SectionHeader(title: String, onSeeAll: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = title, fontSize = 18.sp, fontWeight = FontWeight.Bold)
        Text(
            text = "See All",
            fontSize = 14.sp,
            color = Color(0xFF6200EE),
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.clickable { onSeeAll() }
        )
    }
}

@Composable
fun MallProductCard(product: Int, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(150.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column {
            AsyncImage(
                model = "",
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(8.dp)) {
//                Text(text = product.name, fontSize = 12.sp, maxLines = 1, fontWeight = FontWeight.Medium)
//                Text(text = "₹${product.finalPrice}", fontSize = 14.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun MallCategoryItem(name: String, image: String, onClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(80.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = image,
            contentDescription = null,
            modifier = Modifier
                .size(60.dp)
                .clip(CircleShape)
                .background(Color.White),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = name, fontSize = 11.sp, maxLines = 1, color = Color.Gray)
    }
}

@Composable
fun PremiumProductItem(product: ProductDataModel, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
            AsyncImage(
                model = product.image,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = product.name, fontWeight = FontWeight.Bold, fontSize = 15.sp)
                Text(text = "Exclusive Premium Collection", color = Color.Gray, fontSize = 12.sp)
                Text(
                    text = "₹${product.finalPrice}",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 16.sp,
                    color = Color(0xFF6200EE)
                )
            }
        }
    }
}
