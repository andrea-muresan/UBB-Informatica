#define _CRT_SECURE_NO_WARNINGS
#include "repository.h"
#include "participant.h"

#include <malloc.h>
#include <assert.h>
#include <stdio.h>
#include <string.h>

repo* create_repo() {
	// Create a list of participants
	// :return: the repo
	repo* part_lst = (repo*)malloc(sizeof(repo));
	part_lst->dim = 0;
	part_lst->capacity = 100;
	part_lst->parts = (participant**)malloc(sizeof(participant*) * (part_lst->capacity));
	return part_lst;
}

void destroy_repo(repo* part_lst) {
	// Destroy the list of participants - free the memory
	// : param part_lst: the list of participants
	for (int i = 0; i < get_dim(part_lst); i++)
		destroy_part(get_participant(part_lst, i));
	free(part_lst->parts);
	free(part_lst);
}

void resize_repo(repo* part_lst) {
	// Double the capacity of the repo list
	repo* r_cpy = (repo*)malloc(sizeof(repo));
	r_cpy->dim = 0;
	r_cpy->capacity = get_capacity(part_lst) * 2;
	r_cpy->parts = (participant**)malloc(sizeof(participant*) * part_lst->capacity);

	for (int i = 0; i < get_dim(part_lst); i++)
		r_add_part(r_cpy, get_participant(part_lst, i));

	free(part_lst->parts);

	part_lst->capacity = r_cpy->capacity;
	part_lst->dim = 0;
	part_lst->parts = (participant**)malloc(sizeof(participant*) * (part_lst->capacity));
	for (int i = 0; i < get_dim(r_cpy); i++)
		r_add_part(part_lst, get_participant(r_cpy, i));
	free(r_cpy->parts);
	free(r_cpy);
}

participant* get_participant(repo* part_lst, int pos) {
	// : param l: the list of participants
	// : param poz: the position in the list
	return part_lst->parts[pos];
}

int get_dim(repo* part_lst) {
	// : param part_lst: the list of participants
	// : return: the dimension of the list
	return part_lst->dim;
}

int inc_dim(repo* part_lst) {
	// Increment the list dimension
	// : param part_lst: the list of participants
	// : return: the dimension | rtype: int
	part_lst->dim += 1;
	return part_lst->dim;
}

int dec_dim(repo* part_lst) {
	// Decrement the list dimension
	// : param part_lst: the list of participants
	// :return: the dimension | rtype: int
	part_lst->dim -= 1;
	return part_lst->dim;
}

int get_capacity(repo* part_lst) {
	// : param part_lst: the list of participants
	// : return: the capacity of the list | rtype: int
	return part_lst->capacity;
}

int r_add_part(repo* part_lst, participant* part) {
	// Add a participant to the list
	// : param part_lst: the list of participants
	// : param part: the participant
	// : return: 0 - the size of the list was doubled, 1 - it wasn't
	if (get_dim(part_lst) < get_capacity(part_lst)) {
		(part_lst->parts)[part_lst->dim] = part;
		inc_dim(part_lst);
		return 1;
	}
	else {
		resize_repo(part_lst);
		(part_lst->parts)[part_lst->dim] = part;
		inc_dim(part_lst);
		return 0;
	}
}

int r_search_part(repo* part_lst, char* id) {
	// Checks if a participant is in the list
	// : param part_lst: the list of participants
	// : param id: the participant's id
	// : return: the position of the participant in the list, -1 if it is not in the list
	for (int i = 0; i < get_dim(part_lst); i++)
		if (strcmp(get_id(get_participant(part_lst, i)), id) == 0)
		if (strcmp(get_id(get_participant(part_lst, i)), id) == 0)
			return i;
	return -1;
}

int r_delete_part(repo* part_lst, char* id) {
	// Delete a participant from the list
	// : param part_lst: the list of participants
	// : param id: the participant's id
	// : return: 1 - the participant was deleted, 0 - it wasn't
	int poz = r_search_part(part_lst, id);
	if (poz == -1)
		return 0;
	destroy_part(part_lst->parts[poz]);
	for (int i = poz; i < get_dim(part_lst); i++)
		part_lst->parts[i] = part_lst->parts[i + 1];
	dec_dim(part_lst);
	return 1;
}

int r_update_score(repo* part_lst, char* id, int nr, int new_val) {
	// Update a participant's score for the problem with number nr
	// : return: 1 - the score was updated, 0 - it wasn't
	int poz = r_search_part(part_lst, id);
	if (poz == -1)
		return 0;
	set_score(part_lst->parts[poz], nr, new_val);
	return 1;
}

int r_update_forename(repo* part_lst, char* id, const char* new_val) {
	// Change a participant's forename 
	// : return: 1 - the forename was updated, 0 - it wasn't
	int poz = r_search_part(part_lst, id);
	if (poz == -1)
		return 0;
	set_forename(part_lst->parts[poz], new_val);
	return 1;
}

int r_update_surname(repo* part_lst, char* id, const char* new_val) {
	// Change a participant's surname 
	// : return: 1 - the surname was updated, 0 - it wasn't
	int poz = r_search_part(part_lst, id);
	if (poz == -1)
		return 0;
	set_surname(part_lst->parts[poz], new_val);
	return 1;
}



// -----TESTS-----
void test_create_repo() {
	// Test fot the function: create_repo
	repo* l = create_repo();
	assert(get_dim(l) == 0);
	assert(get_capacity(l) == 100);
	assert(inc_dim(l) == 1);
	assert(dec_dim(l) == 0);

	destroy_repo(l);
}

void test_get_participant() {
	// Test fot the function get_participant
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant *part1 = create_part("111", "Miruna", "Petrea", sc);

	l->parts[0] = part1;
	inc_dim(l);

	assert(strcmp(get_forename(get_participant(l, 0)), "Miruna") == 0);
	assert(strcmp(get_forename(get_participant(l, 0)), "Mara") != 0);
	assert(strcmp(get_surname(get_participant(l, 0)), "Petrea") == 0);
	assert(get_score(get_participant(l, 0), 1) == 1);

	destroy_repo(l);
	
}

void test_r_add_part() {
	// Test for function: r_add_part
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };

	for (int i = 0; i < get_capacity(l); i++) {
		participant* part = create_part("111", "Micki", "Holmgrean", sc);
		assert(r_add_part(l, part) == 1);
	}
	
	participant* part = create_part("111", "Ana", "Popescu", sc);
	assert(r_add_part(l, part) == 0);

	destroy_repo(l);
}

void test_r_search_part() {
	// Test for function: r_search_part
	repo* r = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Laura", "Cardos", sc);
	participant* part2 = create_part("122", "Mariana", "Paulescu", sc);

	r_add_part(r, part1);
	r_add_part(r, part2);

	assert(r_search_part(r, "111") == 0);
	assert(r_search_part(r, "122") == 1);
	assert(r_search_part(r, "123") == -1);

	destroy_repo(r);
}

void test_r_delete_part() {
	// Test for function: r_delete_part
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Alex", "Baicus", sc);
	participant* part2 = create_part("222", "Vasile", "Pruscov", sc);

	assert(r_add_part(l, part1) == 1);
	assert(get_dim(l) == 1);

	assert(r_add_part(l, part2) == 1);
	assert(get_dim(l) == 2);


	assert(r_delete_part(l, "111") == 1);
	assert(get_dim(l) == 1);

	assert(r_delete_part(l, "111") == 0);

	destroy_repo(l);
}

void test_r_update_score() {
	// Test for function: r_update_part
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);

	assert(r_add_part(l, part1) == 1);
	r_update_score(l, "111", 5, 10);
	assert(get_score(get_participant(l, 0), 5) == 10);

	assert(r_update_score(l, "112", 5, 10) == 0);

	destroy_repo(l);
}

void test_r_update_forename() {
	// Test for function: r_update_forename
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);

	assert(r_add_part(l, part1) == 1);
	r_update_forename(l, "111", "Maria");
	assert(strcmp(get_forename(get_participant(l, 0)), "Maria") == 0);
	assert(r_update_forename(l, "112", "Andrea") == 0);

	destroy_repo(l);
}

void test_r_update_surname() {
	// Test for function: r_update_surname
	repo* l = create_repo();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);

	assert(r_add_part(l, part1) == 1);
	r_update_surname(l, "111", "Pop");
	assert(strcmp(get_surname(get_participant(l, 0)), "Pop") == 0);

	assert(r_update_surname(l, "112", "Popa") == 0);

	destroy_repo(l);
}