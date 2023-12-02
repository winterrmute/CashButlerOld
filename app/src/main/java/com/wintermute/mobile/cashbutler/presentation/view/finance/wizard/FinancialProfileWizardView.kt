package com.wintermute.mobile.cashbutler.presentation.view.finance.wizard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.wintermute.mobile.cashbutler.presentation.view.ScreenViewNames
import com.wintermute.mobile.cashbutler.presentation.view.finance.CashFlowView

@Composable
fun FinancialProfileWizardView(
    navHostController: NavHostController,
    page: Int
) {
    var currentPage by rememberSaveable { mutableIntStateOf(page) }
    val wizardSteps = 3

    val name: String? = null
    if (name == "John") {
        // Code
    }


    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxSize()
        ) {
            when (currentPage) {
                WizardSteps.CASH_FLOW.ordinal -> CashFlowView()
                WizardSteps.EXPENSES.ordinal -> ExpenseView()
                WizardSteps.FINANCIAL_GOALS.ordinal -> FinancialGoalsView {
                    navHostController.navigate(ScreenViewNames.DASHBOARD.name)
                }
            }
        }

        BottomNavigationBar(
            steps = wizardSteps,
            selectedStep = currentPage,
            onStepIndicatorClick = {
                currentPage = it
            },
            onBack = {
                if (currentPage > 0) {
                    currentPage--
                }
            },
            onForward = {

                if (currentPage < wizardSteps - 1) {
                    currentPage++
                }
            })

        BackHandler {
            println()
            if (currentPage > 0) {
                currentPage--
            } else {
                navHostController.popBackStack()
            }
        }
    }
}

@Composable
fun StepIndicator(selected: Boolean, onClick: () -> Unit) {
    val color = if (selected) MaterialTheme.colorScheme.primary else Color.Gray

    Box(
        modifier = Modifier
            .size(16.dp)
            .background(color = color, shape = CircleShape)
            .clickable { onClick() }
    )
}

@Composable
fun BottomNavigationBar(
    steps: Int,
    selectedStep: Int,
    onStepIndicatorClick: (targetStep: Int) -> Unit,
    onBack: () -> Unit,
    onForward: () -> Unit
) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        IconButton(
            onClick = { onBack() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back"
            )
        }

        repeat(steps) {
            Box(modifier = Modifier.padding(16.dp)) {
                StepIndicator(selected = it == selectedStep) {
                    onStepIndicatorClick(it)
                }
            }
        }

        IconButton(
            onClick = { onForward() }
        ) {
            Icon(
                imageVector = Icons.Default.ArrowForward,
                contentDescription = "Forward"
            )
        }
    }
}