% a. Definiti un predicat care determina predecesorul unui numar reprezentat cifra cu cifra intr-o lista. De ex: [1 9 3 6 0 0] => [1 9 3 5 9 9]

%precedent(L, CF, R)
precedent([H], CF, []):-
	0 is H - CF, !.
precedent([H], CF, [H1]):-
	H1 is H - CF, !.
precedent([H|T], CF, R):-
	H1 is H - CF,
	H1 >= 0, !,
	precedent(T, 0, R1),
	R = [H1|R1].
precedent([_|T], 1, R):-
	precedent(T, 1, R1),
	R = [9|R1].


precedent_final(L, R):-
	reverse(L, LR),
	precedent(LR, 1, RR),
	reverse(RR, R).
