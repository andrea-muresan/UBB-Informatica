% a. Sa se scrie un predicat care transforma o lista intr-o multime, in ordinea ultimei aparitii. Exemplu: [1,2,3,1,2] e transformat in [3,1,2].

multime([],[]):-!.
multime([H|T], [H|R]):-
	\+ member(H, T),
	!,
	multime(T, R).
multime([H|T], R):-
	member(H, T), !,
	multime(T, R).

