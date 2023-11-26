% a. Sa se determine cea mai lunga secventa de numere pare consecutive dintr-o lista (daca sunt mai multe secvente de lungime maxima, una dintre ele).

% secvmax(L, LP, NP, LM, NM, R).

secvmax([], LP, NP, _, NM, LP):-
	NP > NM, !.
secvmax([], _, NP, LM, NM, LM):-
	NP =< NM, !.
secvmax([H|T], LP, NP, LM, NM, R):-
	0 is H mod 2,
	NP2 is NP + 1, !,
	secvmax(T, [H|LP], NP2, LM, NM, R).
secvmax([H|T], LP, NP, _, NM, R):-
	\+ 0 is H mod 2,
	NP > NM, !,
	secvmax(T, [], 0, LP, NP, R).
secvmax([H|T], _, NP, LM, NM, R):-
	\+ 0 is H mod 2,
	NP =< NM, !,
	secvmax(T, [], 0, LM, NM, R).

secventa(L, R):- secvmax(L, [], 0, [], 0, R1),
	reverse(R1, R).
