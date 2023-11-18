from domain.entities import Client
from domain.validators import *


class ClientService:

    def __init__(self, repo, validator):
        self.__repo = repo  # list of clients as classes
        self.__validator = validator

    def create_client(self, id_client, name):
        """
        Create a new client
        :type id_client: str (lldddd ; l -letter, d - digit)
        :type name: str
        :return: Return the new client
        :rtype: Client
        """
        client = Client(id_client, name)
        self.__validator.validate(client)
        return client

    def generate_clients(self):
        """
        Add some random clients to the list
        """
        self.add_client(self.create_client('cc1111', 'Popescu Ion'))
        self.add_client(self.create_client('cc2222', 'Bud Maria'))
        self.add_client(self.create_client('cc3333', 'Popa Ioan'))
        self.add_client(self.create_client('cc4444', 'Petrea Corina'))


    def add_client(self, client):
        """
        Add a client read from console to the list
        :param client: the client that will be added
        :type client: Client
        """
        self.__repo.store(client)

    def get_all_clients(self):
        """
        Return a list with all clients
        """
        return self.__repo.get_all_clients()

    def delete_client(self, id_client):
        """
        Delete a client from the list by id
        :param id_client: the id of the client that will be deleted
        """
        self.__repo.delete_client(id_client)

    def update_client_name(self, id_client, new_client_name):
        self.__repo.update_client_name(id_client, new_client_name)

    def search_client_by_id(self, client_id):
        """
        Search a client by its id
        :param client_id: client's id
        """
        searched_client = self.__repo.search_client_by_criteria(lambda x: client_id == x.get_id())
        return searched_client
