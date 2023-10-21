
from domain.entities import Imobil
from repository.imobil_repo import InMemoryImobilFile
from domain.validator import *
from service.imobil_service import ImobilService

import unittest

class TestCaseCreateImobil(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.im = Imobil('23', 'Cluj', '200', 'vanzare')

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testGetters(self):
        self.assertEqual(self.im.get_id(), '23', "ID incorect")
        self.assertEqual(self.im.get_adresa(), 'Cluj', "Adresa incorecta")
        self.assertEqual(self.im.get_tip_oferta(), 'vanzare', "Tip oferta incorect")
        self.assertEqual(self.im.get_pret(), '200', "Pret incorect")


class TestCaseImobilRepoFile(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.imobil_repo = InMemoryImobilFile("file_test.txt")
        # self.im1 = Imobil('23', 'str. Cernei nr. 10 Cluj Napoca' , '600', 'vanzare')

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testGetAllVanzare(self):
        self.assertEqual(len(self.imobil_repo.get_all_vanzare()), 2, 'Nu s-au luat toate imobilele de vanzare')

    def testGetAllInchiriere(self):
        self.assertEqual(len(self.imobil_repo.get_all_inchiriere()), 2, 'Nu s-au luat toate imobilele de vanzare')

    def testSearchImobilByID(self):
        im = self.imobil_repo.search_imobil_by_id('23')
        self.assertEqual(im.get_id(), '23', 'Cautarea nu a functionat')


class TestCaseImobilService(unittest.TestCase):

    def setUp(self):
        unittest.TestCase.setUp(self)
        self.imobil_repo = InMemoryImobilFile("file_test.txt")
        self.imobil_val = ImobilValidator()
        self.imobil_srv = ImobilService(self.imobil_repo, self.imobil_val)

    def tearDown(self):
        unittest.TestCase.tearDown(self)

    def testGetMediaVanzare(self):
        media = self.imobil_srv.get_media_vanzare()
        self.assertEqual(media, 800, "Media nu a fost calculata corect")

    def testGetMediaInchiriere(self):
        media = self.imobil_srv.get_media_inchiriere()
        self.assertEqual(media, 300, "Media nu a fost calculata corect")

    def testSearchImobilByID(self):
        im = self.imobil_srv.search_imobil_by_id('23')
        self.assertEqual(im.get_id(), '23', 'Cautarea nu a functionat')

    def testComision(self):
        comision = self.imobil_srv.comision('vanzare', 400)
        self.assertEqual(comision, 8, "Comision incorect")
        comision2 = self.imobil_srv.comision('inchiriere', 400)
        self.assertEqual(comision2, 200 , "Comision incorect")
