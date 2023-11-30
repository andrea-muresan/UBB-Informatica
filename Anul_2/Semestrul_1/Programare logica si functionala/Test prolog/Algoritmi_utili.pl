% ==========ADAUGA LA SFARSITUL UNEI LISTE==========
%
% adaugasf(L:lista, X:intreg, R:lista)
% L - lista initiala
% X - elementul pe care trebuie sa il adaugam
% R - lista rezultata dupa adaugarea elementului
% model de flux - (i, i, o), (i, i, i)
%
adaugasf([], X, [X]):-!.
adaugasf([H|T], X, [H|R]):-
	adaugasf(T, X, R).
% ==================================================



% ===============INVERSUL UNEI LISTE================
%
% invers(L:lista, R:lista)
% L - lista pe care o intoarcem
% R - lista intoarsa
% model de flux - (i, o), (i, i)
%
invers([], []).
invers([H|T], R):-!,
	invers(T, R1),
	adaugasf(R1, H, R).
% ==================================================



% ===========VERIFICA DACA UN NUMAR E PRIM==========
%
% divizibil(X:intreg, Y:intreg)
% X - numarul pe care il verificam daca are divizori proprii
% Y - posibil divizor al lui X (conditie de intrare: Y = 2)
% model de flux	- (i, o), (i, i)
%
divizibil(X, X):-!, true.
divizibil(X, Y):-
	0 is X mod Y,
	false, !.
divizibil(X, Y):-
	\+ 0 is X mod Y,
	!,
	Y1 is Y +1,
	divizibil(X, Y1).

% e_prim(X:intreg)
% X - numarul pe care il verificam daca e prim
% model de flux - (i)
%
e_prim(X):- X < 2, !, false.
e_prim(X):- divizibil(X, 2).
% ==================================================



% ======SORTEAZA O LISTA DE NUMERE PRIN INSERTIE====
%
% plaseaza(L:lista, X:intreg, R:lista)
% L - lista data (conditie intrare: L-crescatoare)
% X - elementul care trebuie adaugat
% R - lista dupa adaugarea elementului (conditie iesire: R-crescatoare)
%
plaseaza([], X, [X]):-!.
plaseaza([H|T], X, [X, H|T]):- X =< H, !.
plaseaza([H|T], X, [H|R]):- X > H, !,
	plaseaza(T, X, R).

% L - lista care trebuie sortata
% R - lista sortata
%
sortare([], []):-!.
sortare([H|T], R):- !,
	sortare(T, R1),!,
	plaseaza(R1, H, R).
% ==================================================



% =====VERIFICA DACA UN ELEMENT APARE INTR-O LISTA==
%
% memberx(X:intreg, L:lista)
% X -numarul pe care il cautam in lista
% L lista in care cautam
% modele de flux - (i, i)
%
memberx(X, [X|_]).
memberx(X, [_|T]):- memberx(X, T).
% ==================================================
