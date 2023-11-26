% a. Dandu-se o lista liniara numerica, sa se stearga toate secventele de valori consecutive. Ex: sterg([1, 2, 4, 6, 7, 8, 10], L) va produce L=[4, 10].

sterge([], []):-!.
sterge([H], [H]):-!.
sterge([H1, H2], []):-
	H2 =:= H1 + 1, !.
sterge([H1, H2, H3|T], R):-
	H2 =:= H1 + 1,
	H3 =:= H2 + 1, !,
	sterge([H2, H3|T], R).
sterge([H1, H2, H3|T], R):-
	H2 =:= H1 + 1,
	\+ H3 =:= H2 + 1, !,
	sterge([H3|T], R).
sterge([H1, H2|T], [H1|R]):-
	\+ H2 =:= H1 + 1,
	sterge([H2|T], R).


