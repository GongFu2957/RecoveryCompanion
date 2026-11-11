package com.gongfu.recoverycompanion.logs.presentation.add_edit_logentry.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.gongfu.recoverycompanion.R
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType
import com.gongfu.recoverycompanion.logs.domain.model.OutcomeType.entries
import com.gongfu.recoverycompanion.logs.presentation.utils.label
import com.gongfu.recoverycompanion.ui.theme.RecoveryCompanionTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutcomeDropdownMenu(
    selected: OutcomeType,
    onOutcomeSelected: (OutcomeType) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = it },
        modifier = modifier,
    ) {
        OutlinedTextField(
            value = selected.label(),
            onValueChange = { },
            readOnly = true,
            singleLine = true,
            label = { Text(stringResource(R.string.add_log_healthy_outcome))},
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            entries.forEach { outcome ->
                DropdownMenuItem(
                    text = { outcome.label() },
                    onClick = {
                        onOutcomeSelected(outcome)
                        expanded = false
                    },
                    leadingIcon =
                        if (outcome == selected) {
                            { Icon(Icons.Default.Check, contentDescription = null) }
                        } else null,
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding
                )
            }
        }

    }
}

@Preview
@Composable
private fun PreviewOutcomeDropdownMenu() {
    var selected by remember { mutableStateOf(OutcomeType.SLIP) }

    RecoveryCompanionTheme { 
        OutcomeDropdownMenu(
            selected = selected,
            onOutcomeSelected = { selected = it },
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}