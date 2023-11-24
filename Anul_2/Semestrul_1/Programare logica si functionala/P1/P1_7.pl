% a.  Sa se scrie un predicat care intoarce reuniunea a doua multimi.

% reuniune(L1, L2, R)
reuniune([], L2, L2):-!.
reuniune([H|T], L2, R):-
	member(H, L2), !,
	reuniune(T, L2, R).
reuniune([H|T], L2, [H|R]):- !,
	reuniune(T, L2, R).

