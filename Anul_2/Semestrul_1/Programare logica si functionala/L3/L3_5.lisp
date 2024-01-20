; Definiti o functie care testeaza apartenenta unui nod intr-un arbore n-ar 
; reprezentat sub forma (radacina lista_noduri_subarb1... lista_noduri_
; _subarbn) 
; Ex: arborelele este (a (b (c)) (d) (e (f))) si nodul este 'b => adevarat

(defun apare (lst el)
	(cond
		((null lst) 0)
		((atom lst) (if (eq lst el) 1 0))
		(t
			(apply #'+ (mapcar #'(lambda (X) (apare X el)) lst) )
		)
	)
)

(defun apare_main (lst el)
	(if (eq (apare lst el) 0) nil t)
)