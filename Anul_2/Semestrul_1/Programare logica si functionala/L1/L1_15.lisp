; a) Sa se scrie o functie care intoarce reuniunea a doua multimi.

(defun membru (lst el)
	(cond 
		((null lst) nil)
		((eq (car lst) el) t)
		(t (membru (cdr lst) el))
	)
)

(defun reuniune (lst acc)
	(cond
		((null lst) acc)
		((eq nil (membru acc (car lst))) (reuniune (cdr lst) (cons (car lst) acc)))
		(t (reuniune (cdr lst) acc))
	)
)

; b) Sa se construiasca o functie care intoarce produsul atomilor numerici 
; dintr-o lista, de la orice nivel.

(defun extrage_numere (lst acc)
	(cond
		((null lst) acc)
		((atom lst) (if (numberp lst) (cons lst acc) acc))
		(t (extrage_numere (cdr lst) (extrage_numere (car lst) acc)))
	)
)

(defun produs (lst prod)
	(cond
		((null lst) prod)
		(t (produs (cdr lst) (* (car lst) prod)))
	) 
)

(defun produs_lista (lst)
	(cond
		((null (extrage_numere lst ())) -1)
		(t (produs (extrage_numere lst ()) 1))
	)
)

;; c) Definiti o functie care sorteaza cu pastrarea dublurilor o lista liniara.

(defun plaseaza (lst el)
	(cond
		((null lst) (list el))
		((<= el (car lst)) (cons el lst))
		(t (cons (car lst) (plaseaza (cdr lst) el)))
	)
)

(defun sortare (lst rez)
	(cond
		((null lst) rez)
		(t (sortare (cdr lst) (plaseaza rez (car lst))))
	)
)

(defun sortare_main (lst)
	(sortare lst ())
)

;; d) Definiti o functie care construiește o listă cu pozițiile elementului 
; minim dintr-o listă liniară numerică

(defun mini (a b)
	(cond
		((<= a b) a)
		(t b)
	)
)

(defun minim_lista (lst)
	(cond
		((null (cdr lst)) (car lst))
		(t (mini (car lst) (minim_lista (cdr lst))))
	)
)


(defun poz_minim (lst i mn)
	(cond
		((null lst) ())
		((= (car lst) mn) (cons i (poz_minim (cdr lst) (+ i 1) mn)))
		(t (poz_minim (cdr lst) (+ i 1) mn))
	)
)

(defun poz_minim_main (lst)
	(poz_minim lst 1 (minim_lista lst))
)