package com.ajsnarr.peoplenotes.db

data class Color(
    val r: Int? = null,
    val g: Int? = null,
    val b: Int? = null,
    val a: Int? = null
): DBObject<com.ajsnarr.peoplenotes.data.Color> {
    companion object {
        fun fromAppObject(other: com.ajsnarr.peoplenotes.data.Color): Color {
            return Color(
                r = other.r,
                g = other.g,
                b = other.b,
                a = other.a
            )
        }
    }

    override fun toAppObject(): com.ajsnarr.peoplenotes.data.Color {
        return com.ajsnarr.peoplenotes.data.Color(
            r = this.r!!,
            g = this.g!!,
            b = this.b!!,
            a = this.a
        )
    }
}
