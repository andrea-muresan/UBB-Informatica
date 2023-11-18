from domain.entities import *
from domain.validators import *

class InMemoryRepositoryClientFile:
    def __init__(self, file_name):
        self.__file_name = file_name
        self.__clients = []
        self.__load_from_file()

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
            part = line.split(",")
            cl = Client(part[0].strip(), part[1].strip())
            self.__clients.append(cl)
            line = f.readline().strip()
        f.close()

    def __store_in_file(self):
        """
        Store a list of clients in file
        """
        with open(self.__file_name, 'w') as f:
            for cl in self.__clients:
                cl_line = cl.get_id() + ', ' + cl.get_name() + '\n'
                f.write(cl_line)

    def clear_file(self):
        """
        Remove all clients from the repository
        """
        self.__clients = []
        self.__store_in_file()

    def get_all_clients(self):
        """
        Return all clients stored in file
        :return: list of clients
        """
        return self.__clients

    def store(self, cl):
        """
        Store a client
        :param cl: client
        """
        if cl in self.__clients:
            raise IdTakenError()
        self.__clients.append(cl)
        self.__store_in_file()

    def find(self, id_client):
        """
        Searches a client in the list by its id
        :return: True - found / None - not found
        """
        for el in self.__clients:
            if el.get_id() == id_client:
                return el
        return None

    def update_client_name(self, client_id, new_name):
        """
        Update a client's name
        """
        for cl in self.__clients:
            if cl.get_id() == client_id:
                cl.set_name(new_name)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def delete_client(self, id_client):
        """
        Delete a client from the list by the id of a client
        :param id_client: the id of the client that will be deleted (format: lldddd ; l- letter, d - digit)
        """
        for el in self.__clients:
            if el.get_id() == id_client:
                self.__clients.remove(el)
                self.__store_in_file()
                return
        raise IdNotFoundError()

    def search_client_by_criteria(self, search_criteria):
        """
        Return the list of clients that meet the criteria
        :param search_criteria: the search criteria
        """
        return [el for el in self.__clients if search_criteria(el)]
