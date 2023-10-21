
import unittest

from domain.entities import Song
from repository.song_repo_file import InMemorySongRepoFile
from domain.validators import SongValidator
from service.song_service import SongService

class TestCaseCreateSong(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateSong(self):
        song = Song('ceva', 'ceva', 'jazz', '3', '10', '2002')
        self.assertEqual(song.get_day(), '3', 'Wrong day')
        self.assertEqual(song.get_name(), 'ceva', 'Wrong name')

class TestCaseRepo(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        self.__repo = InMemorySongRepoFile('test.txt')

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testUpdate(self):
        song = Song('ana', 'ana', 'pop', '2', '2', '2020')
        self.__repo.update_song(song)
        self.assertEqual(song.get_name(), 'ana', "wrong")
        self.assertEqual(song.get_genre(), 'pop', 'wrong')

class TestCaseService(unittest.TestCase):
    def setUp(self):
        unittest.TestCase.setUp(self)
        self.__repo = InMemorySongRepoFile('test.txt')
        self.__val = SongValidator()
        self.__srv = SongService(self.__repo, self.__val)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testUpdate(self):
        """
        Verificare modificare
        :return:
        """
        song = Song('ana', 'ana', 'pop', '2', '2', '2020')
        self.__repo.update_song(song)
        self.assertEqual(song.get_name(), 'ana', "wrong")
        self.assertEqual(song.get_genre(), 'pop', 'wrong')


