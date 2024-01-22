; a) Definiti o functie care selecteaza al n-lea element al unei liste, sau 
; NIL, daca nu exista.

(defun elementul_n (lst n)
	(cond
		((null lst) nil)
		((eq n 1) (car lst))
		(t (elementul_n (cdr lst) (- n 1)))
	)
)

;  b) Sa se construiasca o functie care verifica daca un atom e membru al unei 
; liste nu neaparat liniara.

(defun membru (lst at)
	(cond 
		((null lst) nil)
		((atom lst) (if (eq lst at) t nil))
		(t (or (membru (car lst) at) (membru (cdr lst) at)))	
	)
)

;; c) Sa se construiasca lista tuturor sublistelor unei liste. Prin sublista se 
;; intelege fie lista insasi, fie un element de pe orice nivel, care este 
;; lista. Exemplu: (1 2 (3 (4 5) (6 7)) 8 (9 10)) => 
;; ( (1 2 (3 (4 5) (6 7)) 8 (9 10)) (3 (4 5) (6 7)) (4 5) (6 7) (9 10) ).

(defun subliste (lst acc)
	(cond
		((null lst) acc)
		((listp (car lst)) (subliste (car lst) (subliste (cdr lst) (cons (car lst) acc))))
		(t (subliste (cdr lst) acc))
	)
)

(defun subliste_main (lst)
	(subliste lst (list lst))
)

;; d) Sa se scrie o functie care transforma o lista liniara intr-o multime.
(defun in_multime (lst acc)
	(cond 
		((null lst) acc)
		((eq nil (membru acc (car lst))) (in_multime lst (cons (car lst) acc)))
		(t (in_multime (cdr lst) acc))
	)
)
