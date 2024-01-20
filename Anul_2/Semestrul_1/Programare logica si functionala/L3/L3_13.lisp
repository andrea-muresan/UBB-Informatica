; Definiti o functie care substituie un element prin altul la toate 
; nivelurile unei liste date.

(defun inloc (el nou lst)
	(cond
		((null lst) nil)
		((atom lst) (if (eq el lst) (list nou) (list lst)))
		(t
			(list 
				(apply #'append (mapcar #'(lambda (X) (inloc el nou X)) lst) )
			)
		)
	)
)