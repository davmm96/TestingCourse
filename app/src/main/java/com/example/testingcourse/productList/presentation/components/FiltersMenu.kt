package com.example.testingcourse.productList.presentation.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testingcourse.R
import com.example.testingcourse.productList.domain.model.SortOption
import com.example.testingcourse.productList.domain.model.SortOption.DISCOUNT
import com.example.testingcourse.productList.domain.model.SortOption.PRICE_ASC
import com.example.testingcourse.productList.domain.model.SortOption.PRICE_DESC
import com.example.testingcourse.productList.presentation.ProductListUiState

@Composable
fun FiltersMenu(
    modifier: Modifier = Modifier,
    state: ProductListUiState.Success,
    onCategorySelected: (category: String?) -> Unit,
    onSortSelected: (sort: SortOption) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Column(
            Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(stringResource(R.string.categories_label))

            Row(
                Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = state.selectedCategory == null,
                    onClick = { onCategorySelected(null) },
                    label = {
                        Text(
                            stringResource(R.string.all_categories),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
                state.categories.forEach { category ->
                    FilterChip(
                        selected = category.equals(state.selectedCategory, ignoreCase = true),
                        onClick = { onCategorySelected(category) },
                        label = { Text(category, style = MaterialTheme.typography.labelSmall) }
                    )
                }
            }

            HorizontalDivider()

            Row(
                Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = state.sortOption == PRICE_ASC,
                    onClick = { onSortSelected(PRICE_ASC) },
                    label = {
                        Text(
                            stringResource(R.string.price_sorting_asc),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )

                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = state.sortOption == PRICE_DESC,
                    onClick = { onSortSelected(PRICE_DESC) },
                    label = {
                        Text(
                            stringResource(R.string.price_sorting_desc),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )

                FilterChip(
                    modifier = Modifier.weight(1f),
                    selected = state.sortOption == DISCOUNT,
                    onClick = { onSortSelected(DISCOUNT) },
                    label = {
                        Text(
                            stringResource(R.string.discount_sorting),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                )
            }
        }
    }
}