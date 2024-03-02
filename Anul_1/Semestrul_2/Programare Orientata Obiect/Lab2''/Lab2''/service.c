#define _CRT_SECURE_NO_WARNINGS
#define _CRTDBG_MAP_ALLOC
#include "participant.h"
#include "repository.h"
#include "service.h"

#include <stdlib.h>
#include <stdio.h>
#include <assert.h>
#include <string.h>

service* create_service() {
	// Create the service
	// return: the service | type: service
	service* serv = (service*)malloc(sizeof(service));
	serv->repo_lst = create_repo();
	return serv;
}

void destroy_service(service* srv) {
	// Destroy the eservice - free the memory
	// :param srv: the service
	destroy_repo(srv->repo_lst);
	free(srv);
}

repo* get_repo(service *serv) {
	// : return: the service's repository | rtype: repo
	return serv->repo_lst;
}

int s_add_part(service* serv, participant* part) {
	// Add a participant in the list
	// return: 0 - if the size of the list was doubled, 1 - it wasn't
	return r_add_part(serv->repo_lst, part);
}

int s_delete_part(service* srv, char *id) {
	// Delete a participant from the list
	// : param  srv: the service
	// : param id: the participant's id
	// return: 1 - if the participant was deleted, 0 - it wasn't
	return r_delete_part(srv->repo_lst, id);
}

int s_update_score(service* srv, char *id, int nr, int new_val) {
	// Update a participant's score for the problem with number nr
	// return: 1 - if the score was updated, 0 - it wasn't
	return r_update_score(srv->repo_lst, id, nr, new_val);
}

int s_update_forename(service* srv, char *id, const char* new_val) {
	// Change a participant's forename
	// return: 1 - if the forename was updated, 0 - it wasn't
	return r_update_forename(srv->repo_lst, id, new_val);
}

int s_update_surname(service* srv, char *id, const char* new_val) {
	// Change a participant's surname
	// return: 1 - if the surname was updated, 0 - it wasn't
	return r_update_surname(srv->repo_lst, id, new_val);
}

int get_total_score_greater_than(repo* lst, service* srv, int score) {
	// Search the participant with a total score greater than a given score
	// :param lst: the list of participants that with good scores
	// :param srv: the service
	// :param score: the given score
	for(int i = 0; i < get_dim(get_repo(srv)); i++)
		if(get_total_score(get_participant(get_repo(srv), i)) >= score)
			r_add_part(lst, get_participant(get_repo(srv), i));

	return get_dim(lst);
}

void sort_by_score(service* srv, int reverse) {
	// Sort the list of participants by their scores
	// If the scores are equal, ascending by id
	// :param reverse: if it is 0 - ascensing sort/ 1 - descending sort
	for (int i = 0; i < get_dim(get_repo(srv)) - 1; i++) {
		int p = i;
		for (int j = i + 1; j < get_dim(get_repo(srv)); j++)
		{	
			participant* partj = get_participant(get_repo(srv), j);
			if (reverse == 0) {
				int scp = get_total_score(get_participant(get_repo(srv), p));
				int scj = get_total_score(partj);
				if (scp > scj)
					p = j;
				else if (scp == scj && atoi(get_id(get_participant(get_repo(srv), p))) > atoi(get_id(partj)))
					p = j;
			}
			else {
				int scp = get_total_score(get_participant(get_repo(srv), p));
				int scj = get_total_score(get_participant(get_repo(srv), j));
				if (scp < scj)
					p = j;
				else if (scp == scj && atoi(get_id(get_participant(get_repo(srv), p))) > atoi(get_id(partj)))
					p = j;
			}
			
		}
		swap_parts(get_participant(get_repo(srv), p), get_participant(get_repo(srv), i));
	}
	
}

void sort_by_name(service* srv, int reverse) {
	// Sort the list of participants by their surnames
	// If the surnames are eqauls, by their forenames
	// If they are equals too, ascending by id
	// :param reverse: if it is 0 - ascensing sort/ 1 - descending sort
	repo* rep = get_repo(srv);
	for (int i = 0; i < get_dim(rep) - 1; i++) {
		int p = i;
		for (int j = i + 1; j < get_dim(get_repo(srv)); j++)
		{
			participant* partj = get_participant(rep, j);
			if (reverse == 0) {
				char* srp = get_surname(get_participant(rep, p));
				char* srj = get_surname(partj);
				if (strcmp(srp, srj) > 0)
					p = j;
				else if (strcmp(srp, srj) == 0) {
					if (strcmp(get_forename(get_participant(rep, p)), get_forename(partj)) > 0)
						p = j;
					else if (strcmp(get_forename(get_participant(rep, p)), get_forename(partj)) == 0)
						if(atoi(get_id(get_participant(rep, p))) > atoi(get_id(partj)))
							p = j;
				}
			}
			else {
				//int scp = get_total_score(get_participant(rep, p));
				//int scj = get_total_score(get_participant(rep, j));
				char* srp = get_surname(get_participant(rep, p));
				char* srj = get_surname(partj);
				if (strcmp(srp, srj) < 0)
					p = j;
				else if (strcmp(srp, srj) == 0) {
					if (strcmp(get_forename(get_participant(rep, p)), get_forename(partj)) < 0)
						p = j;
					else if (strcmp(get_forename(get_participant(rep, p)), get_forename(partj)) == 0)
						if (atoi(get_id(get_participant(rep, p))) > atoi(get_id(partj)))
							p = j;
				}
			}

		}
		swap_parts(get_participant(rep, p), get_participant(rep, i));
	}

}
int cmp_score(participant* p1, participant* p2) {
	// Return if p1's score < p2's score
	
	return get_total_score(p1) > get_total_score(p2);
}

int cmp_surname(participant* p1, participant* p2) {
	// Return if p1's score < p2's score
	return strcmp(get_surname(p1), get_surname(p2));
}

void sort(repo* rep, f_cmp func, int reverse){
	// Sort a list of participants
	// : param rep: the list
	// : param func: the sort relation
	// : k = 0 -> ascending, k = 1 -> descending
	int i, j;
	for (i = 0; i < get_dim(rep); i++)
		for (j = i + 1; j < get_dim(rep); j++)
		{
			participant* p1 = get_participant(rep, i);
			participant* p2 = get_participant(rep, j);
			if (func(p1, p2) > 0 && reverse == 0)
			{
				swap_parts(p1, p2);
			}
			else if (func(p1, p2) <= 0 && reverse == 1)
			{
				swap_parts(p1, p2);
			}
		}
}


void generate_part(service* srv) {
	// Add a couple of random participants in the list
	// : param srv: the service
	// !! Use it just if your list is empty - it does not check if the ID is available
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Victoria", "Papucescu", sc);
	int sc2[] = { 0, 1, 2, 8, 4, 3, 3, 9, 1, 0, 0 };
	participant* part2 = create_part("222", "Mara", "Constantin", sc2);
	int sc3[] = { 0, 7, 2, 8, 4, 3, 3, 9, 1, 0, 9 };
	participant* part3 = create_part("333", "Matei", "Antonescu", sc3);

	s_add_part(srv, part1);
	s_add_part(srv, part2);
	s_add_part(srv, part3);
}


// -----TESTS-----
void test_s_add_part() {
	// Test for function: s_add_part
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	

	for (int i = 0; i < get_capacity(get_repo(serv)); i++) {
		participant* part = create_part("111", "Ana", "Popescu", sc);
		assert(s_add_part(serv, part) == 1);
	}
	
	participant* part = create_part("111", "Nechifor", "Lipan", sc);
	assert(s_add_part(serv, part) == 0);

	destroy_service(serv);
}

void test_s_delete_part() {
	// Test for function: s_delete_part
	service* serv = create_service();
	// participant* part;
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant *part = create_part("111", "Ana", "Popescu", sc);

	assert(get_dim(get_repo(serv)) == 0);
	assert(s_delete_part(serv, "111") == 0);
	assert(get_dim(get_repo(serv)) == 0);

	assert(s_add_part(serv, part) == 1);
	assert(get_dim(get_repo(serv)) == 1);

	assert(s_delete_part(serv, "111") == 1);
	assert(get_dim(get_repo(serv)) == 0);

	destroy_service(serv);
}

void test_s_update_score() {
	// Test for function: s_update_score
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant *part = create_part("111", "Ana", "Popescu", sc);

	assert(s_add_part(serv, part) == 1);

	assert(s_update_score(serv, "111", 5, 10) == 1);
	assert(get_score(get_participant(get_repo(serv), 0), 5) == 10);

	destroy_service(serv);
}

void test_s_update_forename() {
	// Test for function: s_update_forename
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant *part = create_part("111", "Ana", "Popescu", sc);

	assert(s_add_part(serv, part) == 1);

	assert(s_update_forename(serv, "111", "Andrei") == 1);
	assert(strcmp(get_forename(get_participant(get_repo(serv), 0)), "Andrei") == 0);

	destroy_service(serv);
}

void test_s_update_surname() {
	// Test for function: s_update_surname
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant *part = create_part("111", "Ana", "Popescu", sc);

	assert(s_add_part(serv, part) == 1);

	assert(s_update_surname(serv, "111", "Pop") == 1);
	assert(strcmp(get_surname(get_participant(get_repo(serv), 0)), "Pop") == 0);

	destroy_service(serv);
}

void test_get_total_score_greater_than() {
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);
	int sc2[] = { 0, 1, 2, 3, 4, 3, 2, 9, 1, 0, 0 };
	participant* part2 = create_part("222", "Mara", "Constantin", sc2);

	assert(s_add_part(serv, part1) == 1);
	assert(s_add_part(serv, part2) == 1);

	repo* lst = create_repo();
	assert(get_total_score_greater_than(lst, serv, 50) == 1);

	
	destroy_service(serv);
	free(lst->parts);
	free(lst);
}

void test_sort() {
	// Test for function: sort
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);
	int sc2[] = { 0, 1, 2, 3, 4, 10, 6, 7, 8, 9, 10 };
	participant* part2 = create_part("222", "Mara", "Constantin", sc2);
	int sc3[] = { 0, 1, 2, 3, 10, 10, 6, 7, 8, 9, 10 };
	participant* part3 = create_part("333", "Matei", "Teodor", sc3);

	assert(s_add_part(serv, part1) == 1);
	assert(s_add_part(serv, part3) == 1);
	assert(s_add_part(serv, part2) == 1);

	// Sort by score
	sort(get_repo(serv), cmp_score, 1);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "111") == 0);

	sort(get_repo(serv), cmp_score, 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "333") == 0);

	// Sort by name
	sort(get_repo(serv), cmp_surname, 1);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "222") == 0);

	sort(get_repo(serv), cmp_surname, 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "333") == 0);


	destroy_service(serv);
}

void test_sort_by_score() {
	// Test for function: sort_by_score
	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);
	int sc2[] = {0, 1, 2, 3, 4, 10, 6, 7, 8, 9, 10};
	participant* part2 = create_part("222", "Mara", "Constantin", sc2);
	int sc3[] = { 0, 1, 2, 3, 10, 10, 6, 7, 8, 9, 10 };
	participant* part3 = create_part("333", "Matei", "Teodor", sc3);
	participant* part4 = create_part("444", "Marian", "Ratiu", sc3);
	participant* part5 = create_part("555", "Marian", "Ratiu", sc3);

	assert(s_add_part(serv, part1) == 1);
	assert(s_add_part(serv, part4) == 1);
	assert(s_add_part(serv, part3) == 1);
	assert(s_add_part(serv, part2) == 1);
	assert(s_add_part(serv, part5) == 1);

	sort_by_score(serv, 1);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "444") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "555") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 3)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 4)), "111") == 0);

	sort_by_score(serv, 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 3)), "444") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 4)), "555") == 0);

	destroy_service(serv);
}

void test_sort_by_name() {
	// Test for function: sort_by_name

	service* serv = create_service();
	int sc[] = { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
	participant* part1 = create_part("111", "Ana", "Popescu", sc);
	participant* part2 = create_part("222", "Mara", "Constantin", sc);
	participant* part3 = create_part("333", "Matei", "Popescu", sc);
	participant* part4 = create_part("444", "Marian", "Ratiu", sc);
	participant* part5 = create_part("555", "Marian", "Ratiu", sc);

	assert(s_add_part(serv, part1) == 1);
	assert(s_add_part(serv, part2) == 1);
	assert(s_add_part(serv, part3) == 1);
	assert(s_add_part(serv, part4) == 1);
	assert(s_add_part(serv, part5) == 1);
	

	sort_by_name(serv, 1);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "444") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "555") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 3)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 4)), "222") == 0);
	

	sort_by_name(serv, 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 0)), "222") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 1)), "111") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 2)), "333") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 3)), "444") == 0);
	assert(strcmp(get_id(get_participant(get_repo(serv), 4)), "555") == 0);

	destroy_service(serv);
}

void test_generate_part() {
	// Test for function: generate_part
	service* srv = create_service();

	generate_part(srv);

	assert(get_dim(get_repo(srv)) == 3);

	destroy_service(srv);
}