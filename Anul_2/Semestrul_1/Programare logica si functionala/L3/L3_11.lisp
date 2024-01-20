; Sa se scrie o functie care sterge toate aparitiile unui atom de la
; toate nivelurile unei liste.

(defun sterge (el lst)
	(cond
		((null lst) nil)
		((atom lst) (if (eq lst el) nil (list lst)))
		(t
			(list
				(apply #'append (mapcar #'(lambda (x) (sterge el x)) lst))
			)
		)
	)
)
