; Definiti o functie care inlocuieste un nod cu altul intr-un arbore n-ar 
; reprezentat sub forma (radacina lista_noduri_subarb1...lista_noduri_subarbn) 
; Ex: arborelele este (a (b (c)) (d) (e (f))) si nodul 'b se inlocuieste cu 
; nodul 'g => arborele (a (g (c)) (d) (e (f)))

(defun inloc (el nou lst)
	(cond
		((null lst) nil)
		((atom lst) (if (eq lst el) (list nou) (list lst)))
		(t
			(list
				(apply #'append (mapcar #'(lambda (X) (inloc el nou X)) lst))
			)
		)
	)
)