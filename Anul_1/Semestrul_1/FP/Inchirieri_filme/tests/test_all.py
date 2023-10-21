from domain.entities import Movie, Client
from repository.movie_repo import InMemoryRepositoryMovie
from repository.client_repo import InMemoryRepositoryClient
from repository.rental_repo import InMemoryRepositoryRental
from domain.validators import *
from service.movie_service import MovieService
from service.client_service import ClientService


# entities
def test_create_movie():
    movie1 = Movie('ll2233', 'Harry Potter', 'fantasy', 'A gripping story about...')
    assert (movie1.get_id() == 'll2233')
    assert (movie1.get_name() == 'Harry Potter')
    assert (movie1.get_genre() == 'fantasy')
    assert (movie1.get_description() == 'A gripping story about...')

    movie1.set_id('lm4444')
    movie1.set_name('Now you see me')
    movie1.set_genre('thriller')
    movie1.set_description('An American movie...')

    assert (movie1.get_id() == 'lm4444')
    assert (movie1.get_name() == 'Now you see me')
    assert (movie1.get_genre() == 'thriller')
    assert (movie1.get_description() == 'An American movie...')


def test_create_client():
    client = Client('mm2222', 'Matei')
    assert (client.get_id() == 'mm2222')
    assert (client.get_name() == 'Matei')

    client.set_id('ll1111')
    client.set_name('Maria')

    assert (client.get_id() == 'll1111')
    assert (client.get_name() == 'Maria')


# repo
def test_repo_movie():
    tst = InMemoryRepositoryMovie()
    movie1 = Movie('ll0000', 'Harry Potter', 'fantasy', 'A gripping story about...')
    length = len(tst.get_all_movies())

    tst.store(movie1)
    assert (len(tst.get_all_movies()) == length + 1)


def test_repo_client():
    tst = InMemoryRepositoryClient()
    client1 = Client('mm1111', 'Matei')
    length = len(tst.get_all_clients())

    tst.store(client1)
    length += 1
    assert (len(tst.get_all_clients()) == length)


# validators
def test_validation():
    tst = MovieValidation()
    movie1 = Movie('ll2233', 'Harry Potter', 'fantasy', 'A gripping story about...')
    tst.validate(movie1)

    movie2 = Movie('ll223', 'Now you see me', 'thriller', 'An American movie...')
    try:
        tst.validate(movie2)
        assert False
    except ValueError:
        assert True

    movie3 = Movie('ll4444', 'N', 'thriller', 'An American movie...')
    try:
        tst.validate(movie3)
        assert False
    except ValueError:
        assert True

    movie4 = Movie('ll4444', 'Now you see me', '', 'An American movie...')
    try:
        tst.validate(movie4)
        assert False
    except ValueError:
        assert True

    movie5 = Movie('ll4444', 'Now you see me', 'thriller', 'An')
    try:
        tst.validate(movie5)
        assert False
    except ValueError:
        assert True

# service
def test_add_delete_movie():
    repo = InMemoryRepositoryMovie()
    validator = MovieValidation()
    tst = MovieService(repo, validator)
    movie1 = Movie('ll0000', 'Harry Potter', 'fantasy', 'A gripping story about...')
    length = len(tst.get_all_movies())

    tst.add_movie(movie1)
    length += 1
    assert (len(tst.get_all_movies()) == length)


    tst.delete_movie('ll0000')
    length -= 1
    assert (len(tst.get_all_movies()) == length)

def test_add_delete_client():
    repo = InMemoryRepositoryClient()
    validator = ClientValidation()
    tst = ClientService(repo, validator)
    length = len(tst.get_all_clients())

    client1 = tst.create_client('mm1122', 'Carmen')

    lst_client = tst.add_client(client1)
    length += 1
    assert (len(tst.get_all_clients()) == length)

    lst_client = tst.delete_client(client1.get_id())
    length -= 1
    assert (len(tst.get_all_clients()) == length)


# EVERYTHING

def test_all():
    test_create_movie()
    test_repo_movie()
    test_validation()
    test_add_delete_movie()
    test_create_client()
    test_repo_client()
    test_add_delete_client()
