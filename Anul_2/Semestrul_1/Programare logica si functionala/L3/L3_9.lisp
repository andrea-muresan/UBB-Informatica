;; subst_list (L E R) =
;; { R , daca L este atom si E = L
;; { (L) , daca L este atom si L != E
;; { U (list subst_list(Li E R)), i = 1, ...n, altfel

(defun subst_list(L E R)
    (cond
        ((atom L) (if (eql E L) R (list L)))
        (t 
            (list (apply #' append 
		 (mapcar 
                	#'(lambda (lista)
                    	(subst_list lista E R)
                	)
                	L
            
		)
		) )
        )
    )
)