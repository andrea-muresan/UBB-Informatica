% a. Sa se scrie un predicat care elimina dintr-o lista toate elementele care se repeta (ex: l=[1,2,1,4,1,3,4] => l=[2,3])

% elimina(L-lista, R-rezultatul)
elimina([], []):-!.
elimina([H|T], Rez):-
	member(H, T), !,
	elimina(T, Rez).
elimina([H|T], [H|Rez]):-
	elimina(T, Rez).
