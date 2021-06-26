package com.example.mondiamusic.models

data class ExtendedMetaData(val originalSongId: String = "",
                            val languages: String = "",
                            val originalTitle: String = "",
                            val moods: List<String>?,
                            val genresHierarchy: List<String>?,
                            val tempos: List<String>?,
                            val releaseYear: String = "",
                            val gracenoteRythmApiGenreIds: String = "")