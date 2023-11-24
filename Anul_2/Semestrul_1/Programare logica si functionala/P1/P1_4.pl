%  a. Sa se scrie un predicat care substituie intr-o lista un element printr-o alta lista.

% substituie(L, X, Sl, R)
% L -lista
% X - elementul pe care il substituim
% Sl - lista cu care substituim
% R - lista rezultata
substituie([], _, _, []):-!.
substituie([X|T], X, Sl, [Sl|R]):- !,
	substituie(T, X, Sl, R).
substituie([H|T], X, Sl, [H|R]):- !,
	substituie(T, X, Sl, R).

