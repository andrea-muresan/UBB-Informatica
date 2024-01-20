; Definiti o functie care determina numarul nodurilor de pe nivelul k
; dintr-un arbore n-ar reprezentat sub forma (radacina lista_noduri_subarb1
; ... lista_noduri_subarbn) Ex: arborelele este (a (b (c)) (d) (e (f))) si 
; k=1 => 3 noduri

(defun nr_noduri (lst nivel)
	(cond
		((null lst) 0)
		((atom lst) (if (= nivel -1) 1 0))
		(t
			(apply #'+ (mapcar #'(lambda (X) (nr_noduri X (- nivel 1))) lst) )
		)
	)
)