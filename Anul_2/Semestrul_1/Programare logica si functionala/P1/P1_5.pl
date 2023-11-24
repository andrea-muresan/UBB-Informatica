% a.  Sa se scrie un predicat care sterge toate aparitiile unui anumit atom dintr-o lista

% sterge(L-lista, A-atom, R-rezultat)
sterge([], _, []):-!.
sterge([H|T], H, Rez):- !,
	sterge(T, H, Rez).
sterge([H|T], A, [H|Rez]):- !,
	sterge(T, A, Rez).
