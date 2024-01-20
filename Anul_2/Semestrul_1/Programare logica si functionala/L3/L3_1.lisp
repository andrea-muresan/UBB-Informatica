; Sa se construiasca o functie care intoarce adancimea unei liste.

(defun adancime (lst)
	(cond
		((null lst) 0)
		((atom lst) 0)
		(t
			(+ 1 (apply #'max (mapcar #'adancime lst)))
		)
	)
)