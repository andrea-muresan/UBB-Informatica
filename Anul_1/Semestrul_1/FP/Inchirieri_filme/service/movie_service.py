from domain.entities import Movie
from domain.validators import *
import random


class MovieService:

    def __init__(self, repo, validator):
        self.__repo = repo  # list of movies as classes
        self.__validator = validator

    def generate_movies(self):
        """
        Add some random movies to the list
        """
        movie1 = self.create_movie('mm1111', 'Harry Potter', 'fantasy', 'A gripping story about...')
        self.add_movie(movie1)
        movie2 = self.create_movie('mm2222', 'Now you see me', 'thriller', 'An American movie...')
        self.add_movie(movie2)
        movie3 = self.create_movie('mm3333', 'Fantastic Beasts', 'fantasy', 'Half century before Harry Potter was born...')
        self.add_movie(movie3)
        movie4 = self.create_movie('mm4444', 'Loco por ella', 'romance', 'An compelling romantic comedy...')
        self.add_movie(movie4)
        movie5 = self.create_movie('mm5555', 'Black Panther', 'action', 'A Marvel plot...')
        self.add_movie(movie5)
        movie6 = self.create_movie('mm6666', 'The age of Adaline', 'fantasy', 'An endless life...')
        self.add_movie(movie6)
        movie7 = self.create_movie('mm7777', 'Atlantis', 'fantasy', 'Miles under the seas...')
        self.add_movie(movie7)

    def create_movie(self, id_movie, name, genre, description):
        """
        Create a new movie and validate it
        :type id_movie: str (lldddd ; l -letter, d - digit)
        :type name: str
        :type genre: str
        :type description: str
        :return: Return the new movie
        :rtype: Movie
        """
        movie = Movie(id_movie, name, genre, description)
        self.__validator.validate(movie)
        return movie

    def add_movie(self, movie):
        """
        Add a movie read from console to the list
        :param movie: the movie that will be added
        :type movie: Movie
        """
        self.__repo.store(movie)

    def get_all_movies(self):
        """
        Return a list with all movies
        """
        return self.__repo.get_all_movies()

    def delete_movie(self, id_movie):
        """
        Delete a movie from a list by id
        :param id_movie: the id of the movie that will be deleted
        """
        # self.__repo.delete_movie(id_movie)
        movies = self.__repo.get_all_movies()
        self.__repo.delete_movie(id_movie)

    def update_movie_name(self, movie_id, new_movie_name):
        self.__repo.update_movie_name(movie_id, new_movie_name)

    def update_movie_genre(self, movie_id, new_movie_genre):
        self.__repo.update_movie_genre(movie_id, new_movie_genre)

    def update_movie_description(self, movie_id, new_movie_description):
        self.__repo.update_movie_description(movie_id, new_movie_description)

    def search_movie_by_genre(self, genre):
        """
        Search the movies with a certain genre
        """
        searched_list = self.__repo.search_movie_by_criteria(lambda x: genre == x.get_genre())
        return searched_list

    def search_movie_by_id(self, id_movie):
        """
        Search a movie by its id - recursive method
        """
        movies = self.__repo.get_all_movies()
        return self.__repo.recursive_search_movie_by_id(id_movie, movies)

    def generate_random_movie(self):
        """
        Generate a random movie
        :return: the movie
        """
        # generate id
        id = chr(random.randint(ord('a'), ord('z')))
        id += chr(random.randint(ord('a'), ord('z')))
        id += str(random.randrange(1000, 10000))

        # generate Name
        name = chr(random.randint(ord('a'), ord('z')))
        for i in range(1, random.randrange(2, 7)):
            name += chr(random.randint(ord('a'), ord('z')))

        # generate genre
        genre_list = ['fantasy', 'horror', 'romance', 'thriller', 'action', 'adventure', 'comedy', 'drama', 'mystery', 'musical']
        genre = random.choice(genre_list)

        # generate description
        description = ''
        for nr_words in range(1, random.randrange(3, 10)):
            for i in range(1, random.randrange(4, 7)):
                description += chr(random.randint(ord('a'), ord('z')))
            description += ' '

        rand_movie = Movie(id, name, genre, description)
        return rand_movie
