% a) Definiti un predicat care determina succesorul unui numar reprezentat
%    cifra cu cifra intr-o lista. De ex: [1 9 3 5 9 9] => [1 9 3 6 0 0]

% succesor(L, CF = 0, R)
succesor([], 1, []).
succesor([H|T], CF, [H1|R]):- !,
	succesor(T, CF1, R),
	Ad is H + CF1,
	H1 is Ad mod 10,
	CF is Ad // 10.

