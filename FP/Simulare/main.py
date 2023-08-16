
from repository.imobil_repo import InMemoryImobilFile
from domain.validator import ImobilValidator
from service.imobil_service import ImobilService
from ui.console import Console

imobil_repo = InMemoryImobilFile("files\imobile.txt")
imobil_val = ImobilValidator()
imobil_srv = ImobilService(imobil_repo, imobil_val)

ui = Console(imobil_srv)
ui.start()
