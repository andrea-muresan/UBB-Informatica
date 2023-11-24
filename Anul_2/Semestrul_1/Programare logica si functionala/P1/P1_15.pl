% a.  Sa se scrie un predicat care se va satisface daca o lista are numar  par de elemente si va esua in caz contrar, fara sa se numere elementele listei.

% epar(L, Bool)
epar([], 1):-!.
epar([_|T], N):-!,
	epar(T, N1),
	N is (N1+1) mod 2.

epar_final(L):-
	epar(L, N),
	N = 1,
	true.
