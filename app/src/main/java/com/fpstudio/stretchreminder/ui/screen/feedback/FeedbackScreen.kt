package com.fpstudio.stretchreminder.ui.screen.feedback

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.FeedbackSubject
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.Intent
import com.fpstudio.stretchreminder.ui.screen.feedback.contract.FeedbackScreenContract.SideEffect
import com.fpstudio.stretchreminder.ui.theme.TurquoiseAccent
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedbackScreen(
    viewModel: FeedbackScreenViewModel = koinViewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    var showSuccessDialog by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.sideEffect.collect { effect ->
            when (effect) {
                is SideEffect.ShowSuccess -> {
                    // Show success dialog
                    showSuccessDialog = true
                }

                SideEffect.NavigateBack -> onNavigateBack()
            }
        }
    }

    // Success Dialog
    if (showSuccessDialog) {
        com.fpstudio.stretchreminder.ui.screen.feedback.components.FeedbackSuccessDialog(
            onDismiss = {
                showSuccessDialog = false
                onNavigateBack()
            }
        )
    }

    Scaffold(
        containerColor = Color(0xFFF5F5F5),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Send Feedback",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { viewModel.handleIntent(Intent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFF5F5F5)
                )
            )
        },
        bottomBar = {
            Column(modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)) {
                Button(
                    onClick = { viewModel.handleIntent(Intent.SubmitFeedback) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TurquoiseAccent
                    ),
                    shape = RoundedCornerShape(28.dp),
                    enabled = !uiState.isLoading
                ) {
                    if (uiState.isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(24.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Send Feedback",
                                color = Color.White,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Main content card
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color.White)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icon
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .clip(RoundedCornerShape(26.dp))
                        .border(2.dp, color = TurquoiseAccent, shape = RoundedCornerShape(26.dp))
                        .background(Color(0xFFD1F4E8)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.MailOutline,
                        contentDescription = null,
                        tint = TurquoiseAccent,
                        modifier = Modifier.size(36.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Header text
                Text(
                    text = "We'd love to hear from you",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = "Your feedback helps us improve the stretching experience for everyone.",
                    fontSize = 14.sp,
                    color = Color(0xFF9E9E9E),
                    textAlign = TextAlign.Center,
                    lineHeight = 20.sp
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Subject section - label and optional on same line
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Subject",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(0xFF616161)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "(Optional)",
                        fontSize = 13.sp,
                        color = Color(0xFFBDBDBD)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Subject chips
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SubjectChip(
                            subject = FeedbackSubject.BUG_REPORT,
                            isSelected = uiState.selectedSubject == FeedbackSubject.BUG_REPORT,
                            onClick = { viewModel.handleIntent(Intent.SelectSubject(FeedbackSubject.BUG_REPORT)) },
                            modifier = Modifier.weight(1f)
                        )
                        SubjectChip(
                            subject = FeedbackSubject.FEATURE_REQUEST,
                            isSelected = uiState.selectedSubject == FeedbackSubject.FEATURE_REQUEST,
                            onClick = { viewModel.handleIntent(Intent.SelectSubject(FeedbackSubject.FEATURE_REQUEST)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        SubjectChip(
                            subject = FeedbackSubject.GENERAL_FEEDBACK,
                            isSelected = uiState.selectedSubject == FeedbackSubject.GENERAL_FEEDBACK,
                            onClick = { viewModel.handleIntent(Intent.SelectSubject(FeedbackSubject.GENERAL_FEEDBACK)) },
                            modifier = Modifier.weight(1f)
                        )
                        SubjectChip(
                            subject = FeedbackSubject.OTHER,
                            isSelected = uiState.selectedSubject == FeedbackSubject.OTHER,
                            onClick = { viewModel.handleIntent(Intent.SelectSubject(FeedbackSubject.OTHER)) },
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                // Custom subject field (only shown when "Other" is selected)
                if (uiState.selectedSubject == FeedbackSubject.OTHER) {
                    Spacer(modifier = Modifier.height(4.dp))
                    OutlinedTextField(
                        value = uiState.customSubject,
                        onValueChange = { viewModel.handleIntent(Intent.UpdateCustomSubject(it)) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "TAG",
                                modifier = Modifier
                                    .size(20.dp)
                                    .offset(x = 6.dp),
                                tint = Color(0xFFBDBDBD)
                            )
                        },
                        placeholder = {
                            Text(
                                "e.g. Feature Suggestion",
                                color = Color(0xFFBDBDBD),
                                fontSize = 14.sp
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                        shape = RoundedCornerShape(24.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = Color(0xFFf7f9fa),
                            focusedContainerColor = Color(0xFFf7f9fa),
                            focusedBorderColor = Color.Transparent,
                            unfocusedBorderColor = Color.Transparent
                        )
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Message section
                Text(
                    text = "Message",
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF616161),
                    modifier = Modifier.align(Alignment.Start)
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedTextField(
                    value = uiState.message,
                    onValueChange = { viewModel.handleIntent(Intent.UpdateMessage(it)) },
                    placeholder = {
                        Text(
                            "Tell us what you think...",
                            color = Color(0xFFBDBDBD),
                            fontSize = 14.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        unfocusedContainerColor = Color(0xFFf7f9fa),
                        focusedContainerColor = Color(0xFFf7f9fa),
                        focusedBorderColor = Color.Transparent,
                        unfocusedBorderColor = Color.Transparent
                    ),
                    maxLines = 10
                )
            }

            // Error message
            if (uiState.error != null) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFFFFEBEE)
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = uiState.error ?: "",
                            color = Color(0xFFC62828),
                            fontSize = 14.sp,
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(onClick = { viewModel.handleIntent(Intent.DismissError) }) {
                            Icon(
                                painter = painterResource(android.R.drawable.ic_menu_close_clear_cancel),
                                contentDescription = "Dismiss",
                                tint = Color(0xFFC62828)
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SubjectChip(
    subject: FeedbackSubject,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    FilterChip(
        selected = isSelected,
        onClick = onClick,
        label = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(id = subject.iconRes),
                    contentDescription = subject.displayName,
                    modifier = Modifier.size(20.dp),
                    tint = if (isSelected) Color.White else Color(0xFF424242)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = subject.displayName,
                    fontSize = 13.sp,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold
                )
            }
        },
        modifier = modifier.height(50.dp),
        shape = RoundedCornerShape(25.dp),
        colors = FilterChipDefaults.filterChipColors(
            selectedContainerColor = Color(0xFF2D2D2D),
            selectedLabelColor = Color.White,
            containerColor = Color(0xFFf7f9fa),
            labelColor = Color(0xFF424242)
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = isSelected,
            borderColor = Color.Transparent,
            selectedBorderColor = Color(0xFF2D2D2D),
            borderWidth = 0.dp,
            selectedBorderWidth = 1.dp
        )
    )
}
