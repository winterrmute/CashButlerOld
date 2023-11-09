package com.wintermute.mobile.cashbutler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.wintermute.mobile.cashbutler.presentation.view.ScreenViewNames
import com.wintermute.mobile.cashbutler.presentation.view.finance.CashFlowView
import com.wintermute.mobile.cashbutler.presentation.view.finance.wizard.FinancialProfileWizardView
import com.wintermute.mobile.cashbutler.ui.theme.CashButlerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            CashButlerTheme(darkTheme = false) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = ScreenViewNames.CASH_FLOW.name,
                    ) {
                        composable(
                            route = "${ScreenViewNames.WIZARD.name}/{page}",
                            arguments = listOf(navArgument("page") { type = NavType.IntType })
                        ) {
                            val desiredPage = it.arguments?.getInt("page") ?: 0
                            FinancialProfileWizardView(
                                navHostController = navHostController,
                                page = desiredPage
                            )
                        }
                        composable(ScreenViewNames.DASHBOARD.name) {
                        }
                        composable(ScreenViewNames.CASH_FLOW.name) {
                            CashFlowView()
                        }
                    }
                }
            }
        }
    }
}