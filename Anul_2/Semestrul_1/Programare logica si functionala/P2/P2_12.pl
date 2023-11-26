% a. Sa se inlocuiasca toate aparitiile unui element dintr-o lista cu un alt element

% inlocuire(L, E, NE, R)
inlocuire([], _, _, []).
inlocuire([H|T], H, NE, [NE|R]):- !,
	inlocuire(T, H, NE, R).
inlocuire([H|T], E, NE, [H|R]):- !,
	inlocuire(T, E, NE, R).

