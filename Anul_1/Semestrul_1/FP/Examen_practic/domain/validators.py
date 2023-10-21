
from domain.entities import Song
import datetime


class SongValidator:

    def validate(self, song):
        errors = []
        if len(song.get_name()) < 2:
            errors.append("Name must have at least 2 characters")
        if len(song.get_artist()) < 2:
            errors.append("Artist must have at least 2 characters")
        genre_list = ['rock', 'pop', 'jazz']
        if song.get_genre() not in genre_list:
            errors.append("Genre must be: rock, pop or jazz")
        try:
            date = datetime.date(int(song.get_year()), int(song.get_month()), int(song.get_day()))
        except ValueError:
            errors.append("Invalid date")

        if len(errors) > 0:
            error_string = ''.join(errors)
            raise InputError(error_string)


class InputError(Exception):

    def __init__(self, errors):
        self.__errors = errors

    def get_errors(self):
        return self.__errors


class FileNotFoundError(Exception):

    def __init__(self):
        self.__errors = "File not found"

    def get_errors(self):
        return self.__errors


class SongNotFoundError(Exception):

    def __init__(self):
        self.__errors = "Song not found"

    def get_errors(self):
        return self.__errors
