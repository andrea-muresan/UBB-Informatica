from domain.validators import *
from domain.entities import *
from repository.movie_repo import *
from repository.client_repo import *
from service.movie_service import *
from service.client_service import *
from domain.infos import ClientInfo
from sorting.sort_methods import mergesort, bingosort


class Console:

    def __init__(self, movie_srv, client_srv, rental_srv):
        self.__movie_srv = movie_srv
        self.__client_srv = client_srv
        self.__rental_srv = rental_srv

    # Util reads and prints

    def read_option(self):
        cmd = input("Enter your option: ").lower().strip()
        return cmd

    def print_invalid_option(self):
        print("Invalid option")

    def read_valid_id(self):
        id = input("Enter the ID (format: lldddd ; l - letter, d - digit): ")
        if len(id) != 6:
            raise ValueError("ID must have 6 characters.")
        if id[:2].isalpha() is False or id[2:].isdigit() is False:
            raise ValueError("ID format is incorrect! Please follow the tamplate: lldddd (l = letter, d = digit).")
        return id

    def read_valid_name(self):
        name = input("Enter the name: ")
        if len(name) < 2:
            raise ValueError("Name must have at least 2 characters.")
        return name

    def read_valid_genre(self):
        genre = input("Enter the genre: ")
        if len(genre) < 2:
            raise ValueError("Genre must have at least 2 characters.")
        return genre

    def read_valid_description(self):
        description = input("Enter the description: ")
        if len(description) < 4:
            raise ValueError("Name must have at least 4 characters.")
        return description

    # print lists

    def __print_movies(self, movie_list):
        # movie_list = self.__movie_srv.get_all_movies()
        if len(movie_list) == 0:
            print("There is no movie in the list.")
        else:
            print("The list is: ")
            for movie in movie_list:
                print("ID: ", movie.get_id(), "; Name: ", movie.get_name(), \
                      "; Genre: ", movie.get_genre(), "; Description: ", movie.get_description())

    def __print_clients(self, client_list):
        # client_list = self.__client_srv.get_all_clients()
        if len(client_list) == 0:
            print("There is no client in the list.")
        else:
            print("The list is:")
            for el in client_list:
                print("ID: " + el.get_id() + '; Name: ' + el.get_name())

    def __print_rentals(self, rental_list):
        if len(rental_list) == 0:
            print("There is no rental in the list.")
        else:
            print("The list is:")
            for rental in rental_list:
                print("Movie: [", rental.get_movie().get_id(), ', ', rental.get_movie().get_name(),
                      "] - Client: [", rental.get_client().get_id(), ', ', rental.get_client().get_name(),
                      '] - Return date: [', rental.get_date(), ']')

    def __print_clients_with_movies(self, lst_clients):
        if len(lst_clients) == 0:
            print("There is no rental in the list.")
        else:
            print("The list is:")
            for client in lst_clients:
                print("Client: [", client.get_name(), '] - Number of movies: [', client.get_no_movies(), ']')

    def __print_movies_no_rented(self, lst_movies):
        if len(lst_movies) == 0:
            print("There is no rental in the list.")
        else:
            print("The list is:")
            for movie in lst_movies:
                print("Movie: [", movie.get_name(), '] - Rented: [', movie.get_no_rented(), '] times')

    def __print_movies_rented_ordered_by_name(self, lst_rentals):
        if len(lst_rentals) == 0:
            print("There is no rental in the list.")
        else:
            print("The list is:")
            for rental in lst_rentals:
                print("Movie: [", rental.get_movie().get_name(), '] - Rented by: [', rental.get_client().get_name(), ']' )

    # add

    def __call_add_movie(self):
        """
        Add a new movie if the input data is correct, else show the error messages
        """
        id_movie = input("Enter an ID for the movie (format lldddd; l - letter, d - digit): ")
        name = input("Enter the name of the movie: ")
        genre = input("Enter its genre: ")
        description = input("Enter its description: ")

        try:
            movie = self.__movie_srv.create_movie(id_movie, name, genre, description)  # create a movie
            self.__movie_srv.add_movie(movie)
            print("Movie " + movie.get_name() + " has been successfully added.")
        except ValueError as ve:
            print(str(ve))

    def __call_add_client(self):
        """
        Add a new client if the input data is correct, else show the error messages
        """
        id_client = input("Enter ID (format: lldddd): ")
        name = input("Enter name: ")

        try:
            client = self.__client_srv.create_client(id_client, name)
            self.__client_srv.add_client(client)
            print("Client " + client.get_name() + " has been successfully added.")
        except ValueError as ve:
            print(str(ve))

    def __call_add_rental(self):
        """
        Add a new client if the input data is correct, else show the error message
        """
        print("For movie: ", end='')
        id_movie = self.read_valid_id()
        print("For client: ", end='')
        id_client = self.read_valid_id()

        try:
            date = int(input("Enter the return date: "))
            rental = self.__rental_srv.create_rental(id_movie, id_client, date)
            self.__rental_srv.add_rental(rental)
            print("The rental has been successfully added.")
        except ValueError as ve:
            print(str(ve))


    # delete

    def __call_delete_movie(self):
        """
        Delete a movie from the list by its id. If there is nothing to delete, show an error message
        """
        id_movie = self.read_valid_id()
        try:
            self.__movie_srv.delete_movie(id_movie)
            self.__rental_srv.delete_if_movie_deleted(id_movie)
            print("Movie " + id_movie + " has been successfully deleted.")
        except ValueError as ve:
            print(str(ve))

    def __call_delete_client(self):
        """
        Delete a client from the list by its id. If there is nothing to delete, show an error message
        """
        id_client = self.read_valid_id()
        try:
            # client = self.__client_srv.create_client(id_client, 'something')  # creates a client just with the id
            self.__client_srv.delete_client(id_client)
            self.__rental_srv.delete_if_client_deleted(id_client)
            print("Client " + id_client + " has been successfully deleted.")

        except ValueError as ve:
            print(str(ve))

    def __call_delete_rental(self):
        print("For movie: ", end='')
        id_movie = self.read_valid_id()
        print("For client: ", end='')
        id_client = self.read_valid_id()

        try:
            self.__rental_srv.delete_rental(id_movie, id_client)
            print("The rental has been successfully deleted.")
        except ValueError as ve:
            print(str(ve))

    # update

    def __call_update_movie_name(self):
        """
        Update a movie's name if it is in the list, else => error message
        """
        id_movie = self.read_valid_id()
        new_movie_name = self.read_valid_name()
        self.__movie_srv.update_movie_name(id_movie, new_movie_name)
        print("Movie name has been successfully changed to", new_movie_name + ".")

    def __call_update_movie_genre(self):
        """
        Update a movie's genre if it is in the list, else => error message
        """
        id_movie = self.read_valid_id()
        new_movie_genre = self.read_valid_genre()
        self.__movie_srv.update_movie_genre(id_movie, new_movie_genre)
        print("Movie genre has been successfully changed to", new_movie_genre + ".")

    def __call_update_movie_description(self):
        """
        Update a movie's description if it is in the list, else => error message
        """
        id_movie = self.read_valid_id()
        new_movie_description = self.read_valid_description()
        self.__movie_srv.update_movie_description(id_movie, new_movie_description)
        print("Movie description has been successfully changed to", new_movie_description + ".")

    def __call_update_client_name(self):
        """
        Update a client's data if it is in the list, else => error message
        """
        id_client = self.read_valid_id()
        new_client_name = self.read_valid_name()
        try:
            self.__client_srv.update_client_name(id_client, new_client_name)
            print("Client name has been successfully changed to", new_client_name + ".")
        except ValueError as ve:
            print(str(ve))

    # search

    def __call_search_movie_by_genre(self):
        """
        Search all movies with a given genre
        """
        genre = self.read_valid_genre()
        searched_list = self.__movie_srv.search_movie_by_genre(genre)
        self.__print_movies(searched_list)

    def __call_search_movie_by_id(self):
        """
        Search a movie by its id - recursive method
        """
        id_movie = self.read_valid_id()
        searched_movie = [self.__movie_srv.search_movie_by_id(id_movie)]
        self.__print_movies(searched_movie)

    def __call_search_client_by_id(self):
        """
        Search a client by its id
        """
        client_id = self.read_valid_id()
        searched_client = self.__client_srv.search_client_by_id(client_id)
        self.__print_clients(searched_client)

    # Random Movie / Generate
    def __call_random_movies(self):
        """
        Add 10 random movies in the list
        """
        for i in range(1, 11):
            rand_movie = self.__movie_srv.generate_random_movie()
            self.__movie_srv.add_movie(rand_movie)

    def __call_generate_rentals(self):
        self.__rental_srv.generate_rentals()

    def __call_generate_clients(self):
        self.__client_srv.generate_clients()

    def __call_generate_movies(self):
        self.__movie_srv.generate_movies()

    # Reports
    def __call_clients_ordered_by_name(self):
        """
        Print the clients with movies ordered by name
        """
        lst_clients = self.__rental_srv.get_client_info()

        lst_clients = bingosort(lst_clients, key=lambda x: x.get_name())
        self.__print_clients_with_movies(lst_clients)

    def __call_clients_ordered_by_no_movies(self):
        """
        Print the clients with movies ordered by number of movies. If two clients have the same number of movies, they will be ordered by name
        """
        lst_clients = self.__rental_srv.get_client_info()
        lst_clients.sort(key=lambda client: (client.get_no_movies(), client.get_name()))
        self.__print_clients_with_movies(lst_clients)

    def __call_clients_with_most_movies(self):
        """
        Print the first 30% clients with most movies
        """
        lst_clients = self.__rental_srv.get_client_info()
        # lst_clients = bingosort(lst_clients, key=lambda x: x.get_no_movies(), reverse=True)
        lst_clients = self.__rental_srv.get_first_x_percentage_from_list(30, lst_clients)
        self.__print_clients_with_movies(lst_clients)

    def cmp(self, x, y):
        if x.get_no_rented() < y.get_no_rented():
            return True
        elif x.get_no_rented() == y.get_no_rented() and x.get_name() < y.get_name():
            return True
        else:
            return False

    def __call_most_rented_movies(self):
        lst_movies = self.__movie_srv.get_all_movies()
        # lst_movies.sort(key=lambda movie: movie.get_no_rented(), reverse=True)
        lst_movies = bingosort(lst_movies, lambda x, y: self.cmp(x, y))
        self.__print_movies_no_rented(lst_movies)

    def __call_movies_rented_ordered_by_name(self):
        lst_rentals = self.__rental_srv.get_all_rentals()
        # lst_rentals = bingosort(lst_rentals, key=lambda rental: rental.get_movie().get_name())
        self.__print_movies_rented_ordered_by_name(lst_rentals)

    # MENUES

    def print_main_menu(self):
        print("\nMENU")
        print("Choose what you want to work with. Enter: ")
        print("1 for Movies")
        print("2 for Client")
        print("3 for Rental")
        print("x for Exit")

    def print_movie_menu(self):
        print("\nMENU - MOVIE")
        print("Choose an option:")
        print("1 to add a movie")
        print("2 to update a movie")
        print("3 to delete a movie by ID")
        print("4 to search a movie by genre")
        print("5 to search a movie by ID")
        print("p to print all movies")
        print("x to go back to the main menu")

    def print_client_menu(self):
        print("\nMENU - CLIENT")
        print("Choose an option:")
        print("1 to add a client")
        print("2 to update a client name")
        print("3 to delete a client by ID")
        print("4 to search a client by ID")
        print("p to print all clients")
        print("x to go back to the main menu")

    def print_rental_menu(self):
        print("\nMENU - RENTAL")
        print("Choose an option:")
        print("1 to add a rental")
        print("2 to delete a rental")
        print("3 for reports")
        print("p to print all rentals")
        print("x to go back to the main menu")

    def print_report_menu(self):
        print("\nMENU - RENTAL")
        print("Choose an option:")
        print("1 to order the clients with movies by clients' names")
        print("2 to order the clients with movies by number of movies")
        print("3 to see the most rented movies")
        print("4 to see the first 30% clients with most movies")
        print("5 to order the movies rented by name")  # new report
        print("p to print all rentals")
        print("x to go back to the main menu")

    def print_update_movie_menu(self):
        print("\nMENU - UPDATE MOVIE")
        print("Choose an option:")
        print("1 to update the name")
        print("2 to update the genre")
        print("3 to update the description")
        print("x to go back to the main menu")

    # run menus

    def run_movie_menu(self):
        self.print_movie_menu()
        cmd = self.read_option()
        if cmd == '1':
            self.__call_add_movie()
        elif cmd == '2':
            self.run_update_movie_menu()
        elif cmd == '3':
            self.__call_delete_movie()
        elif cmd == '4':
            self.__call_search_movie_by_genre()
        elif cmd == '5':
            self.__call_search_movie_by_id()
        elif cmd == 'p':
            movie_list = self.__movie_srv.get_all_movies()
            self.__print_movies(movie_list)
        elif cmd == 'x':
            return
        else:
            self.print_invalid_option()

    def run_client_menu(self):
        self.print_client_menu()
        cmd = self.read_option()
        if cmd == '1':
            self.__call_add_client()
        elif cmd == '2':
            self.__call_update_client_name()
        elif cmd == '3':
            self.__call_delete_client()
        elif cmd == '4':
            self.__call_search_client_by_id()
        elif cmd == 'p':
            client_list = self.__client_srv.get_all_clients()
            self.__print_clients(client_list)
        elif cmd == 'x':
            return
        else:
            self.print_invalid_option()

    def run_rental_menu(self):
        self.print_rental_menu()
        cmd = self.read_option()
        if cmd == '1':
            self.__call_add_rental()
        elif cmd == '2':
            self.__call_delete_rental()
        elif cmd == '3':
            self.run_report_menu()
        elif cmd == 'p':
            rental_list = self.__rental_srv.get_all_rentals()
            self.__print_rentals(rental_list)
        elif cmd == 'x':
            return
        else:
            self.print_invalid_option()

    def run_report_menu(self):
        self.print_report_menu()
        cmd = self.read_option()
        if cmd == '1':
            self.__call_clients_ordered_by_name()
        elif cmd == '2':
            self.__call_clients_ordered_by_no_movies()
        elif cmd == '3':
            self.__call_most_rented_movies()
        elif cmd == '4':
            self.__call_clients_with_most_movies()
        elif cmd == '5':
            self.__call_movies_rented_ordered_by_name()
        elif cmd == 'x':
            return
        else:
            self.print_invalid_option()

    def run_update_movie_menu(self):
        self.print_update_movie_menu()
        cmd = self.read_option()
        if cmd == '1':
            self.__call_update_movie_name()
        elif cmd == '2':
            self.__call_update_movie_genre()
        elif cmd == '3':
            self.__call_update_movie_description()
        elif cmd == 'x':
            return
        else:
            self.print_invalid_option()

    def start(self):
        # self.__call_random_movies()
        # self.__call_generate_movies()
        # self.__call_generate_clients()
        # self.__call_generate_rentals()

        while True:
            try:
                self.print_main_menu()
                cmd = self.read_option()
                if cmd == '1':
                    self.run_movie_menu()
                elif cmd == '2':
                    self.run_client_menu()
                elif cmd == '3':
                    self.run_rental_menu()
                elif cmd == 'x':
                    return
                else:
                    self.print_invalid_option()

            except ValueError as ve:
                print(ve)
            except IdNotFoundError as er:
                print(er)
            except RentalNotFoundError as er:
                print(er)
            except InputError as er:
                print(er)
            except FileNotReadError as er:
                print(er)
            except DuplicatedDataError as er:
                print(er)
            except ClientNotFoundError as er:
                print(er)
            except MovieNotFoundError as er:
                print(er)
