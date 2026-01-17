package com.fpstudio.stretchreminder.ui.screen.premium

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.screen.premium.components.BenefitsList
import com.fpstudio.stretchreminder.ui.screen.premium.components.PremiumHeader
import com.fpstudio.stretchreminder.ui.screen.premium.components.SubscriptionPlanCardWithBilling
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.screen.premium.contract.PremiumScreenContract.SubscriptionPlan
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import org.koin.androidx.compose.koinViewModel
import androidx.compose.ui.res.stringResource
import com.fpstudio.stretchreminder.R
// ...
// I will not apply changes here as I need to update strings.xml first.
// See next tool call.


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PremiumScreen(
    viewModel: PremiumScreenViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {},
    onNavigateToSuccess: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    
    // Set activity reference
    DisposableEffect(context) {
        if (context is Activity) {
            viewModel.setActivity(context)
        }
        onDispose {
            // Cleanup handled in ViewModel
        }
    }
    
    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                SideEffect.NavigateBack -> onNavigateBack()
                
                SideEffect.ShowPurchaseSuccess -> {
                    onNavigateToSuccess()
                }
                
                is SideEffect.ShowPurchaseError -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.premium_purchase_failed, effect.message),
                        duration = SnackbarDuration.Long
                    )
                }
                

                
                SideEffect.ShowRestoreSuccess -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.premium_restore_success),
                        duration = SnackbarDuration.Short
                    )
                }
                
                is SideEffect.ShowRestoreError -> {
                    snackbarHostState.showSnackbar(
                        message = context.getString(R.string.premium_restore_failed, effect.message),
                        duration = SnackbarDuration.Long
                    )
                }
            }
        }
    }
    
    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = stringResource(R.string.premium_screen_title),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleIntent(Intent.Close) }) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = stringResource(R.string.action_close)
                        )
                    }
                },
                actions = {
                    TextButton(
                        onClick = { viewModel.handleIntent(Intent.RestorePurchases) },
                        enabled = !uiState.isLoading
                    ) {
                        Text(
                            text = stringResource(R.string.action_restore),
                            color = if (uiState.isLoading) Color.Gray else TurquoiseAccent,
                            fontWeight = FontWeight.Medium
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        },
        bottomBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Button(
                    onClick = { viewModel.handleIntent(Intent.StartTrial) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TurquoiseAccent
                    ),
                    shape = RoundedCornerShape(16.dp),
                    enabled = !uiState.isLoading && uiState.billingConnected
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White
                        )
                    } else {
                        Text(
                            text = stringResource(R.string.premium_start_trial_btn),
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                
                Text(
                    text = stringResource(R.string.premium_billing_disclaimer),
                    style = MaterialTheme.typography.bodySmall,
                    fontSize = 10.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Spacer(modifier = Modifier.height(4.dp))
            
            // Premium Header
            PremiumHeader()
            
            // Benefits List
            BenefitsList()
            
            // Subscription Plans
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SubscriptionPlanCardWithBilling(
                    plan = SubscriptionPlan.ANNUAL,
                    packageInfo = uiState.annualPackage,
                    isSelected = uiState.selectedPlan == SubscriptionPlan.ANNUAL,
                    onPlanSelected = { 
                        viewModel.handleIntent(Intent.SelectPlan(SubscriptionPlan.ANNUAL))
                    }
                )
                
                SubscriptionPlanCardWithBilling(
                    plan = SubscriptionPlan.MONTHLY,
                    packageInfo = uiState.monthlyPackage,
                    isSelected = uiState.selectedPlan == SubscriptionPlan.MONTHLY,
                    onPlanSelected = { 
                        viewModel.handleIntent(Intent.SelectPlan(SubscriptionPlan.MONTHLY))
                    }
                )
            }
            
            // Show error if billing not connected
            if (!uiState.billingConnected && !uiState.isLoading) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    )
                ) {
                    Text(
                        text = stringResource(R.string.premium_error_billing),
                        modifier = Modifier.padding(16.dp),
                        color = Color(0xFFC62828),
                        fontSize = 14.sp
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
