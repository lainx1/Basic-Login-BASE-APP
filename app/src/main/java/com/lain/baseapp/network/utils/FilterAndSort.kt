package com.lain.baseapp.network.utils

import com.lain.baseapp.network.enums.Sort
import java.util.HashMap

/**
 * This object contains a example of filters and sorts available.
 */
object FilterAndSort {
    /**
     * A example of a sort.
     */
    val sort: HashMap<Sort, String> = hashMapOf(
        Sort.A_Z to "a_z",
        Sort.Z_A to "z_a"
    )
}