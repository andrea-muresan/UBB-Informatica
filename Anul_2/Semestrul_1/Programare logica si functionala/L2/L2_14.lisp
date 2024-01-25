; 14 Sa se construiasca lista nodurilor unui arbore de tipul (2) parcurs in 
; postordine.


(defun postordine (lst)
	(cond 
		((null lst) nil)
		((atom lst) (list lst))
		(t (append (append (postordine (cadr lst)) (postordine (caddr lst))) (list (car lst))))
	)
)