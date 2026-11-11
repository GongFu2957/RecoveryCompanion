package com.gongfu.recoverycompanion.logs.presentation.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType

@Composable
internal fun OutcomeType.label(): String = when (this) {
    OutcomeType.SLIP -> stringResource(R.string.outcome_slip)
    OutcomeType.SUCCESS -> stringResource(R.string.outcome_success)
    OutcomeType.NEUTRAL -> stringResource(R.string.outcome_neutral)
}