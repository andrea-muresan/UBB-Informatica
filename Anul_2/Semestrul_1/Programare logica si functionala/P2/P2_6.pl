% a. Intr-o lista L sa se inlocuiasca toate aparitiile unui element E cu elementele unei alte liste, L1. De ex: inloc([1,2,1,3,1,4],1,[10,11],X) va produce X=[10,11,2,10,11,3,10,11,4].

adauga([], L2, L2):- !.
adauga([H|T], L2, [H|R]):- !,
	adauga(T, L2, R).

% inlocuire(L, E, NE, R).
inlocuire([], _, _, []).
inlocuire([H|T], H, NE, R):- !,
	inlocuire(T, H, NE, R1), !,
	adauga(NE, R1, R).

inlocuire([H|T], E, NE, [H|R]):- !,
	inlocuire(T, E, NE, R).
