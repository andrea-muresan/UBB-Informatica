; Definiti o functie care obtine dintr-o lista data lista tuturor atomilor
; care apar, pe orice nivel, dar in aceeasi ordine. De exemplu 
; (((A B) C) (D E)) --> (A B C D E)

(defun linie (lst)
	(cond
		((null lst) nil)
		((atom lst) (list lst))
		(t 
			(apply #'append (mapcar #'linie lst))
		)
	)
)