package com.example.productcatalog

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.productcatalog.presentation.detail.ProductDetailScreen
import com.example.productcatalog.presentation.list.ProductListScreen
import com.example.productcatalog.ui.theme.ProductCatalogTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProductCatalogTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "productList") {
                        composable("productList") {
                            ProductListScreen(
                                onProductClick = { productId ->
                                    navController.navigate("productDetail/$productId")
                                }
                            )
                        }
                        composable("productDetail/{productId}") { backStackEntry ->
                            val productId = backStackEntry.arguments?.getString("productId")?.toIntOrNull() ?: -1
                            ProductDetailScreen(
                                productId = productId,
                                onBackClick = { navController.popBackStack() }
                            )
                        }
                    }
                }
            }
        }
    }
}