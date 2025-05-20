
package com.animeworld

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.Jsoup

class AnimeWorld : MainAPI() {
    override var name = "Anime World"
    override var mainUrl = "https://animelek.me"
    override val supportedTypes = setOf(TvType.Anime)
    override val lang = "ar"

    override suspend fun getMainPage(page: Int, request: MainPageRequest): HomePageResponse {
        val res = app.get(mainUrl).document
        val homeList = res.select(".anime-card a").map {
            val title = it.selectFirst(".anime-card-title")?.text()?.trim().orEmpty()
            val poster = it.selectFirst("img")?.attr("data-src").orEmpty()
            val link = fixUrl(it.attr("href"))
            newAnimeSearchResponse(title, link, TvType.Anime) {
                this.posterUrl = poster
            }
        }
        return HomePageResponse(listOf(HomePageList("الأنميات المضافة حديثاً", homeList)))
    }

    override suspend fun load(url: String): LoadResponse {
        val doc = app.get(url).document
        val title = doc.selectFirst("h1")?.text().orEmpty()
        val poster = doc.selectFirst(".anime-thumbnail img")?.attr("data-src").orEmpty()
        val description = doc.selectFirst(".description")?.text().orEmpty()

        val episodes = doc.select(".episodes-list a").map {
            val name = it.text()
            val link = fixUrl(it.attr("href"))
            Episode(link, name)
        }.reversed()

        return newAnimeLoadResponse(title, url, TvType.Anime) {
            this.posterUrl = poster
            this.plot = description
            this.episodes = episodes
        }
    }

    override suspend fun loadLinks(
        data: String,
        isCasting: Boolean,
        subtitleCallback: (SubtitleFile) -> Unit,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        val doc = app.get(data).document
        val iframe = doc.selectFirst("iframe")?.attr("src") ?: return false
        val embedUrl = fixUrl(iframe)
        // هذه الخطوة تعتمد على سيرفرات التشغيل ويمكن تطويرها لاحقاً
        callback.invoke(
            ExtractorLink("AnimeLek", "AnimeLek", embedUrl, "", Qualities.Unknown.value)
        )
        return true
    }
}
