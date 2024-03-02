#define _CRTDBG_MAP_ALLOC
#include <stdlib.h>
#include <crtdbg.h>
#include <stdio.h>

#include "participant.h"
#include "repository.h"
#include "service.h"
#include "console.h"
#include "validate.h"


void tests();

int main()
{
    long lBreakAlloc = 0;
    if (lBreakAlloc > 0)
    {
        _CrtSetBreakAlloc(lBreakAlloc);
    }
    tests();
    start();

    _CrtDumpMemoryLeaks();

    return 0;
}

void tests() {
    // Participant
    test_create_part();
    test_set_part();
    test_get_total_score();
    test_swap_parts();

    // Repo
    test_create_repo();
    test_get_participant();
    test_r_add_part();
    test_r_search_part();
    test_r_delete_part();
    test_r_update_score();
    test_r_update_surname();
    test_r_update_forename();

    // Service
    test_s_add_part();
    test_s_delete_part();
    test_s_update_score();
    test_s_update_forename();
    test_s_update_surname();
    test_get_total_score_greater_than();
    test_sort_by_score();
    test_sort_by_name();
    test_sort();
    test_generate_part();

    // Validate
    test_validate_score();
    test_validate_word();
    test_validate_id();
}