% plaseaza(L:lista, X:intreg, R:lista)
% L - lista data
% X - elementul care trebuie adaugat
% R - lista dupa adaugarea elementului (crescatoare)
% model de flux - (i, i, o), (i, i, i)
plaseaza([], X, [X]):- !.
plaseaza([H|T], X, [H|R1]) :- X > H, !,
	plaseaza(T, X, R1).
plaseaza([H|T], X, [X, H|T]) :- X < H, !.
plaseaza(L, _, L).

% sorteaza_fara_dubluri(L:lista, R:lista)
% L - lista care trebuie sortata
% R - lista sortata
% model de flux - (i, o), (i, i)
sorteaza_fara_dubluri([], []).
sorteaza_fara_dubluri([H|T], R):- sorteaza_fara_dubluri(T, R1),
	plaseaza(R1, H, R).


% lista_eterogena(L:lista, R:lista)
% L - lista eterogena asupra careia se aplica sortarea cu eliminare de
%   dubluri pentru fiecare elemnt-lista al ei R - lista eterogena
% rezultata dupa sortarea cu eliminare
% model de flux - (i, o), (i, i)
lista_eterogena([], []):- !.
lista_eterogena([H|T], [Rez| Tail]) :- is_lista(H), !, sorteaza_fara_dubluri(H, Rez), !,
	lista_eterogena(T, Tail).
lista_eterogena([H|T], [H| Tail]):- !,
	lista_eterogena(T, Tail).


% is_lista(X: lista sau intreg)
% X - termenul pentru care verificam daca e lista
% model de flux - (i)
is_lista(X) :- var(X), !, false.
is_lista([]).
is_lista([_|_]).

% main_3a(L:lista)
% L - lista data de utilizator pe care o sortam cu eliminare de dubluri
% model de flux - (i, i), (i, o)
main_3a(L,Rez):- sorteaza_fara_dubluri(L, Rez).

% main_3b(L:lista)
% L - lista eterogena data de utilizator pe care o sortam cu eliminare
% de dubluri
% model de flux - (i, i), (i, o)
main_3b(L, Rez):- lista_eterogena(L, Rez).








































































































