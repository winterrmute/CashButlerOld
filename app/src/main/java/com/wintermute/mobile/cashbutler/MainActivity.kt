package com.wintermute.mobile.cashbutler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.wintermute.mobile.cashbutler.presentation.view.ScreenViews
import com.wintermute.mobile.cashbutler.presentation.view.finance.ExpenseView
import com.wintermute.mobile.cashbutler.presentation.view.finance.FinancialProfileWizardView
import com.wintermute.mobile.cashbutler.presentation.view.finance.IncomeView
import com.wintermute.mobile.cashbutler.ui.theme.CashButlerTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navHostController = rememberNavController()
            CashButlerTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(
                        navController = navHostController,
                        startDestination = ScreenViews.BUDGET.viewName,
                    ) {
                        composable(ScreenViews.BUDGET.viewName) {
//                            IncomeView(navHostController = navHostController)
                            FinancialProfileWizardView(navHostController = navHostController)
                        }
                        composable(ScreenViews.EXPENSES.viewName) {
                            ExpenseView()
                        }
                    }
                }
            }
        }
    }
}