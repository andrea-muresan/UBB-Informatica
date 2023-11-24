% a. Sa se scrie un predicat care sa testeze daca o lista formata din numere intregi are aspect de "vale"(o multime se spune ca are aspect de "vale" daca elementele descresc pana la un moment dat, apoi cresc. De ex. 10 8 6 9 11 13).

% vale(L, N)
% N - numarul de l1 > l2 < l3
vale([_, _], 0):-!.
vale([_], 0):-!.
vale([], 0):-!.
vale([H1, H2, H3 | T], N):-
	H1 > H2,
	H2 < H3, !,
	vale([H2, H3|T], N1),
	N is N1 + 1.
vale([_, H2, H3 | T], N):- !,
	vale([H2, H3|T], N).

evale(L):- !,
	vale(L, N),
	N = 1,
	true.

