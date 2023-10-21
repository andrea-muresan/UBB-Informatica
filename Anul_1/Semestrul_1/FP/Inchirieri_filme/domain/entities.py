# movie class
class Movie:

    def __init__(self, id_movie, name, genre, description):
        """
        Create a new movie with the given idMovie, name, genre and description
        :param id_movie: movie ID
        :type id_movie: str (lldddd ; l- letter, d - digit)
        :param name: movie name
        :type name: str
        :param genre: movie genre
        :type genre: str
        :param description: movie description
        :type description: str
        """
        self.__id = id_movie
        self.__name = name
        self.__genre = genre
        self.__description = description

        self.__no_rented = 0

    def get_id(self):
        return self.__id

    def get_name(self):
        return self.__name

    def get_genre(self):
        return self.__genre

    def get_description(self):
        return self.__description

    def get_no_rented(self):
        return self.__no_rented

    def set_id(self, new_value):
        self.__id = new_value
        return self.__id

    def set_name(self, new_value):
        self.__name = new_value
        return self.__name

    def set_genre(self, new_value):
        self.__genre = new_value
        return self.get_genre()

    def set_description(self, new_value):
        self.__description = new_value
        return self.__description

    def increase_movie_no_rented(self):
        self.__no_rented += 1

    def __eq__(self, other):
        """
        Check if two movies have the same ids
        :param other:
        :return:
        """
        if self.__id == other.get_id():
            return True
        return False


# client class
class Client:

    def __init__(self, id_client, name):
        """
        Create a new object with the given idClient, name, CNP
        :param id_client: client id
        :type id_client: int
        :param name: client name
        :type name: str
        """
        self.__id = id_client
        self.__name = name

    def get_id(self):
        return self.__id

    def get_name(self):
        return self.__name

    def set_id(self, new_value):
        self.__id = new_value

    def set_name(self, new_value):
        self.__name = new_value
        return self.__name

    def __eq__(self, other):
        """
        Check if two clients have the same ids
        """
        if self.__id == other.get_id():
            return True
        return False


class Rental:

    def __init__(self, movie, client, rental_date):
        self.__movie = movie
        self.__client = client
        self.__rental_date = rental_date

    def get_movie(self):
        return self.__movie

    def get_client(self):
        return self.__client

    def get_date(self):
        return self.__rental_date

    def __eq__(self, other):
        """
        Check if two rentals have the same id_client and the same id_movie
        :param other:
        :return:
        """
        return self.get_movie().get_id() == other.get_movie().get_id() \
            and self.get_client().get_id() == other.get_client().get_id()
