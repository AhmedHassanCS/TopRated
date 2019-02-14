package com.apps.ahfreelancing.toprated.data.convertor

import com.apps.ahfreelancing.toprated.data.entity.GenreEntity
import com.apps.ahfreelancing.toprated.data.entity.MovieEntity
import com.apps.ahfreelancing.toprated.domain.model.MovieModel

/**
 * Created by Ahmed Hassan on 2/12/2019.
 *
 * A class to convert design models to data entities and vise-versa
 */
class MovieConverter {
    fun convertEntityToModel(entity: MovieEntity): MovieModel {

        val movieModel = MovieModel()
        movieModel.id = entity.id
        movieModel.name = entity.name
        movieModel.averageVote = entity.averageVote
        movieModel.language = entity.language
        movieModel.posterURL = "http://image.tmdb.org/t/p/w342/" + entity.posterURL
        movieModel.releaseDate = entity.releaseDate
        movieModel.voteCount = entity.voteCount

        movieModel.genres = ArrayList()
        if (entity.genres != null)
            for (genre: GenreEntity in entity.genres!!)
                movieModel.genres!!.add(genre.name!!)

        return movieModel
    }

    fun convertModelToEntity(model: MovieModel): MovieEntity {
        val movieEntity = MovieEntity()
        movieEntity.id = model.id
        movieEntity.name = model.name
        movieEntity.averageVote = model.averageVote
        movieEntity.language = model.language
        movieEntity.posterURL = model.posterURL
        movieEntity.releaseDate = model.releaseDate
        movieEntity.voteCount = model.voteCount
        movieEntity.genres = ArrayList()

        if (model.genres != null)
            for (genre: String in model.genres!!) {
                val genreEntity = GenreEntity()
                genreEntity.name = genre
                movieEntity.genres!!.add(genreEntity)
            }

        return movieEntity
    }

    fun convertEntitiestoModels(entities: List<MovieEntity>): ArrayList<MovieModel> {
        val models: ArrayList<MovieModel> = ArrayList()
        for (entity: MovieEntity in entities)
            models.add(convertEntityToModel(entity))

        return models
    }

    fun convertModelsToEntities(models: ArrayList<MovieModel>): List<MovieEntity> {
        val entities: ArrayList<MovieEntity> = ArrayList()
        for (model: MovieModel in models)
            entities.add(convertModelToEntity(model))

        return entities.toList()
    }
}