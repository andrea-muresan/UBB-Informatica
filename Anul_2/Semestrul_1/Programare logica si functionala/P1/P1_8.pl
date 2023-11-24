% a. Sa se scrie un predicat care testeaza daca o lista este multime.

% multime(L, R)
multime([]):- !, true.
multime([H| T]):-
	\+ member(H, T), !,
	multime(T).
multime([H|T]):-
	member(H, T), !,
	false.
