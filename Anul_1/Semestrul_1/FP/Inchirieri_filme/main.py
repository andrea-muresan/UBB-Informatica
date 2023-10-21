from domain.validators import *
from repository.movie_repo import InMemoryRepositoryMovie
from repository.client_repo import InMemoryRepositoryClient
from repository.rental_repo import InMemoryRepositoryRental
from service.movie_service import MovieService
from service.client_service import ClientService
from service.rental_service import RentalService
from ui.console import Console
from tests.test_all import test_all
from repository.client_repo_file import InMemoryRepositoryClientFile
from repository.movie_repo_file import InMemoryRepositoryMovieFile
from repository.rental_repo_file import InMemoryRepositoryRentalFile

# test_all()

# repo_movie = InMemoryRepositoryMovie()
repo_movie = InMemoryRepositoryMovieFile("movies.txt")
val_movie = MovieValidation()
movie_srv = MovieService(repo_movie, val_movie)

# repo_client = InMemoryRepositoryClient()
repo_client = InMemoryRepositoryClientFile("clients.txt")
val_client = ClientValidation()
client_srv = ClientService(repo_client, val_client)

# repo_rental = InMemoryRepositoryRental()
repo_rental = InMemoryRepositoryRentalFile("rentals.txt", repo_movie, repo_client)
val_rental = RentalValidation()
rental_srv = RentalService(repo_rental, val_rental, repo_movie, repo_client)

ui = Console(movie_srv, client_srv, rental_srv)
ui.start()

