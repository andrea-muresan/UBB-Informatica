#define _CRT_SECURE_NO_WARNINGS
#include <string.h>
#include <assert.h>
#include <ctype.h>

int validate_score(char str[]) {
	// Check if the string has only digits
	// : param str: teh given string
	// : return: the number - the string is a valid score / -1 - it is not

	if (strlen(str) == 1)
		if (isdigit(str[0]))
			return str[0] - '0';
		else return -1;
	else if (strlen(str) == 2)
		if (str[0] == '1' && str[1] == '0')
			return 10;
		else return -1;
	else return -1;

}

int validate_word(char str[]) {
	// Check if a string has only letters and spaces
	// : param str: the given string
	// : return: 1 - valid word / 0 - invalid word
	for (int i = 0; i < strlen(str); i++)
		if (isalpha(str[i]) == 0 && str[i] != '-')
			return 0;
	return 1;
}


int validate_id(char str[]) {
	// Check if the string is a valid id (3 digits)
	// : param str: the given string
	// : return: the number - the string is a valid score / 0 - it is not

	if (strlen(str) != 3)
		return 0;
	for (int i = 0; i < 3; i++)
		if (!isdigit(str[i]))
			return 0;
	return 1;

}


// -----TESTS-----
void test_validate_score() {
	// Test for the function: validate_score
	char s[10] = "10";
	assert(validate_score(s) == 10);
	strcpy(s, "7");
	assert(validate_score(s) == 7);
	strcpy(s, "a");
	assert(validate_score(s) == -1);
	strcpy(s, "11");
	assert(validate_score(s) == -1);
	strcpy(s, "111");
	assert(validate_score(s) == -1);
}

void test_validate_word() {
	// Test for the function: validate_word
	assert(validate_word("andre15n1") == 0);
	assert(validate_word("andrea") == 1);
	assert(validate_word("Andrea-Simina") == 1);
	assert(validate_word("*ana") == 0);
	assert(validate_word("andrea-simina") == 1);
	assert(validate_word("4") == 0);
}

void test_validate_id() {
	// Test for the function: validate_id
	assert(validate_id("123") == 1);
	assert(validate_id("12") == 0);
	assert(validate_id("ers") == 0);
	assert(validate_id("e12") == 0);
	assert(validate_id("1234") == 0);
}