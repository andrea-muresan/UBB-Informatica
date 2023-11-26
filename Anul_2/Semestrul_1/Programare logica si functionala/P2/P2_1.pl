% Definiti un predicat care determina suma a doua numere scrise in reprezentare de lista.

% adaugaSf(L, E, R)
adaugaSf([], E, [E]):-!.
adaugaSf([H|T], E, [H|R]):-
	adaugaSf(T, E, R).

% intoarce(L1, R)
intoarce([], []):-!.
intoarce([H|T], R):- !,
	intoarce(T, R1), !,
	adaugaSf(R1, H, R).

suma([], [], 0, []):-!.
suma([], [], 1, [1]):-!.

suma([H|T], [], CF, [H1|R]):- !,
	H1 is (H + CF) mod 10,
	CF1 is (H + CF) // 10,
	suma(T, [], CF1, R).

suma([], [H|T], CF, [H1|R]):-!,
	H1 is (H + CF) mod 10,
	CF1 is (H + CF) // 10,
	suma([], T, CF1, R).


suma([H1|T1], [H2|T2], CF, [H|R]):-
	H is (H1 + H2 + CF) mod 10,
	CF1 is (H1 + H2 + CF) // 10,
	suma(T1, T2, CF1, R).

final(L1, L2, R):-
	intoarce(L1, IL1),
	intoarce(L2, IL2),
	suma(IL1, IL2, 0, R1),
	intoarce(R1, R).
