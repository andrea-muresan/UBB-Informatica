% a.  Sa se sorteze o lista cu pastrarea dublurilor. De ex: [4 2 6 2 3 4] => [2 2 3 4 4 6]

plaseaza([], X, [X]):-!.
plaseaza([H|T], X, [H|R]):- X > H, !,
	plaseaza(T, X, R).
plaseaza([H|T], X, [X, H|T]):-!.
plaseaza(L, _, L):-!.

sorteaza([], []):-!.
sorteaza([H|T], R):- !,
	sorteaza(T, R1), !,
	plaseaza(R1, H, R).
