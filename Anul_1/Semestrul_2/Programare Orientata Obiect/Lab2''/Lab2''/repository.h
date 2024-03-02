#pragma once
#include "participant.h"

typedef struct {
	participant** parts;
	int dim, capacity;
}repo;

repo* create_repo();

void destroy_repo(repo* part_lst);

participant* get_participant(repo* part_lst, int pos);

int get_dim(repo* part_lst);

int inc_dim(repo* part_lst);

int dec_dim(repo* part_lst);

int get_capacity(repo* part_lst);

int r_add_part(repo* part_lst, participant* part);

int r_search_part(repo* part_lst, char* id);

int r_delete_part(repo* part_lst, char* id);

int r_update_score(repo* part_lst, char* id, int nr, int new_val);

int r_update_forename(repo* part_lst, char* id, const char* new_val);

int r_update_surname(repo* part_lst, char* id, const char* new_val);


// TESTS
void test_create_repo();

void test_get_participant();

void test_r_add_part();

void test_r_search_part();

void test_r_delete_part();

void test_r_update_score();

void test_r_update_forename();

void test_r_update_surname();