; a)  Sa se intercaleze un element pe pozitia a n-a a unei liste liniare.

(defun plaseaza (lst poz el)
	(cond
		((null lst) nil)
		((= poz 1) (cons el lst))
		(t (cons (car lst) (plaseaza (cdr lst) (- poz 1) el)))
	)
)

; b) Sa se construiasca o functie care intoarce suma atomilor numerici dintr-o 
; lista, de la orice nivel.

(defun suma (lst rez)
	(cond
		((null lst) 0)
		((atom lst) (if (numberp lst) (+ rez lst) rez))
		(t (suma (car lst) (suma (cdr lst) rez)) ) 
	)
)

; c) Sa se scrie o functie care intoarce multimea tuturor sublistelor unei 
; liste date. Ex: Ptr. lista ((1 2 3) ((4 5) 6)) => ((1 2 3) (4 5) ((4 5) 
; 6))

(defun subliste (lst)
	(cond
		((null lst) nil)
		((listp (car lst)) (append (cons (car lst) (subliste (car lst))) (subliste (cdr lst))))
		(t (subliste (cdr lst)))
	)
)
