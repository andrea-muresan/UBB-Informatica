; Definiti o functie care da adancimea unui arbore n-ar reprezentat sub forma 
; (radacina lista_noduri_subarb1...lista_noduri_subarbn) 
; Ex: adancimea arborelui este (a (b (c)) (d) (e (f))) este 3

(defun adancime (lst)
	(cond
		((null lst) 0)
		((atom lst) 0)
		(t
			(+ 1 (apply #'max (mapcar #'adancime lst) ))
		)
	)
)