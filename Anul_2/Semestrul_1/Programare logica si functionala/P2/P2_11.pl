% a. Se da o lista de numere intregi. Se cere sa se scrie de 2 ori in lista fiecare numar prim.

% eprim(X, N, ND) N = 2
prim(X, _):-
	X < 2, !,
	false.
prim(X, N):-
	X = N, !,
	true.
prim(X, N):-
	0 is X mod N, !,
	false.
prim(X, N):-
	N1 is N + 1, !,
	prim(X, N1).

eprim(X):- prim(X, 2).

% lista(L, R)
lista([], []):-!.
lista([H|T], [H, H|R]):-
	eprim(H), !,
	lista(T, R).
lista([H|T], [H|R]):- !,
	lista(T, R).
