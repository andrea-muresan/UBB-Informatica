from domain.entities import Movie
from domain.validators import *


class InMemoryRepositoryMovie:

    def __init__(self):
        """
        The movies will be stored in 'movies' list
        """
        self.__movies = {}
        # undo

    def find(self, id_movie):
        if id_movie in self.__movies:
            return self.__movies[id_movie]
        return None

    def store(self, movie):
        """
        Store a movie in the list
        :param movie: the movie that will be added
        :type movie: Movie
        :return -;
        """
        # for el in self.__movies:
        #     if el.get_id() == movie.get_id():
        #         raise ValueError("ID is taken")
        # self.__movies.append(movie)
        if movie.get_id() in self.__movies.keys():
            raise IdTakenError()
        self.__movies[movie.get_id()] = movie

    def get_all_movies(self):
        """
        Return a list with all movies
        """
        return list(self.__movies.values())

    def delete_movie(self, id_movie):
        """
        Delete a movie from the list by the id
        :param id_movie: the id of the movie that will be deleted
        """
        # for el in self.__movies:
        #     if el.get_id() == id_movie:
        #         self.__movies.remove(el)
        #         return

        if not id_movie in self.__movies.keys():
            raise IdNotFoundError()
        self.__movies.pop(id_movie)

    def update_movie_name(self, movie_id, new_movie_name):
        for el in self.__movies.keys():
            if el == movie_id:
                self.__movies[el].set_name(new_movie_name)
                return
        raise IdNotFoundError()

    def update_movie_genre(self, movie_id, new_movie_genre):
        for el in self.__movies.keys():
            if el == movie_id:
                self.__movies[el].set_genre(new_movie_genre)
                return
        raise IdNotFoundError()

    def update_movie_description(self, movie_id, new_movie_description):
        for el in self.__movies.keys():
            if el == movie_id:
                self.__movies[el].set_description(new_movie_description)
                return
        raise IdNotFoundError()

    def search_movie_by_criteria(self, search_criteria):
        """
        Return the list of movies that meet the criteria
        :param search_criteria: the search criteria
        """
        return [el for el in self.__movies.values() if search_criteria(el)]



