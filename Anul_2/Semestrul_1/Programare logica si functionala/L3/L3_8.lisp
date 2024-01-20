; Sa se construiasca o functie care intoarce maximul atomilor numerici
; dintr-o lista, de la orice nivel.


(defun maxim (lst)
	(cond
		((null lst) nil)
		((atom lst) (if (numberp lst) lst -9999))
		(t 
			(apply #'max (mapcar #'maxim lst))
		)
	)
)