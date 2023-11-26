% a. Sa se determine pozitiile elementului maxim dintr-o list liniara. De ex: poz([10,14,12,13,14], L) va produce L = [2,5].

% maxim(L, M)
maxim([M], M):-!.
maxim([H|T], R):-
	maxim(T, R1),
	H > R1, !,
	R = H.
maxim([H|T], R):-
	maxim(T, R1),
	H =< R1, !,
	R = R1.

% poz_max(L, M, N, R)
poz_max([], _, _, []):-!.
poz_max([H|T], H, N, [N|R]):- !,
	N1 is N + 1,
	poz_max(T, H, N1, R).
poz_max([_|T], M, N, R):- !,
	N1 is N + 1,
	poz_max(T, M, N1, R).

pozitii(L, R):-
	maxim(L, M),
	poz_max(L, M, 0, R).
