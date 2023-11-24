% a. Sa se scrie un predicat care substituie intr-o lista un element prin altul.

% substituie(L, E, Ne, R)
substituie([], _, _, []).
substituie([H|T], H, Ne, [Ne|R]):- !,
	substituie(T, H, Ne, R).
substituie([H|T], E, Ne, [H|R]):- !,
	substituie(T, E, Ne, R).
