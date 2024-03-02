#define _CRT_SECURE_NO_WARNINGS
#define _CRT_SECURE_NO_DEPRECATE  
#define _CRT_NONSTDC_NO_DEPRECATE
#include "participant.h"
#include "repository.h"
#include "service.h"
#include "console.h"
#include "validate.h"

# include <stdlib.h>
#include <stdio.h>
#include <string.h>

console* create_console() {
	// Create the console and return it
	// : return: the console | rtype: console
	console* cons = (console*)malloc(sizeof(console));
	cons->srv = create_service();
	return cons;
}

service* get_service(console* cons) {
	// : param cons: the console
	// : return: the console's service | rtype: service
	return cons->srv;
}


void print_main_menu() {
	// Print the main menu of the app
	printf("---------------------------------\n");
	printf("      CONCURS DE PROGRAMARE      \n");
	printf("---------------------------------\n");
	printf(" Alege o optiune:                \n");
	printf("---------------------------------\n");
	printf(" 1 - Adauga participant          \n");
	printf(" 2 - Actualizeaza participant    \n");
	printf(" 3 - Sterge participant          \n");
	printf(" 4 - Filtru - scor mai mare decat\n");
	printf(" 5 - Sortari                     \n");
	printf(" p - Printeaza toti participantii\n");
	printf(" x - Iesire                      \n");
	printf("---------------------------------\n");
}

void print_update_menu() {
	// Print the option for updates
	printf("\n");
	printf("------------\n");
	printf("UPDATE:     \n");
	printf("------------\n");
	printf(" 1 - prenume\n");
	printf(" 2 - nume   \n");
	printf(" 3 - scor   \n");
	printf("------------\n");
}

void print_sort_menu() {
	// Print the sort option
	printf("\n");
	printf("------------------------\n");
	printf("SORT:					\n");
	printf("------------------------\n");
	printf(" 1 - scor - crescator	\n");
	printf(" 2 - scor - descrescator\n");
	printf(" 3 - nume - crescator	\n");
	printf(" 4 - nume - descrescator\n");
	printf("------------------------\n");
}

void print_successful_operation() {
	printf("Operatie realizata cu succes\n\n");
}

void print_unsuccessful_operation() {
	printf("Operatia nu s-a realizat cu succes\n\n");
}

void print_all_part_repo(repo *rep) {
	// Print all participants
	// : param repo: the repo
	if (get_dim(rep) == 0)
		printf("Nu exista participanti\n");
	else {
		for (int i = 0; i < get_dim(rep); i++) {
			participant* p = get_participant(rep, i);
			printf(" - ID: %s | NUME: %s | PRENUME: %s |",
				get_id(p), get_surname(p), get_forename(p));
			for (int j = 1; j <= 10; j++)
				printf("Scor%d: %d | ", j, get_score(p, j));
			printf("Scor total: %d", get_total_score(get_participant(rep, i)));
			printf("\n");
		}
	}
	printf("\n");

}

const char* read_id() {
	// Read a valid id
	// : return: forename | rtype: string
	static char id[10];
	printf("ID (3 cifre): ");
	scanf_s("%s", id, 10);

	while (validate_id(id) == 0) {
		printf("Invalid. ID-ul trebuie sa contina 3 cifre. Incearca din nou: \nID: ");
		scanf_s("%s", id, 10);
	}

	return id;
}

const char* read_forename() {
	// Read a valid forename
	// : return: forename | rtype: string
	static char forename[25];
	printf("PRENUME: ");
	scanf_s("%s", forename, 24);

	while (validate_word(forename) == 0) {
		printf("Invalid. Prenumele poate contine doar litere, fara spatii. Incearca din nou: \nPRENUME: ");
		scanf_s("%s", forename, 24);
	}

	return forename;
}

const char* read_surname() {
	// Read a valid surname
	// : return: the surname | rtype: string
	static char surname[25];
	printf("NUME: ");
	scanf_s("%s", surname, 24);

	while (validate_word(surname) == 0) {
		printf("Invalid. Numele poate contine doar litere. Incearca din nou: \nNUME: ");
		scanf_s("%s", surname, 24);
	}

	return surname;
}

int read_score(int i) {
	// Read a valid score
	// : param i: the number of the problem
	// : return: the score | rtype: int
	char score[20];
	printf("SCOR: ");
	scanf("%s", &score);

	while (validate_score(score) == -1) {
		printf("Invalid. Scorul trebuie sa fie un numar de la 0 la 10. Incearca din nou: \nP%d SCOR: ", i);
		scanf("%s", &score);
	}

	return validate_score(score);
}

participant* read_part(service* srv) {
	// Read a NEW participant valid data and create the participant
	// : return: the participant | rtype: participant
	char forename[25], surname[25], id[25];
	int score[15];

	strcpy(id, read_id());
	while (r_search_part(get_repo(srv), id) != -1) {
		printf("ID-ul este deja luat. Incearca din nou: \n");
		strcpy(id, read_id());
	}

	strcpy(forename, read_forename());
	strcpy(surname, read_surname());
	for (int i = 1; i <= 10; i++) {
		printf("P%d ", i);
		score[i] = read_score(i);
	}


	participant* part = create_part(id, forename, surname, score);
	return part;
}

void call_add_part(service* srv) {
	// Call the function for adding a participant
	/*participant* part;
	part = read_part(srv);*/

	// Read a NEW participant valid data and create the participant
	// : return: the participant | rtype: participant
	char forename[25], surname[25], id[25];
	int score[15];

	strcpy(id, read_id());
	while (r_search_part(get_repo(srv), id) != -1) {
		printf("ID-ul este deja luat. Incearca din nou: \n");
		strcpy(id, read_id());
	}

	strcpy(forename, read_forename());
	strcpy(surname, read_surname());
	for (int i = 1; i <= 10; i++) {
		printf("P%d ", i);
		score[i] = read_score(i);
	}

	s_add_part(srv, create_part(id, forename, surname, score));
	print_successful_operation();
	//destroy_part(part);
}

void call_delete_part(service* srv) {
	// Call the function for deleting a participant
	char id[15];
	strcpy(id, read_id());

	if (s_delete_part(srv, id) == 1)
		print_successful_operation();
	else print_unsuccessful_operation();
}

void call_update_forename(service* srv) {
	// Call the function for updating a participant's forename
	char id[15], forename[25];
	strcpy(id, read_id());
	printf("Valoare noua - ");
	strcpy(forename, read_forename());

	if (s_update_forename(srv, id, forename))
		print_successful_operation();
	else print_unsuccessful_operation();
}

void call_update_surname(service* srv) {
	// Call the function for updating a participant's surname
	char id[15], surname[25];
	strcpy(id, read_id());
	printf("Valoare noua - ");
	strcpy(surname, read_surname());

	if (s_update_surname(srv, id, surname))
		print_successful_operation();
	else print_unsuccessful_operation();
}

void call_update_score(service* srv) {
	// Call the function for updating a participant's score
	char id[15];
	int score, nr;
	strcpy(id, read_id());

	printf("Numarul problemei: ");
	scanf("%d", &nr);

	printf("Valoare noua - ");
	score = read_score(nr);
	printf("\n");

	if (s_update_score(srv, id, nr, score))
		print_successful_operation();
	else print_unsuccessful_operation();
}

void call_filter_score_greater_than(service* srv) {
	// Call the function for showing the participants with a greater score than a given one
	// param srv: the service
	repo* lst = create_repo();
	int nr;

	printf("Scorul minim: ");
	scanf("%d", &nr);

	get_total_score_greater_than(lst, srv, nr);
	print_all_part_repo(lst);

	free(lst);
}

int start() {
	// User interface
	console* cons = create_console();
	service* srv = get_service(cons);
	char opt[20];

	generate_part(srv);

	while (1) {
		print_main_menu();
		printf("Optiunea: ");
		scanf("%s", &opt);
		
		if (!strcmp(opt, "1")) // ADD
			call_add_part(srv);

		else if (!strcmp(opt, "2")) { // UPDATE
			print_update_menu();
			char cmd[15];
			printf("Optiunea: ");
			scanf("%s", &cmd);

			if (!strcmp(cmd, "1"))  // update forename
				call_update_forename(srv);
			else if (!strcmp(cmd, "2")) // update surname
				call_update_surname(srv);
			else if (!strcmp(cmd, "3")) // update score
				call_update_score(srv);
			else printf("Optiune invalida\n\n");
		}

		else if (!strcmp(opt, "3")) // DELETE
			call_delete_part(srv);
		else if (!strcmp(opt, "4")) // FILTER
			call_filter_score_greater_than(srv);
		else if (!strcmp(opt, "5")) { // SORT
			print_sort_menu();
			char cmd[15];
			printf("Optiunea: ");
			scanf("%s", &cmd);

			if (!strcmp(cmd, "1")) { // sort by score ascending
				// sort_by_score(srv, 0);
				sort(get_repo(srv), cmp_score, 0);
				print_all_part_repo(get_repo(srv));
			}
			else if (!strcmp(cmd, "2")) { // sort by score descending
				//sort_by_score(srv, 1);
				sort(get_repo(srv), cmp_score, 1);
				print_all_part_repo(get_repo(srv));
			}
			else if (!strcmp(cmd, "3")) { // sort by name ascending
				//sort_by_name(srv, 0);
				sort(get_repo(srv), cmp_surname, 0);
				print_all_part_repo(get_repo(srv));
			}
			else if (!strcmp(cmd, "4")) { // sort by name descending
				// sort_by_name(srv, 1);
				sort(get_repo(srv), cmp_surname, 1);
				print_all_part_repo(get_repo(srv));
			}
			else printf("Optiune invalida\n\n");


		}
		else if (!strcmp(opt, "x") || !strcmp(opt, "X")) {
			destroy_service(srv);
			free(cons);
			return 0;
		}
		else if (!strcmp(opt, "p") || !strcmp(opt, "P"))
			print_all_part_repo(get_repo(srv));

		else printf("Optiune invalida\n\n");
	}
	destroy_service(cons->srv);
	
	
	return 0;
}