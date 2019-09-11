package com.vibot.youtube

import com.vibot.youtube.binding.request.Create
import com.vibot.youtube.binding.request.Snippet
import com.vibot.youtube.binding.request.Status

data class VideoData(val title: String, val description: String, val keywords: List<String>, val category: Category) {

    fun toYoutubeCreateRequest(): Create {
        return Create().apply {
            snippet = Snippet().apply { title = this@VideoData.title; description = this@VideoData.description; tags = keywords; categoryId = category.youtubeId };
            status  = Status().apply { privacyStatus = "public" } }
    }
}

enum class Category(val youtubeId: Int, val youtubeName: String) {
    NEWS(25, "News & Politics"),
    ANIMATION(1, "Film & Animation"),
    VEHICLES(2, "Autos & Vehicles"),
    MUSIC(10, "Music"),
    PETS(15, "Pets & Animals"),
    SPORTS(17, "Sports"),
    TRAVELS(19, "Travel & Events"),
    GAMING(20, "Gaming"),
    PEOPLE(22, "People & Blogs"),
    COMEDY(23, "Comedy"),
    ENTERTAIMENT(24, "Entertainment"),
    STYLE(26, "Howto & Style"),
    EDUCATION(27, "Education"),
    SCIENCE(28, "Science & Technology")
}

