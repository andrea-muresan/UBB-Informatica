% a. Sa se intercaleze un element pe pozitia a n-a a unei liste.

%insereaza(L, El, Poz, R)
insereaza(L, El, 0, [El|L]):-!.
insereaza([H|T], El, Poz, [H|R]):-
	Poz1 is Poz - 1, !,
	insereaza(T, El, Poz1, R).
