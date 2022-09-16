package com.example.codapizza.model

import android.os.Parcelable
import com.example.codapizza.model.ToppingPlacement.*
import kotlinx.parcelize.Parcelize

@Parcelize // Parcelize so it can be saved in a bundle
data class Pizza(
    val toppings: Map<Topping, ToppingPlacement> = emptyMap()
) : Parcelable {
    // This pizza costs 9.99 as standard, plus the amount of toppings
    val price: Double
        get() = 9.99 + toppings.asSequence()
            .sumOf { (_, toppingPlacement) ->
                when (toppingPlacement) {
                    Left, Right -> 0.5
                    All -> 1.0
                }
            }

    fun withTopping(topping: Topping, placement: ToppingPlacement?): Pizza {
        // Returns a copy of the toppings map, adjusted based on placement value
        return copy(
            toppings = if (placement == null) {
                toppings - topping
            } else {
                toppings + (topping to placement)
            }
        )
    }
}