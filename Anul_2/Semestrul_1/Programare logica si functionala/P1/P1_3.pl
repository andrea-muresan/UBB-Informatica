%  a. Sa se scrie un predicat care transforma o lista intr-o multime, in ordinea primei aparitii. Exemplu: [1,2,3,1,2] e transformat in[1,2,3].

memberx(X, [X|_]).
memberx(X, [_|T]):-
	memberx(X, T).

% adaugaSf(L, E, R)
adaugaSf([], E, [E]).
adaugaSf([H|T], E, [H|R]):-
	adaugaSf(T, E, R).

% multime(L, Ac, Rez)
multime([], Ac, Ac):-!.
multime([H|T], Ac, R):-
	\+ member(H, Ac), !,
	adaugaSf(Ac, H, Ac1),
	multime(T, Ac1, R).
multime([H|T], Ac, R):-
	member(H,Ac), !,
	multime(T, Ac, R).

fct(L, R):- multime(L, [], R).
