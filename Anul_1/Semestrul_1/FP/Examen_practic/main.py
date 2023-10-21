
from repository.song_repo_file import InMemorySongRepoFile
from domain.validators import SongValidator
from service.song_service import SongService
from ui.console import Console

song_repo = InMemorySongRepoFile("songs.txt")
song_val = SongValidator()
song_srv = SongService(song_repo, song_val)

ui = Console(song_srv)
ui.start()