#include "produsRepo.h"
#include "validator.h"
#include "produsService.h"
#include "console.h"
#include <assert.h>

#include <iostream>


void testAll() {
    testVector();
    testsRepo();
    testValidator();
    testCtr();
}

int main()
{
    //testAll();

    ProdusRepo rep;
    ProdusValidator val;
    ProdusStore ctr{ rep,val };
    ctr.adaugaCateva(); //adaug cateva produse
    ConsolUI ui{ ctr };
    ui.start();
    _CrtDumpMemoryLeaks();

    return 0;
}