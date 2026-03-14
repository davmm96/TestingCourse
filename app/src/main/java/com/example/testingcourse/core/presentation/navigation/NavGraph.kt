package com.example.testingcourse.core.presentation.navigation

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.sp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay

@Composable
fun NavGraph() {
    val backstack = rememberNavBackStack(Screen.ProductList)

    val entries = entryProvider<NavKey> {
        entry<Screen.ProductList> {
            Text("ProductList", fontSize = 30.sp)
        }
        entry<Screen.Cart> {
            Text("Cart", fontSize = 30.sp)
        }
        entry<Screen.Setting> {
            Text("Setting", fontSize = 30.sp)
        }
        entry<Screen.ProductDetail> {
            Text("ProductDetail", fontSize = 30.sp)
        }
    }

    NavDisplay(
        backStack = backstack,
        entryProvider = entries,
        onBack = { backstack.removeLastOrNull() }
    )

}