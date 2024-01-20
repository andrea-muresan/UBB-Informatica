; Sa se construiasca o functie care intoarce numarul atomilor dintr-o 
; lista, de la orice nivel.

(defun numar_atomi(l)
	(cond
		((null l) 0)
		((atom l) 1)
		(t 
			(apply #'+ (mapcar #'numar_atomi l))
		)
	)
)