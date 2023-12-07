(setq *print-case* :capitalize)

; a) Dandu-se o lista liniara, se cere sa se elimine elementele din N in N.
(defun remove_by_n (lst n k)
	(cond
		((null lst) nil)
		((= k 1)(remove_by_n (cdr lst) n n))
		((/= k 1) (cons (car lst) (remove_by_n (cdr lst) n (- k 1))))
	)
)

(defun remove_by_n_main (lst n)
	(remove_by_n lst n n)
	
)

; b)  Sa se scrie o functie care sa testeze daca o lista liniara formata din
; numere intregi are aspect de "vale"(o secvență se spune ca are aspect de
; "vale" daca elementele descresc pana la un moment dat, apoi cresc. 
; De ex. 10 8 6 17 19 20).

(defun valley_no (lst)
	(cond
		((null lst) 0)
		((null (cdr lst)) 0)
		((null (cddr lst)) (if (>= (first lst) (second lst)) 2 0))
		((= (first lst) (second lst)) 2) ; 5 5 2 3 nu e vale
		((and (> (first lst) (second lst))
			(< (second lst) (third lst)))
		(+ 1 (valley_no (cdr lst))))
		(t (valley_no (cdr lst)))
	)
)

(defun is_valley (lst)
	(if (= (valley_no lst) 1)
		(format nil "E vale")
		(format nil "NU e vale")
	) 	
)



; c) Sa se construiasca o functie care intoarce minimul atomilor numerici 
; dintr-o lista, de la orice nivel.

(defun extract_numbers (lst acc)
	(cond
		((null lst) acc)
		((atom lst) (if (numberp lst) (cons lst acc) acc))
		(t (extract_numbers (cdr lst) (extract_numbers (car lst) acc)))
	)
)

(defun minim (a b)
	(if (< a b) a b))

(defun minim_list (lst)
	(cond
		((null lst) nil)
		((null (cdr lst)) (car lst))
		(t (minim (car lst) (minim_list (cdr lst))))
		
	)
)

(defun minim_list_main (lst)
	(minim_list (extract_numbers lst ()))
)


; d) Sa se scrie o functie care sterge dintr-o lista liniara toate aparitiile
; elementului maxim numeric.

(defun maxim (a b)
	(if (> a b) a b))

(defun maxim_list (lst)
	(cond
		((null lst) nil)
		((null (cdr lst)) (car lst))
		(t (maxim (car lst) (maxim_list (cdr lst))))
	)
)


(defun remove_elem(lst el)
	(cond
		((null lst) nil)
		((equal (car lst) el) (remove_elem (cdr lst) el))
		(t (cons (car lst) (remove_elem (cdr lst) el)))
	)
)	

(defun remove_maxim (lst)
	(remove_elem lst (maxim_list (extract_numbers lst ()))))

