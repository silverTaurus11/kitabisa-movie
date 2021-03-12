package com.project.myapplication.utils

import com.project.myapplication.data.source.remote.model.BaseDetailResponse
import com.project.myapplication.data.source.remote.model.GenreEntity
import com.project.myapplication.data.source.remote.model.MovieEntity
import com.project.myapplication.data.source.remote.model.ReviewEntity

object DataDummy {

    fun getMovie(id: Int?): BaseDetailResponse.MovieDetailResponse
            = getMoviesDetailListDummy().find { id == it.id }?: BaseDetailResponse.MovieDetailResponse()

    fun getMoviesListDummy(): List<MovieEntity>{
        return getMoviesDetailListDummy().map {
            MovieEntity(it.id, it.name, it.description, it.rating, it.posterPath) }
    }

    private fun getMoviesDetailListDummy(): List<BaseDetailResponse.MovieDetailResponse>{
        val movieList = mutableListOf<BaseDetailResponse.MovieDetailResponse>()
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                1, "A Star Is Born",
                "A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.",
                3.8F, listOf(GenreEntity(1, "Action")), "EN", "2018", 136,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                2, "Alita: Battle Angel",
                "A deactivated cyborg's revived, but can't remember anything of her past and goes on a quest to find out who she is.",
                3.65F, listOf(GenreEntity(1, "Action")), "EN", "2019", 122,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                3, "Aquaman",
                "Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land.",
                3.4F, listOf(GenreEntity(1, "Action")), "EN", "2018", 143,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                4, "Bohemian Rhapsody",
                "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid",
                4F,  listOf(GenreEntity(6, "Biography")), "EN", "2018", 134,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                5, "Cold Pursuit",
                "A grieving snowplow driver seeks out revenge against the drug dealers who killed his son.",
                3.1F, listOf(GenreEntity(1, "Action")), "EN", "2019", 119,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                6, "Creed II",
                "Under the tutelage of Rocky Balboa, newly crowned heavyweight champion Adonis Creed faces off against Viktor Drago, the son of Ivan Drago.",
                3.55F, listOf(GenreEntity(5, "Sport")), "EN", "2018", 130,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                7, "Fantastic Beasts: The Crimes of Grindelwald",
                "The second installment of the \"Fantastic Beasts\" series featuring the adventures of Magizoologist Newt Scamander.",
                3.3F, listOf(GenreEntity(2, "Adventure")), "EN", "2018", 134,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                8, "Glass",
                "Security guard David Dunn uses his supernatural abilities to track Kevin Wendell Crumb, a disturbed man who has twenty-four personalities.",
                3.35F, listOf(GenreEntity(4, "Drama")), "EN", "2019", 129,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                9, "How to Train Your Dragon: The Hidden World",
                "When Hiccup discovers Toothless isn't the only Night Fury, he must seek \"The Hidden World\", a secret Dragon Utopia before a hired tyrant named Grimmel finds it first.",
                3.75F, listOf(GenreEntity(3, "Animation")), "EN", "2019", 104,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )
        movieList.add(
            BaseDetailResponse.MovieDetailResponse(
                10, "Avengers: Infinity War",
                "The Avengers and their allies must be willing to sacrifice all in an attempt to defeat the powerful Thanos before his blitz of devastation and ruin puts an end to the universe.",
                4.2F, listOf(GenreEntity(2, "Adventure")), "EN", "2018", 149,
                "https://image.tmdb.org/t/p/w500/6tfT03sGp9k4c0J3dypjrI8TSAI.jpg"
            )
        )

        return movieList
    }

    fun getMovieReviewList(): List<ReviewEntity>{
        val movieList = mutableListOf<ReviewEntity>()
        movieList.add(
            ReviewEntity("1", "Ruli",
                "A musician helps a young singer find fame as age and alcoholism send his own career into a downward spiral.",
                "2018")
        )
        movieList.add(
            ReviewEntity(
                "2", "Alita",
                "A deactivated cyborg's revived, but can't remember anything of her past and goes on a quest to find out who she is.",
                "2019")
        )
        movieList.add(
            ReviewEntity(
                "3", "Aquaman",
                "Arthur Curry, the human-born heir to the underwater kingdom of Atlantis, goes on a quest to prevent a war between the worlds of ocean and land.",
                "2018")
        )
        movieList.add(
            ReviewEntity(
                "4", "Bohemian Rhapsody",
                "The story of the legendary British rock band Queen and lead singer Freddie Mercury, leading up to their famous performance at Live Aid",
                "2018")
        )
        movieList.add(
            ReviewEntity(
                "5", "Cold Pursuit",
                "A grieving snowplow driver seeks out revenge against the drug dealers who killed his son.",
                "2019")
        )
        return movieList
    }
}