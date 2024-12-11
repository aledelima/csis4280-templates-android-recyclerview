package br.com.solanches.recycler_view.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    var imageResId: Int,
    var name: String,
    var price: Double
) : Parcelable
