
from domain.entities import Movie, Client


class MovieValidation:
    """
    Validate the input data for a movie
    """
    def validate(self, movie):
        errors = []
        if len(movie.get_id()) != 6:
            errors.append("ID must have 6 characters.")
        if movie.get_id()[:2].isalpha() is False or movie.get_id()[2:].isdigit() is False:
            errors.append("ID format is incorrect! Please follow the tamplate: lldddd (l = letter, d = digit).")
        if len(movie.get_name()) < 2:
            errors.append("Name must have at least 2 characters.")
        if len(movie.get_genre()) < 2:
            errors.append("Genre must have at least 2 characters.")
        if len(movie.get_description()) < 4:
            errors.append("Description must have at least 4 characters.")

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class ClientValidation:
    """
    Validate the input for a client
    """
    def validate(self, client):
        errors = []
        if len(client.get_id()) != 6:
            errors.append("ID must have 6 characters.")
        if client.get_id()[:2].isalpha() is False or client.get_id()[2:].isdigit() is False:
            errors.append("ID format is incorrect! Please follow the tamplate: lldddd (l = letter, d = digit).")
        if len(client.get_name()) < 2:
            errors.append("Name must have at least 2 characters.")

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class RentalValidation:
    """
    Validate the data of a rental
    """
    def validate(self, rental):
        errors = []
        if rental.get_date() < 0 or rental.get_date() > 31:
            errors.append('Date must be from 1 to 31')

        if len(errors) > 0:
            errors_string = '\n'.join(errors)
            raise ValueError(errors_string)

class IdNotFoundError(Exception):
    """
    Return hidden message if the id can't be found
    """
    def __init__(self):
        self.__error = "ID not found"

    def get_errors(self):
        return self.__error

class IdTakenError(Exception):
    """
    Return hidden message if the id is already taken
    """
    def __init__(self):
        self.__errors = "ID is taken"

    def get_errors(self):
        return self.__errors

class InputError(Exception):
    """
    Return hidden message if input data is incorrect
    """
    def __init__(self, errors):
        self.__errors = errors

    def get_errors(self):
        return self.__errors

class FileNotReadError(Exception):
    """
    Return hidden message if the file is unable to be read
    """
    def __init__(self):
        self.__errors = "Unable to read from file"

    def get__errors(self):
        return self.__errors

class DuplicatedDataError(Exception):
    """
    Return hidden message if the object can't be put in the file
    """
    def __init__(self):
        self.__errors = "Duplicated data. Can't load it in the file"

    def get_errors(self):
        return self.__errors

class RentalNotFoundError(Exception):
    """
    Return hidden message if the rental is not found
    """
    def __init__(self):
        self.__errors = "Rental not found"

    def get_errors(self):
        return self.__errors

class ClientNotFoundError(Exception):
    """
    Return hidden message if the client is not found
    """
    def __init__(self):
        self.__errors = "Client not found"

    def get_errors(self):
        return self.__errors

class MovieNotFoundError(Exception):
    """
    Return hidden message if the movie is not found
    """
    def __init__(self):
        self.__errors = "Movie not found"

    def get_errors(self):
        return self.__errors
