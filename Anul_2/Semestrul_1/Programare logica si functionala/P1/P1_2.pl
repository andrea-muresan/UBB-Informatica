% a. Sa se scrie un predicat care determina cel mai mic multiplu comun al elementelor unei liste formate din numere intregi.

% cmmdc(A, B, Rez)
cmmdc(A, 0, A):-!.
cmmdc(A, B, Rez):-
	Rest is A mod B, !,
	cmmdc(B, Rest, Rez).


cmmmc(A, B, Rez):-
	cmmdc(A, B, Cmmdc_val), !,
	Rez is A * B / Cmmdc_val.

% func_a(L:lista, Rez)
func_a([], 0):-!.
func_a([X], X):-!.
func_a([H|T], Rez):-
	func_a(T, Rez1), !,
	cmmmc(H, Rez1, Rez).
