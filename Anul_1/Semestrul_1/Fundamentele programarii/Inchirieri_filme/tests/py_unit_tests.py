from domain.entities import *
from domain.infos import ClientInfo
from domain.validators import *
from repository.movie_repo import *
from repository.movie_repo_file import *
from repository.client_repo import *
from repository.client_repo_file import *
from repository.rental_repo import *
from repository.rental_repo_file import *
from service.movie_service import *
from service.client_service import *
from sorting.sort_methods import mergesort, bingosort


import unittest


class TestCaseCreateRental(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.mv = Movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.cl = Client("cc1111", "Popescu Ion")
        self.rnt = Rental(self.mv, self.cl, 8)
        self.mv2 = Movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.cl2 = Client("cc1111", "Popescu Ion")
        self.rnt2 = Rental(self.mv2, self.cl2, 8)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def test_Rentals(self):
        self.assertEqual(self.rnt.get_movie(), self.mv, "Movies aren't the same")
        self.assertEqual(self.rnt.get_client(), self.cl, "Clients are not the same")
        self.assertEqual(self.rnt.get_date(), 8, "Dates are not the same")
        self.assertEqual(self.rnt, self.rnt2, "Rentals are not the same")


class TestCaseCreateMovie(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.mv = Movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv2 = Movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateMovie(self):
        self.assertEqual(self.mv.get_id(), "mm1111", "Incorrect id")
        self.assertEqual(self.mv.get_name(), "Harry Potter", "Incorrect name")
        self.assertEqual(self.mv.get_genre(), "fantasy", "Incorrect genre")
        self.assertEqual(self.mv.get_description(), "Wizards and...", "Incorrect description")

    def testSameStudent(self):
        self.assertEqual(self.mv, self.mv2, "Not the same students")

    def testSetName(self):
        self.assertEqual(self.mv.set_name("Now you see me"), "Now you see me", "Name was set incorrectly")

    def testSetGenre(self):
        self.assertEqual(self.mv.set_genre("thriller"), "thriller", "Genre was set incorrectly")

    def testSetDescription(self):
        self.assertEqual(self.mv.set_description("some description"), "some description", "Description was set incorrectly")


class TestCaseCreateClient(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.cl = Client("cc2222", "Metes Catalin")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateClient(self):
        self.assertEqual(self.cl.get_id(), "cc2222", "Incorrect id")
        self.assertEqual(self.cl.get_name(), "Metes Catalin", "Incorrect name")

    def testSetName(self):
        self.assertEqual(self.cl.set_name("Muresan Florica"), "Muresan Florica", "Name was set incorrectly")


class TestCaseClientInfo(unittest.TestCase):

    def setUp(self):
        self.mv = Movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.cl = Client("cc1111", "Popescu Ion")
        self.rnt = Rental(self.mv, self.cl, 8)
        self.mv2 = Movie("mm1111", "Now you see me", "thriller", "An American movie...")
        self.rnt2 = Rental(self.mv2, self.cl, 8)
        self.cl_info = ClientInfo(self.cl.get_id(), self.cl.get_name())

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testClientInfoGetters(self):
        self.assertEqual(self.cl_info.get_id(), self.cl.get_id(), "IDs are not matching")
        self.assertEqual(self.cl_info.get_name(), self.cl.get_name(), "Names are not matching")

    # def testClientNoMovies(self):
    #     self.assertEqual(self.cl_info.increase_no_movies(), 2, "Client's number of movies does not increase")


class TestCaseValidation(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.valid_mv = MovieValidation()
        self.valid_cl = ClientValidation()
        self.valid_rnt = RentalValidation()
        self.mv = Movie("", "Hary Potter", "fantasy", "Wizards and...")
        self.cl = Client("cc11", "Petrea Corina")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    # def testMovieValidation(self):
    #     self.assertRaises(InputError, self.valid_mv.validate, self.mv)
    #
    # def testClientValidation(self):
    #     self.assertRaises(InputError, self.valid_cl.validate, self.cl)


class TestCaseMovieRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.mv_repo = InMemoryRepositoryMovie()
        self.mv = Movie("mm1111", "Now you see me", "thriller", "An American movie...")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddMovie(self):
        self.mv_repo.store(self.mv)
        self.assertIn(self.mv, self.mv_repo.get_all_movies(), "Movie was not added")

    def testDeleteMovie(self):
        self.mv_repo.store(self.mv)
        self.mv_repo.delete_movie(self.mv.get_id())
        self.assertNotIn(self.mv, self.mv_repo.get_all_movies(), "Movie was not deleted")

    def testUpdateMovieName(self):
        self.mv_repo.store(self.mv)
        self.mv_repo.update_movie_name("mm1111", "Titanic")
        self.assertEqual(self.mv.get_name(), "Titanic", "Movie name was not updated")

    def testUpdateMovieGenre(self):
        self.mv_repo.store(self.mv)
        self.mv_repo.update_movie_genre("mm1111", "romance")
        self.assertEqual(self.mv.get_genre(), "romance", "Movie genre was not updated")

    def testUpdateMovieDescription(self):
        self.mv_repo.store(self.mv)
        self.mv_repo.update_movie_description("mm1111", "A gripping story...")
        self.assertEqual(self.mv.get_description(), "A gripping story...", "Movie description was not updated")

    def testSearchMovieByCriteria(self):
        self.mv_repo.store(self.mv)
        self.assertEqual(self.mv_repo.search_movie_by_criteria(lambda el: el.get_genre() == "thriller"), [self.mv], "The searched movies were not found")

class TestCaseMovieRepoFile(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.mv_repo = InMemoryRepositoryMovieFile("movies_test.txt")
        self.mv = Movie("mm1111", "Now you see me", "thriller", "An American movie...")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddMovie(self):
        self.mv_repo.store(self.mv)
        self.assertIn(self.mv, self.mv_repo.get_all_movies(), "Movie was not added")
        self.mv_repo.clear_file()

    def testDeleteMovie(self):
        self.mv_repo.store(self.mv)

        self.mv_repo.delete_movie("mm1111")
        self.assertNotIn(self.mv, self.mv_repo.get_all_movies(), "Movie was not deleted")
        self.mv_repo.clear_file()

    def testRecursiveDeleteMovie(self):
        self.mv_repo.store(self.mv)

        mv_list = self.mv_repo.get_all_movies()
        self.mv_repo.recursive_delete_movie("mm1111", mv_list)
        self.assertNotIn(self.mv, mv_list, "Movie was not deleted")
        self.mv_repo.clear_file()


class TestCaseClientRepo(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.cl_repo = InMemoryRepositoryClient()
        self.cl = Client("cc1111", "Popescu Ion")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddClient(self):
        self.cl_repo.store(self.cl)
        self.assertIn(self.cl, self.cl_repo.get_all_clients(), "Client was not added")

    def testDeleteMovie(self):
        self.cl_repo.store(self.cl)
        self.cl_repo.delete_client(self.cl.get_id())
        self.assertNotIn(self.cl, self.cl_repo.get_all_clients(), "Client was not deleted")

    def testUpdateClientName(self):
        self.cl_repo.store(self.cl)
        self.cl_repo.update_client_name("cc1111", "Maria")
        self.assertEqual(self.cl.get_name(), "Maria", "Client name was not updated")

    def testSearchClientByCriteria(self):
        self.cl_repo.store(self.cl)
        self.assertEqual(self.cl_repo.search_client_by_criteria(lambda el: el.get_id() == "cc1111"), [self.cl], "The searched client were not found")

class TestCaseClientRepoFile(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.cl_repo = InMemoryRepositoryClientFile("movies_test.txt")
        self.cl = Client("cc1111", "Popescu Ion")

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testAddMovie(self):
        self.cl_repo.store(self.cl)
        self.assertIn(self.cl, self.cl_repo.get_all_clients(), "Movie was not added")

    def testDeleteMovie(self):
        self.cl_repo.delete_client("cc1111")
        self.assertNotIn(self.cl, self.cl_repo.get_all_clients(), "Movie was not deleted")

class TestCaseMovieServive(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.mv_srv = MovieService(InMemoryRepositoryMovie(), MovieValidation())

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateMovie(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.assertEqual(mv.get_id(), "mm1111", "Movie was not created correctly (id problem)")
        self.assertEqual(mv.get_name(), "Harry Potter", "Movie was not created correctly (name problem)")
        self.assertEqual(mv.get_genre(), "fantasy", "Movie was not created correctly (genre problem)")
        self.assertEqual(mv.get_description(), "Wizards and...", "Movie was not created correctly (description problem)")

    def testAddMovie(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.assertIn(mv, self.mv_srv.get_all_movies(), "Movie was not added")

    def testDeleteMovie(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.mv_srv.delete_movie(mv.get_id())
        self.assertNotIn(mv, self.mv_srv.get_all_movies(), "Movie wss not deleted")

    def testUpdateMovieName(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.mv_srv.update_movie_name("mm1111", "Titanic")
        self.assertEqual(mv.get_name(), "Titanic", "Movie name was not updated")

    def testUpdateMovieGenre(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.mv_srv.update_movie_genre("mm1111", "romance")
        self.assertEqual(mv.get_genre(), "romance", "Movie genre was not updated")

    def testUpdateMovieDescription(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.mv_srv.update_movie_description("mm1111", "description")
        self.assertEqual(mv.get_description(), "description", "Movie description was not updated")

    def testSearchMovieByGenre(self):
        mv = self.mv_srv.create_movie("mm1111", "Harry Potter", "fantasy", "Wizards and...")
        self.mv_srv.add_movie(mv)
        self.assertEqual(self.mv_srv.search_movie_by_genre("fantasy"), [mv], "The searched movies were not found")

class TestCaseClientServive(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.cl_srv = ClientService(InMemoryRepositoryClient(), ClientValidation())

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testCreateMovie(self):
        cl = self.cl_srv.create_client("mm1111", "Harry Potter")
        self.assertEqual(cl.get_id(), "mm1111", "Movie was not created correctly (id problem)")
        self.assertEqual(cl.get_name(), "Harry Potter", "Movie was not created correctly (name problem)")

    def testAddMovie(self):
        cl = self.cl_srv.create_client("mm1111", "Harry Potter")
        self.cl_srv.add_client(cl)
        self.assertIn(cl, self.cl_srv.get_all_clients(), "Movie was not added")

    def testDeleteMovie(self):
        cl = self.cl_srv.create_client("mm1111", "Harry Potter")
        self.cl_srv.add_client(cl)
        self.cl_srv.delete_client(cl.get_id())
        self.assertNotIn(cl, self.cl_srv.get_all_clients(), "Client wss not deleted")

    def testUpdateMovieName(self):
        cl = self.cl_srv.create_client("mm1111", "Harry Potter")
        self.cl_srv.add_client(cl)
        self.cl_srv.update_client_name("mm1111", "Matei")
        self.assertEqual(cl.get_name(), "Matei", "Client name was not updated")

    def testSearchMovieByGenre(self):
        cl = self.cl_srv.create_client("mm1111", "Harry Potter")
        self.cl_srv.add_client(cl)
        self.assertEqual(self.cl_srv.search_client_by_id("mm1111"), [cl], "The searched client were not found")

class TestCaseSorts(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testMergeSort(self):
        lst_test = ['maria', 'bianca', 'carina', 'ana']
        lst_sort = mergesort(lst_test)
        lst_test.sort()
        self.assertEqual(lst_sort, lst_test)

        lst_test = ['maria', 'bianca', 'carina', 'ana']
        lst_sort = mergesort(lst_test, reverse=True)
        lst_test.sort(reverse=True)
        self.assertEqual(lst_sort, lst_test)

    def testBingoSort(self):
        lst_test = ['maria', 'bianca', 'carina', 'ana']
        lst_sort = bingosort(lst_test)
        lst_test.sort()
        self.assertEqual(lst_sort, lst_test)

        lst_test = ['maria', 'bianca', 'carina', 'ana']
        lst_sort = bingosort(lst_test, reverse=True)
        lst_test.sort(reverse=True)
        self.assertEqual(lst_sort, lst_test)


