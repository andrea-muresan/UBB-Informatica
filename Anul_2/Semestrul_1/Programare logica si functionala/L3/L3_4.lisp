; Sa se construiasca o functie care intoarce suma atomilor numerici 
; dintr-o lista, de la orice nivel.

(defun suma (lst)
	(cond 
		((null lst) 0)
		((atom lst) (if (numberp lst) lst 0))
		(t
			(apply #'+ (mapcar #'suma lst))
		)
	)
)
