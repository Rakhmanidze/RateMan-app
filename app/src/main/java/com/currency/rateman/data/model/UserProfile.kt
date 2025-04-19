package com.currency.rateman.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
data class UserProfile(
    val name: String = "",
    val surname: String = "",
    val numberOfKids: Int = 0,
) : Parcelable
