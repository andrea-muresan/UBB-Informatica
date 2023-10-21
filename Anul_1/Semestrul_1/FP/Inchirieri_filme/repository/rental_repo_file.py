from domain.entities import *
from domain.validators import *
from domain.infos import *

class InMemoryRepositoryRentalFile:
    def __init__(self, file_name, movie_repo, client_repo):
        self.__file_name = file_name
        self.__movie_repo = movie_repo
        self.__client_repo = client_repo
        self.__rentals = []
        self.__load_from_file()

    def __load_from_file(self):
        """
        Loads rentals from file
        """
        try:
            f = open(self.__file_name, 'r')
        except:
            raise FileNotReadError()

        line = f.readline().strip()
        while line != "":
            part = line.split(",")
            id_movie = part[0].strip()
            id_client = part[1].strip()
            date = part[2].strip()
            try:
                mv = self.__movie_repo.find(id_movie)
                cl = self.__client_repo.find(id_client)
                rnt = Rental(mv, cl, date)
                self.__rentals.append(rnt)
                mv.increase_movie_no_rented()
            except IdNotFoundError:
                pass
            line = f.readline().strip()
        f.close()

    def __store_in_file(self):
        """
        Store a list of rentals in file
        """
        with open(self.__file_name, 'w') as f:
            for rnt in self.__rentals:
                rnt_line = rnt.get_movie().get_id() + ', ' + rnt.get_client().get_id() + ', ' + str(rnt.get_date()) + '\n'
                f.write(rnt_line)

    def clear_file(self):
        """
        Remove all rentals from the repository
        """
        self.__rentals = []
        self.__store_in_file()

    def get_all(self):
        """
        Return a list with all rentals
        """
        return self.__rentals

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
        Add a movie to the list
        """
        if rental in self.__rentals:
            raise IdTakenError()
        self.__rentals.append(rental)
        self.__store_in_file()
        rental.get_movie().increase_movie_no_rented()

    def delete_rental(self, id_movie, id_client):
        """
        Delete from the list the rental between a movie and a client
        :param id_movie: movie's id
        :param id_client: client's id
        """
        for rnt in self.__rentals:
            if rnt.get_movie().get_id() == id_movie and rnt.get_client().get_id() == id_client:
                self.__rentals.remove(rnt)
                self.__store_in_file()
                return
        raise RentalNotFoundError()

    def delete_if_client_deleted(self, id_client):
        for rnt in self.__rentals:
            if rnt.get_client().get_id() == id_client:
                self.__rentals.remove(rnt)
                self.__store_in_file()
                return
        raise RentalNotFoundError()

    def delete_if_movie_deleted(self, id_movie):
        for rnt in self.__rentals:
            if rnt.get_movie().get_id() == id_movie:
                self.__rentals.remove(rnt)
                self.__store_in_file()
                return
        raise RentalNotFoundError()

