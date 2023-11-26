% a. Sa se adauge dupa fiecare element dintr-o lista divizorii elementului.


% divizori(X, N, R) N = 1 initial
divizori(X, X, [X]):-!.
divizori(X, N, [N|R]):-
	0 is X mod N,
	N1 is N + 1, !,
	divizori(X, N1, R).
divizori(X, N, R):-
	\+ 0 is X mod N,
	N1 is N + 1, !,
	divizori(X, N1, R).

% adauga(L, R)
adauga([], []):-!.
adauga([H|T], R):- !,
	divizori(H, 1, D), !,
	adauga(T, R1),
	R = [H, D|R1].

