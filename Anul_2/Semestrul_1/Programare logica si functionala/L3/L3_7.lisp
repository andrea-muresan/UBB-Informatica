; Sa se scrie o functie care calculeaza suma numerelor pare minus suma
; numerelor impare la toate nivelurile unei liste.

(defun diffPareImp (lst)
	(cond 
		((null lst) 0)
		((atom lst) (if (numberp lst) (if (evenp lst) lst (- 0 lst)) 0))
		(t
			(apply #'+ (mapcar #'diffPareImp lst))
		)
	)
)
