from domain.entities import Client
from domain.validators import *

class InMemoryRepositoryClient:

    def __init__(self):
        """
        The clients will be stored in 'clients' list
        """
        # self.__clients = []
        self.__clients = {}
        # undo

    def find(self, id_client):
        if id_client in self.__clients:
            return self.__clients[id_client]
        return None

    def store(self, client):
        """
        Store a client in the list
        :param client: the client that will be added
        :type client: Client
        :return -;
        """
        # for el in self.__clients:
        #     if el.get_id() == client.get_id():
        #         raise ValueError("ID is taken")
        # self.__clients.append(client)
        if client.get_id() in self.__clients.keys():
            raise IdTakenError()
        self.__clients[client.get_id()] = client

    def get_all_clients(self):
        """
        Return a list with all clients
        """
        # return self.__clients
        return list(self.__clients.values())

    def delete_client(self, id_client):
        """
        Delete a client from the list by the id of a client
        :param id_client: the id of the client that will be deleted (format: lldddd ; l- letter, d - digit)
        """
        # for el in self.__clients:
        #     if el.get_id() == client.get_id():
        #         self.__clients.remove(el)
        #         return
        #
        # raise ValueError("ID not found.")
        if not id_client in self.__clients.keys():
            raise IdNotFoundError()
        self.__clients.pop(id_client)

    def update_client_name(self, id_client, new_client_name):
        """
        Update a client's name
        """
        for el in self.__clients.keys():
            if el == id_client:
                self.__clients[el].set_name(new_client_name)
                return
        raise IdNotFoundError()

    def search_client_by_criteria(self, search_criteria):
        """
        Return the list of clients that meet the criteria
        :param search_criteria: the search criteria
        """
        return [el for el in self.__clients.values() if search_criteria(el)]
