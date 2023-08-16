
from domain.validators import *
from merge_sort import merge_sort

class InMemorySongRepoFile:
    def __init__(self, file_name):
        self.__file_name = file_name
        self.__songs = []
        self.__load_from_file()

    def __load_from_file(self):
        """
        Incarca cantecele din fisier
        """
        try:
            f = open(self.__file_name, 'r')
        except:
            raise FileNotFoundError()

        line = f.readline().strip()
        while line != "":
            part = line.split(",")
            day, month, year = part[3].split(".")
            song = Song(part[0].strip(), part[1].strip(), part[2].strip(), day.strip(), month.strip(), year.strip())
            self.__songs.append(song)
            line = f.readline()

        f.close()

    def __store_in_file(self):
        """
        Stocheaza cantecele in fisier
        """
        with open(self.__file_name, 'w') as f:
            for song in self.__songs:
                line = song.get_name() + ', ' + song.get_artist() + ', ' + song.get_genre() + ', ' + song.get_day() + '.' + song.get_month() + '.' + song.get_year() + '\n'
                f.write(line)

    def clear_file(self):
        """
        Curata fisierul
        :return:
        """
        self.__songs = []
        self.__store_in_file()

    def get_all_songs(self):
        """
        Returneaza toate cantecele
        """
        return self.__songs

    def add_generated_song(self, song):
        """
        Adauga un cantec in fisier
        :param song: cantecul
        """
        self.__songs.append(song)
        self.__store_in_file()

    def update_song(self, song):
        """
        Updateaza un cantec
        :param song: contine datele noului cantec
        :return:
        """
        if song not in self.__songs:
            raise SongNotFoundError()
        for i in self.__songs:
            if i.get_name() == song.get_name() and i.get_artist() == song.get_artist():
                i.set_genre(song.get_genre())
                i.set_year(song.get_year())
                i.set_month(song.get_month())
                i.set_day(song.get_day())
                self.__store_in_file()
                return

    def new_file(self, file):
        """
        Stocheaza cantecele in fisier
        """
        self.__songs.sort(key= lambda x: (x.get_year(), x.get_month(), x.get_day()))
        with open(file, 'w') as f:
            for song in self.__songs:
                line = song.get_name() + ', ' + song.get_artist() + ', ' + song.get_genre() + ', ' + song.get_day() + '.' + song.get_month() + '.' + song.get_year() + '\n'
                f.write(line)