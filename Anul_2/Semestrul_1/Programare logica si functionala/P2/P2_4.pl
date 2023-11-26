% a. Sa se interclaseze fara pastrarea dublurilor doua liste sortate.

% interclasare(L1, L2, R)

% elimina_dubluri(L, R)
elimina_dubluri([], []):-!.
elimina_dubluri([H|T], R):-
	member(H, T), !,
	elimina_dubluri(T, R).
elimina_dubluri([H|T], [H|R]):-
	\+ member(H, T), !,
	elimina_dubluri(T, R).


interclasare([], L, L):-!.
interclasare(L, [], L):-!.

interclasare([H1|T1], [H2|T2], [H1|R]):- H1 <H2, !,
	interclasare(T1, [H2|T2], R).
interclasare([H1|T1], [H2|T2], [H2|R]):- H2 <H1, !,
	interclasare([H1|T1], T2, R).
interclasare([H1|T1], [H2|T2], [H1|R]):- H1 =H2, !,
	interclasare(T1, T2, R).

inter_final(L1, L2, R):-
	elimina_dubluri(L1, L12),
	elimina_dubluri(L2, L22),
	interclasare(L12, L22, R).
