from domain.entities import *
from domain.validators import *


class InMemoryRepositoryMovieFile:
    def __init__(self, file_name):
        self.__file_name = file_name
        self.__movies = []
        self.__load_from_file()

    # def __load_from_file(self):
    #     """
    #     Load clients from file
    #     """
    #     try:
    #         f = open(self.__file_name, 'r')
    #     except:
    #         raise FileNotReadError()
    #
    #     line = f.readline().strip()
    #     while line != "":
    #         part = line.split(",")
    #         mv = Movie(part[0].strip(), part[1].strip(), part[2].strip(), part[3].strip())
    #         self.__movies.append(mv)
    #         line = f.readline().strip()
    #     f.close()

    def __load_from_file(self):
        """
        Load clients from file
        """
        try:
            f = open(self.__file_name, 'r')
        except:
            raise FileNotReadError()

        line = f.readline().strip()
        while line != "":
            id_movie = line
            movie_name = f.readline().strip()
            movie_genre = f.readline().strip()
            movie_description = f.readline().strip()
            mv = Movie(id_movie, movie_name, movie_genre, movie_description)
            self.__movies.append(mv)
            line = f.readline().strip()
        f.close()

    # def __store_in_file(self):
    #     """
    #     Store a list of clients in file
    #     """
    #     with open(self.__file_name, 'w') as f:
    #         for mv in self.__movies:
    #             mv_line = mv.get_id() + ', ' + mv.get_name() + ', ' + mv.get_genre() + ', ' + mv.get_description() + '\n'
    #             f.write(mv_line)

    def __store_in_file(self):
        """
        Store a list of clients in file
        """
        with open(self.__file_name, 'w') as f:
            for mv in self.__movies:
                f.write(mv.get_id() + '\n')
                f.write(mv.get_name() + '\n')
                f.write(mv.get_genre() + '\n')
                f.write(mv.get_description() + '\n')

    def clear_file(self):
        """
        Remove all movies from the repository
        """
        self.__movies = []
        self.__store_in_file()

    def find(self, id_movie):
        for mv in self.__movies:
            if mv.get_id() == id_movie:
                return mv
        return None

    def store(self, movie):
        """
        Store a movie in the list
        :param movie: the movie that will be added
        :type movie: Movie
        :return -;
        """
        if movie in self.__movies:
            raise IdTakenError()
        self.__movies.append(movie)
        self.__store_in_file()

    def get_all_movies(self):
        """
        Return all movies stored in file
        :return: list of movies
        """
        return self.__movies

    def delete_movie(self, id_movie):
        """
        Delete a movie from the list by the id
        :param id_movie: the id of the movie that will be deleted
        """
        for el in self.__movies:
            if el.get_id() == id_movie:
                self.__movies.remove(el)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def update_movie_name(self, movie_id, new_name):
        """
        Update a movie's name
        """
        for el in self.__movies:
            if el.get_id() == movie_id:
                el.set_name(new_name)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def update_movie_genre(self, movie_id, new_genre):
        """
        Update a movie's genre
        """
        for el in self.__movies:
            if el.get_id() == movie_id:
                el.set_genre(new_genre)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def update_movie_description(self, movie_id, new_description):
        """
        Update a movie's description
        """
        for el in self.__movies:
            if el.get_id() == movie_id:
                el.set_description(new_description)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def search_movie_by_criteria(self, search_criteria):
        """
        Return the list of movies that meet the criteria
        :param search_criteria: the search criteria
        """
        return [el for el in self.__movies if search_criteria(el)]

    # ****************************************
    # *the recursive method for search by id
    # ****************************************
    def recursive_search_movie_by_id(self, id_movie, movies):
        """
        Return the list of movies that meet the criteria
        :param movies: the list of movies
        :param id_movie: the searched id
        """
        if not movies:
            raise IdNotFoundError()
        if movies[0].get_id() == id_movie:
            return movies[0]
        return self.recursive_search_movie_by_id(id_movie, movies[1:])

    # ****************************************
    # *the recursive method for delete movie
    # ****************************************
    def recursive_delete_movie(self, id_movie, movies):
        """
        Delete a movie from the list by the id - recursive method
        :param id_movie: the id of the movie that will be deleted
        :param movies: the list
        """
        if not movies:
            raise IdNotFoundError()
        if movies[0].get_id() == id_movie:
            self.__movies.remove(movies[0])
            self.__store_in_file()
            return
        return self.recursive_delete_movie(id_movie, movies[1:])




