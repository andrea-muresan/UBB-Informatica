from domain.entities import *
from domain.validators import *

class InMemoryRepositoryRental:

    def __init__(self):
        self.__rentals = []

    def find_rental(self, id_movie, id_client):
        """
        Search if there is a rental between a client and a movie
        :param id_movie: the movie's id
        :param id_client: the client's id
        :return: True - found, None - not found
        """
        for rental in self.__rentals:
            if rental.get_movie().get_id() == id_movie or rental.get_client().get_id() == id_client:
                return True
        return None

    def find_movie(self, id_movie):
        """
        Check if a movie is assigned
        :param id_movie: movie that is checked
        :return: True - is assigned, False - it is not
        """
        for rental in self.__rentals:
            if rental.get_movie().get_id() == id_movie:
                return True
        return False

    def store(self, rental):
        """
        Add a rental to the list
        :param rental: The rental that will be added
        """
        r = self.find_movie(rental.get_movie().get_id())
        if r is True:
            raise ValueError("Rental already assigned")
        self.__rentals.append(rental)
        rental.get_movie().increase_movie_no_rented()

    def get_all(self):
        """
        Return a list with all rentals
        """
        return self.__rentals

    def delete_rental(self, id_movie, id_client):
        """
        Delete from the list the rental between a movie and a client
        :param id_movie: movie's id
        :param id_client: client's id
        """
        for el in self.__rentals:
            if el.get_movie().get_id() == id_movie and el.get_client().get_id() == id_client:
                self.__rentals.remove(el)
                return
        raise RentalNotFoundError()
