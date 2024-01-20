; Definiti o functie care inverseaza o lista impreuna cu toate sublistele
; sale de pe orice nivel.

(defun intoarce (lst)
	(cond
		((null lst) nil)
		(t (append (intoarce (cdr lst)) (list (car lst))))
	)
)

(defun invers(lst)
	(cond
		((null lst) nil)
		((atom lst) (list lst))
		(t
			(list 
				(apply #'append (mapcar #'invers (intoarce lst)))
			)
		)
	)
)
