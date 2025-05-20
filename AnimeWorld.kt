package com.animeworld

import com.lazycode.lazystream.model.*
import com.lazycode.lazystream.plugin.*

class AnimeWorld : Plugin() {
    override fun load(request: Request): Response {
        val data = listOf(
            Item(
                name = "One Piece",
                url = "https://anime.com/onepiece/episode1.m3u8",
                image = "https://example.com/images/onepiece.jpg",
                headers = mapOf("Referer" to "https://anime.com/")
            ),
            Item(
                name = "Naruto",
                url = "https://anime.com/naruto/episode1.m3u8",
                image = "https://example.com/images/naruto.jpg"
            )
        )

        return Response(
            data = data,
            status = Status.Success
        )
    }
}