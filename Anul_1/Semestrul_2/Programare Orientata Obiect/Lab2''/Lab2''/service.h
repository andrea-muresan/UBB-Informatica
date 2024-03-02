#pragma once

#include "repository.h"

typedef struct {
	repo* repo_lst;
}service;

service* create_service();

void destroy_service(service* srv);

repo* get_repo(service* serv);

int s_add_part(service* serv, participant* part);

int s_delete_part(service* srv, char *id);

int s_update_score(service* srv, char *id, int nr, int new_val);

int s_update_forename(service* srv, char *id, const char* new_val);

int s_update_surname(service* srv, char *id, const char* new_val);

int get_total_score_greater_than(repo* lst, service* srv, int score);

void sort_by_score(service* srv, int reverse);

void sort_by_name(service* srv, int reverse);


int cmp_score(participant* p1, participant* p2);

int cmp_surname(participant* p1, participant* p2);

typedef int(*f_cmp)(participant* p1, participant* p2);

void sort(repo* l, f_cmp func, int k);

void generate_part(service* srv);


// TESTS
void test_s_add_part();

void test_s_delete_part();

void test_s_update_score();

void test_s_update_forename();

void test_s_update_surname();

void test_get_total_score_greater_than();

void test_sort_by_score();

void test_sort_by_name();

void test_sort();

void test_generate_part();