
from domain.validators import *
class Console:
    def __init__(self, song_srv):
        self.__song_srv = song_srv

    def print_menu(self):
        """
        Afiseaza meniul
        :return:
        """
        print("\nMENU")
        print("1 - update a song genre and date")
        print("2 - generate songs")
        print("3 - transfer to another file")
        print("x - exit")


    def read_option(self):
        opt = input("Enter option :")
        return opt

    def print_successful_operation_msg(self):
        print("The operation was successful.")

    def __call_update_movie(self):
        """
        Updateaza un film

        """
        name = input("Enter name: ")
        artist = input("Enter artist: ")
        genre = input("Enter genre: ")
        day = input("Enter day: ")
        month = input("Enter month: ")
        year = input("Enter year: ")
        self.__song_srv.update_song(name, artist, genre, day, month, year)

        self.print_successful_operation_msg()

    def __call_generate_songs(self):
        """
        Genereaza cantece
        """
        nr = int(input("Enter number: "))
        titluri = input("Enter titles: ")
        titluri = titluri.split(',')
        artisti = input("Enter artists: ")
        artisti = artisti.split(',')

        self.__song_srv.generate_songs(nr, titluri, artisti)

    def start(self):

        while True:
            try:
                self.print_menu()
                opt = self.read_option()
                if opt == '1':
                    self.__call_update_movie()
                if opt == '2':
                    self.__call_generate_songs()
                if opt == '3':
                    name = input("Enter file name: ")
                    self.__song_srv.new_file(name)
                elif opt == 'x':
                    return
                else:
                    print("Invalid operation")

            except InputError as er:
                print(er.get_errors())
            except FileNotFoundError as er:
                print(er.get_errors())
            except SongNotFoundError as er:
                print(er.get_errors())
            except ValueError as ve:
                print(ve)
