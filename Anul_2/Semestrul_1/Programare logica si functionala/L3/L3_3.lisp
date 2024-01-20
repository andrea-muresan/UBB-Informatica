; Sa se construiasca o functie care verifica daca un atom e membru al
; unei liste nu neaparat liniara.

(defun membru(e lst)
	(cond
		((null lst) 0)
		((atom lst) (if (eq e lst) 1 0))
		(t 
			(apply #'+ (mapcar (lambda (x) (membru e x)) lst)) 
		)
	)
)

(defun e_membru (e lst)
	(cond
		((= (membru e lst) 0) nil)
		(t t)
	)
)