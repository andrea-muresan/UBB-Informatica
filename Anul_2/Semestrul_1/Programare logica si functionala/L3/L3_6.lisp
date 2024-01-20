; Sa se construiasca o functie care intoarce produsul atomilor numerici 
; dintr-o lista, de la orice nivel.

(defun produs (lst)
	(cond
		((null lst) 0)
		((atom lst) (if (numberp lst) lst 1))
		(t
			(apply #'* (mapcar #'produs lst))
		)
	)
)

(defun membru (lst el)
	(cond
		((null lst) 0)
		((atom lst) (if (and (numberp lst) (eq lst el)) 1 0))
		(t
			(apply #'+ (mapcar #'(lambda (X) (membru X el)) lst))
		)
	)
)

(defun produs_main (lst) 
	(if (= (produs lst) 1) (if (= (membru lst 1) 0) nil 1) (produs lst))
)