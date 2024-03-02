#pragma once

typedef struct {
	char *forename, *surname, *id;
	int *score;
}participant;

participant* create_part(char *id, char *forename, char *surname, int *score);

void destroy_part(participant* part);

char* get_id(participant* p);

char* get_forename(participant* p);

char* get_surname(participant* p);

int get_score(participant* p, int n);

int get_total_score(participant* p);

void set_id(participant* p, const char* new_val);

void set_forename(participant* p, const char* new_val);

void set_surname(participant* p, const char* new_val);

void set_score(participant* p, int n, int new_val);

void swap_parts(participant* a, participant* b);


//TESTS
void test_create_part();

void test_set_part();

void test_get_total_score();

void test_swap_parts();