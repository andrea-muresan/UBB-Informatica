import random

from domain.validators import *
from domain.entities import Song


class SongService:

    def __init__(self, song_repo, song_val):
        self.__repo = song_repo
        self.__val = song_val

    def get_all_songs(self):
        return self.__repo.get_all_songs()

    def create_song(self, name, artist, genre, day, month, year):
        """
        Creaza un cantec
        Updateaza un cantec
        :param name: nume
        :param artist: artist
        :param genre: genul
        :param day: ziua
        :param month: luna
        :param year: anul
        """
        song = Song(name, artist, genre, day, month, year)
        self.__val.validate(song)

        return song

    def update_song(self, name, artist, genre, day, month, year):
        """
        Updateaza un cantec
        :param name: nume
        :param artist: artist
        :param genre: genul
        :param day: ziua
        :param month: luna
        :param year: anul
        """
        song = self.create_song(name, artist, genre, day, month, year)
        self.__repo.update_song(song)

    def generate_songs(self, nr, names, artists):
        """
        Genereaza cantece
        :param nr: cate cantece
        :param names: lista de nume posibile
        :param artists: lista de artisti posibili
        """
        all_songs = []
        genre_list = ['pop', 'rock', 'jazz']
        for i in names:
            for j in artists:
                name = i
                artist = j
                genre = random.choice(genre_list)
                day = str(random.randint(1, 29))
                month = str(random.randint(1, 13))
                year = str(random.randint(1996, 2023))
                song = Song(name, artist, genre, day, month, year)
                all_songs.append(song)

        file_songs = self.get_all_songs()
        for song in all_songs:
            if song in file_songs:
                all_songs.remove(song)

        if nr > len(all_songs):
            print("It's not possible")
            return

        for i in range(nr):
            self.__repo.add_generated_song(all_songs[i])

    def new_file(self, file):
        """
        Stocheza in alt fisier
        :param file:
        :return:
        """
        self.__repo.new_file(file)



