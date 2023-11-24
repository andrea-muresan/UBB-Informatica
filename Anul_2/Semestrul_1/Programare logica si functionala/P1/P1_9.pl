% a.  Sa se scrie un predicat care intoarce intersectia a doua multimi.

% intersectie(L1, L2, R)
intersectie([], _, []):-!.
intersectie([H|T], L2, [H|R]):-
	member(H, L2), !,
	reuniune(T, L2, R).
intersectie([_|T], L2, R):- !,
	reuniune(T, L2, R).
