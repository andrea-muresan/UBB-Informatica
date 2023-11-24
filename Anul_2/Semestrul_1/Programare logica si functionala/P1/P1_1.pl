% a. Sa se scrie un predicat care intoarce diferenta a doua multimi
% b. Sa se scrie un predicat care adauga intr-o lista dupa fiecare element par valoarea 1.

%Predicat diff(L1:lista, L2:lista, Rez:lista)
%L1 lista dat de utilizator
%L2 lista data de utilizator
%Rez lista rezultata dupa L1-L2
%model de flux - (i, i, o), (i, i, i)

diff([], _, []).
diff([H|T], Set, Result) :-
	memberx(H, Set),
	!,
	diff(T, Set, Result).
diff([H|T], Set, [H|Result]):- diff(T, Set, Result).


% memberx(X:intreg, L:lista)
% X -numarul pe care il cautam in lista
% L lista in care cautam
% modele de flux - (i, i)
memberx(X, [X|_]).
memberx(X, [_|T]):- memberx(X, T).

%add_one_after_even(L:lista, Rez:lista)
%L -lista initiala
%R - lista rezultata dupa adaufarea numarului 1, dupa fircare numar par
%model de flux (i, i), (i, o)
add_one_after_even([],[]).
add_one_after_even([H|T], [H, 1|Result]) :-
	0 is H mod 2,
	!,
	add_one_after_even(T, Result).

add_one_after_even([H|T], [H|Result]) :-
	add_one_after_even(T, Result).

final(L):- add_one_after_even(L, R), !,
	write(R).
