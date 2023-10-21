from domain.entities import *
from domain.validators import *
from repository.rental_repo import InMemoryRepositoryRental
from repository.movie_repo import InMemoryRepositoryMovie
from repository.client_repo import InMemoryRepositoryClient
from domain.infos import *

class RentalService:
    def __init__(self, rental_repo, rental_val, movie_repo, client_repo):
        self.__rental_repo = rental_repo
        self.__rental_val = rental_val
        self.__movie_repo = movie_repo
        self.__client_repo = client_repo

    def create_rental(self, movie_id, client_id, rental_date):
        """
        Create a rental between a movie and a client
        :param movie_id: movie's id
        :param client_id: client's id
        :param rental_date: the date the movie must be returned
        :return: the rental
        """
        movie = self.__movie_repo.find(movie_id)
        if movie is None:
            raise MovieNotFoundError()

        client = self.__client_repo.find(client_id)
        if client is None:
            raise ClientNotFoundError()

        rental = Rental(movie, client, rental_date)
        self.__rental_val.validate(rental)
        return rental

    def generate_rentals(self):
        """
        Add some random rentals to the list
        """
        self.add_rental(self.create_rental('mm1111', 'cc1111', 8))
        self.add_rental(self.create_rental('mm2222', 'cc1111', 4))
        self.add_rental(self.create_rental('mm3333', 'cc2222', 9))
        self.add_rental(self.create_rental('mm4444', 'cc2222', 22))
        self.add_rental(self.create_rental('mm5555', 'cc3333', 13))

    def add_rental(self, rental):
        """
        Add a rental
        :type rental: Rental
        """
        self.__rental_repo.store(rental)

    def get_all_rentals(self):
        """
        Return a list with all rentals
        """
        return self.__rental_repo.get_all()

    def delete_rental(self, id_movie, id_client):
        """
        Delete from the list a rental between a movie and a cient
        :param id_movie: movie's id
        :param id_client: client's id
        """
        self.__rental_repo.delete_rental(id_movie, id_client)

    def get_client_info(self):
        all_rentals = self.get_all_rentals()

        clients_no_movies = {}

        for rental in all_rentals:
            if rental.get_client().get_id() not in clients_no_movies:
                dto = ClientInfo(rental.get_client().get_id(), rental.get_client().get_name())
                clients_no_movies[rental.get_client().get_id()] = dto
            else:
                clients_no_movies[rental.get_client().get_id()].increase_no_movies()
        return list(clients_no_movies.values())

    def get_first_x_percentage_from_list(self, x, lst):
        """
        Return the first X percentage rentals from the list. It doesn't sort the list
        :param x: the percentage
        :param lst: the list
        """
        no = len(lst)
        no = int(no * x / 100)
        return lst[0: no + 1]

    def delete_if_client_deleted(self, id_client):
        self.__rental_repo.delete_if_client_deleted(id_client)

    def delete_if_movie_deleted(self, id_client):
        self.__rental_repo.delete_if_movie_deleted(id_client)


