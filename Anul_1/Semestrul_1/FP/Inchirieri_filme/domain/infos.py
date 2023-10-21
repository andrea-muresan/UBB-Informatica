
class ClientInfo:

    def __init__(self, id, name):
        """
        Show how many movies a client has
        :param id: id client
        :param name: movie name
        """
        self.__id = id
        self.__name = name
        self.__no_movies = 1

    def get_id(self):
        return self.__id

    def get_name(self):
        return self.__name

    def get_no_movies(self):
        return self.__no_movies

    def increase_no_movies(self):
        self.__no_movies += 1
        return self.__no_movies

    def __eq__(self, other):
        if self.__name == other.get_name() and self.__no_movies == other.get_no_movies():
            return True

    # def __lt__(self, other):
    #     return self.__name < other.__name
