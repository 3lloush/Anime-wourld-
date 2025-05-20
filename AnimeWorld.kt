class AnimeWorld : MainAPI() {
    override var mainUrl = "https://3loushdev.online"
    override var name = "Anime World"
    override var lang = "ar"

    override val hasMainPage = true

    override val supportedTypes = setOf(TvType.Anime, TvType.AnimeMovie)

    override suspend fun getMainPage(): HomePageResponse {
        return newHomePageResponse("الأنميات") {
            add(
                HomePageList(
                    "الأنميات",
                    listOf(
                        newAnimeSearchResponse("https://3loushdev.online/anime/one-piece")
                            .setTitle("One Piece")
                            .setPosterPos("https://3loushdev.online/wp-content/uploads/2023/07/one-piece.webp")
                            .setType(TvType.Anime)
                    )
                )
            )
        }
    }
}
