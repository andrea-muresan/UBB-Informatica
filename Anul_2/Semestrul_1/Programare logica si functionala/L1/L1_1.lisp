(setq *print-case* :capitalize)

; a) Sa se insereze intr-o lista liniara un atom a dat dupa
; al 2-lea, al 4-lea, al 6-lea,....element

(defun insert_after_even (lst a i)
	(cond
		((null lst) nil)
		((= (mod i 2) 0) (cons (car lst) (cons a (insert_after_even (cdr lst) a (+ i 1)))))
		(t (cons (car lst) (insert_after_even (cdr lst) a (+ i 1))))
	)

)

(defun insert_after_even_main (lst a)
	(insert_after_even lst a 1)
)

; b) Definiti o functie care obtine dintr-o lista data lista tuturor atomilor 
; care apar, pe orice nivel, dar in ordine inversa. De exemplu: (((A B) C) 
; (D E)) --> (E D C B A)

(defun get_atoms (lst acc)
	(cond
		((null lst) acc)
		((atom lst) (cons lst acc))
		(t (get_atoms (cdr lst) (get_atoms (car lst) acc)))
	)
)

(defun get_atoms_main (lst)
	(get_atoms lst ())
)

; c) Definiti o functie care intoarce cel mai mare divizor comun al numerelor 
; dintr-o lista neliniara.

(defun euclid (a b)
	(cond
		((= b 0) a)
		(t (euclid b (mod a b)))
	)
)

(defun cmmdc (lst result)
	(cond
		((null lst) result)
		((atom lst) (if (numberp lst) (euclid result lst) result))
		(t (cmmdc (car lst) (cmmdc (cdr lst) result)))
	)
)

(defun cmmdc_main (lst)
	(cmmdc lst 0)
)

;; d) Sa se scrie o functie care determina numarul de aparitii ale unui atom dat 
;; intr-o lista neliniara.

(defun freq (lst result a)
	(cond 
		((null lst) result)
		((atom lst) (if (eq lst a) (+ 1 result) result))
		(t (freq (car lst) (freq (cdr lst) result a) a))
	)
)


(defun freq_main (lst a)
	(freq lst 0 a)
)