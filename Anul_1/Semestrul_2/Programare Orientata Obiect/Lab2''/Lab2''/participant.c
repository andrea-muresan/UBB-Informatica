#define _CRT_SECURE_NO_WARNINGS
#define _CRTDBG_MAP_ALLOC
#include "participant.h"

#include <stdlib.h>
#include <string.h>
#include <assert.h>

participant* create_part(char *id, char* forename, char* surname, int *score) {
	/// Create a new participant
	/// : param forename : the participant's forename
	/// : param surname : the participant's surname
	/// : param score: the participant's scores
	/// : return: the participant | rtype: participant

	participant* part = (participant*)malloc(sizeof(participant));
	part->id = malloc(sizeof(char) * 4);
	strcpy(part->id, id);
	part->forename = malloc(sizeof(char) * (strlen(forename) + 1));
	strcpy(part->forename, forename);
	part->surname = malloc(sizeof(char) * (strlen(surname) + 1));
	strcpy(part->surname, surname);

	part->score = malloc(sizeof(int) * 11);
	for (int i = 1; i <= 10; i++)
		part->score[i] = score[i];

	return part;
}

void destroy_part(participant* part) {
	/// Destroy a participant - free the memory
	/// :param part: the participant
	free(part->forename);
	free(part->surname);
	free(part->id);
	free(part->score);
	free(part);
}

char* get_forename(participant* p) {
	/// : param p: the participant
	/// : return: the forename of a participant | rtype: string
	return p->forename;
}

char* get_id(participant* p) {
	/// : param p: the participant
	/// : return: the id of a participant | rtype: string
	return p->id;
}

char* get_surname(participant* p) {
	/// : param p: the participant
	/// : return: the surname of a participant | rtype: string
	return p->surname;
}

int get_score(participant* p, int n) {
	/// : param p: the participant
	/// : param n: the number of the problem
	/// : return: the score of a participant for the problem - n  | rtype: int
	return p->score[n];
}

int get_total_score(participant* p) {
	/// : param p: the participant
	/// : return: the total score of a participant  | rtype: int
	int res = 0;
	for (int i = 1; i <= 10; i++)
		res += p->score[i];
	return res;
}

void set_id(participant* p, const char* new_val) {
	/// Update a participant forename
	/// : param p: the participant
	/// : param new_val: the new forename
	strcpy(p->id, new_val);
}

void set_forename(participant* p, const char* new_val) {
	/// Update a participant forename
	/// : param p: the participant
	/// : param new_val: the new forename
	free(p->forename);
	p->forename = malloc(sizeof(char) * (strlen(new_val) + 1));
	strcpy(p->forename, new_val);
	//p->forename = _strdup(new_val);
}

void set_surname(participant* p, const char* new_val) {
	/// Update a participant surname
	/// : param p: the participant
	/// : param new_val: the new surname
	free(p->surname);
	p->surname = malloc(sizeof(char) * (strlen(new_val) + 1));
	strcpy(p->surname, new_val);
	// p->surname = _strdup(new_val);
}

void set_score(participant* p, int n, int new_val) {
	/// Update a participant score for the problem n
	/// : param p: the participant
	/// : param n: the number of the problem
	/// : param new_val: the new score
	p->score[n] = new_val;
}

void swap_parts(participant* a, participant* b) {
	// Swap two participants
	// :param a: the first participant
	// :param b: the second participant

	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* aux = create_part("111", "Carina", "Nastase", sc);
	set_id(aux, get_id(a));
	set_id(a, get_id(b));
	set_id(b, get_id(aux));

	set_forename(aux, get_forename(a));
	set_forename(a, get_forename(b));
	set_forename(b, get_forename(aux));

	set_surname(aux, get_surname(a));
	set_surname(a, get_surname(b));
	set_surname(b, get_surname(aux));

	for (int i = 1; i <= 10; i++) {
		set_score(aux, i,  get_score(a, i));
		set_score(a, i, get_score(b, i));
		set_score(b, i, get_score(aux, i));
	}

	destroy_part(aux);
}


// -----TESTS-----
void test_create_part() {
	/// Test for the function: create_part
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part = create_part("123", "Felix", "Sima", sc);

	assert(strcmp(get_forename(part), "Felix") == 0);
	assert(strcmp(get_surname(part), "Sima") == 0);
	assert(get_score(part, 7) == 7);
	assert(get_score(part, 5) == 5);

	destroy_part(part);

}

void test_set_part() {
	/// Test for the setters
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part = create_part("123", "Otilia", "Marculescu", sc);

	set_id(part, "111");
	assert(strcmp(get_id(part), "111") == 0);

	set_forename(part, "Mara");
	assert(strcmp(get_forename(part), "Mara") == 0);

	set_surname(part, "Zubascu");
	assert(strcmp(get_surname(part), "Zubascu") == 0);

	set_score(part, 1, 10);
	assert(get_score(part, 1) == 10);

	destroy_part(part);

}

void test_get_total_score() {
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part = create_part("123", "Stefan", "Gheorghidiu", sc);

	assert(get_total_score(part) == 55);

	destroy_part(part);

}

void test_swap_parts() {
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("123", "Costache", "Giurgiuveanu", sc);
	int sc2[] = { 0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0 };
	participant* part2 = create_part("123", "Matei", "Iliescu", sc2);

	swap_parts(part1, part2);
	assert(strcmp(get_forename(part1), "Matei") == 0);
	assert(strcmp(get_surname(part1), "Iliescu") == 0);
	assert(get_score(part1, 7) == 8);
	assert(get_score(part1, 5) == 6);

	assert(strcmp(get_forename(part2), "Costache") == 0);
	assert(strcmp(get_surname(part2), "Giurgiuveanu") == 0);
	assert(get_score(part2, 7) == 7);
	assert(get_score(part2, 5) == 5);

	destroy_part(part1);
	destroy_part(part2);

}