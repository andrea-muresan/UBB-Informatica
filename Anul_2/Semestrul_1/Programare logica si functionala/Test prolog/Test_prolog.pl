% Elimina elementele dintr-o lista din N in N

% elimina(L, N, P, R)
% L - lista de numere data
% N - nr dat
% P - pozitia curenta - initial egala cu 1
% R - lista rezultata dupa eliminarea elementelor din N in N
% model de flux (i, i, i, o), (i, i, i, i)
elimina([], _, _, []).
elimina([_|T], N, P, R):-
	0 is P mod N, !,
	P1 is P + 1,
	elimina(T, N, P1, R).
elimina([H|T], N, P, [H|R]):-
	\+ 0 is P mod N, !,
	P1 is P + 1,
	elimina(T, N, P1, R).

% elimina_final(L, N, R)
% L - lista data
% N - nr dat
% R - lista rezultata dupa eliminarea elementelor din N in N
% model de flux(i, i, o), (i, i, i)
elimina_final(L, N, R):- !, elimina(L, N, 1, R).



