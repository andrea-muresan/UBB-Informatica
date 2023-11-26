% a. Definiti un predicat care determina produsul unui numar reprezentatcifra cu cifra intr-o lista cu o anumita cifra. De ex: [1 9 3 5 9 9] * 2 => [3 8 7 1 9 8]


% produs(L, N, CF, R)
produs([], _, 0, []):-!.
produs([], _, CF, [CF]):-!.

produs([H|T], N, CF, [H1|R]):-!,
	H1 is (N*H+CF) mod 10,
	CF1 is (N*H+CF) // 10,
	produs(T, N, CF1, R).

produs_final(L, N, R):- !,
	reverse(L, L2),
	produs(L2, N, 0, R1),
	reverse(R1, R).

