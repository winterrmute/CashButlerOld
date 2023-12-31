package com.wintermute.mobile.cashbutler.presentation.view.components.core.model

import java.util.UUID

/**
 * Represents a data layer representation of a checkbox item with title.
 *
 * @param id unique identifier for localizing in item groups or lists
 * @param title item text
 * @param isChecked item state
 * @param isEnabled item clickable state
 *
 * @author k.kosinski
 */
data class CheckBoxTextItemModel(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val isChecked: Boolean,
    val isEnabled: Boolean
)