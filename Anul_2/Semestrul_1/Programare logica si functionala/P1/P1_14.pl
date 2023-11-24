% a. Sa se scrie un predicat care testeaza egalitatea a doua multimi, fara  sa se faca apel la diferenta a doua multimi.

% nrAparitii(L, E, N)
nrAparitii([], _, 0):-!.
nrAparitii([H|T], H, N):- !,
	nrAparitii(T, H, N1),
	N is N1 + 1.
nrAparitii([_|T], E, N):- !,
	nrAparitii(T, E, N).

% egale(CL1, L1, L2)
egale([], _, _):-!, true.
egale([H|T], L1, L2):-
	nrAparitii(L1, H, N1),
	nrAparitii(L2, H, N2),
	N1 = N2, !,
	egale(T, L1, L2).

fct(L1, L2):-
	egale(L1, L1, L2),
	egale(L2, L2, L1).
