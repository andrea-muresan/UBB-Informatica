% Fiind dat un numar n pozitiv, se cere sa se determine toate
% descompunerile sale ca suma de numere prime distincte.

% divisible(X: intreg, Y: intreg)
% X - numarul pe care il verificam daca are divizori proprii
% Y - posibil divizor al lui X
% model de flux	- (i, o), (i, i)
divisible(X, Y):- 0 is X mod Y, !, true.
divisible(X, Y):-
	X > Y + 1,
	Y1 is Y + 1, !,
	divisible(X, Y1).

% is_prime(N: intreg)
% N - numarul pe care il verificam daca e prim
% model de flux - (i)
is_prime(N):- N < 2, !, false.
is_prime(2):- !, true.
is_prime(N):- N > 2, !,
	not(divisible(N, 2)).

% prime_list(N: intreg, R:lista)
% N - numarul pentru care determinam toate numerele prime mai mici decat
% R - lista continand toate numerele prime mai mici decat N
% model de flux - (i, o), (i, i)
prime_list(N, []):- N =< 1, !.

prime_list(N, R):- not(is_prime(N)), !,
                 N1 is N - 1,
                 prime_list(N1, R).


prime_list(N, R):- is_prime(N), !,
                 N1 is N - 1,
                 prime_list(N1, R1),
                 R = [N|R1].

% partition(N: intreg, P: lista, R: lista)
% N - numarul maxim care poate fi adaugat in partitie
% P - lista din care alegem elementele partitiei
% R - lista partitiilor cu suma elementelor = N initial
% model de flux - (i, i, o), (i, i, i)
partition(0, [], []).
partition(N, [H|T], [H|P]) :-
    N >= H,
    N1 is N - H,
    partition(N1, T, P).
partition(X, [_|T], P) :-
    partition(X, T, P).


% solutions(N: intreg, R: lista)
% N - numarul pentru care determinam partitiile de numere prime
%     distincte cu suma egala cu el
% R - lista de partitiile bune
% model de flux - (i, o), (i, i)
solutions(N, R):-
	prime_list(N, L),
	partition(N, L, R).
