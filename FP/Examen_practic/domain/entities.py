
class Song:
    def __init__(self, name, artist, genre, day, month, year):
        self.__name = name
        self.__artist = artist
        self.__genre = genre
        self.__year = year
        self.__month = month
        self.__day = day

    def get_name(self):
        return self.__name

    def get_artist(self):
        return self.__artist

    def get_genre(self):
        return self.__genre

    def get_year(self):
        return self.__year

    def get_month(self):
        return self.__month

    def get_day(self):
        return self.__day

    def set_name(self, new_value):
        self.__name = new_value

    def set_artist(self, new_value):
        self.__artist = new_value

    def set_genre(self, new_value):
        self.__genre = new_value

    def set_year(self, new_value):
        self.__year = new_value

    def set_month(self, new_value):
        self.__month = new_value

    def set_day(self, new_value):
        self.__day = new_value

    def __eq__(self, other):
        if self.__name == other.get_name():
            if self.__artist == other.get_artist():
                return True
        return False

