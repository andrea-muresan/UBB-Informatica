;; cel mai mare multiplu comun al atomilor numerici dintr-o lista neliniara

(defun extrage_numere (lst)
	(cond
		((null lst) nil)
		((atom lst) (if (numberp lst) (list lst) nil))
		(t (append (extrage_numere (car lst)) (extrage_numere (cdr lst))))
	)
)

(defun cmmdc (a b)
	(cond
		((= b 0) a)
		(t (cmmdc b (mod a b)) )
	)
)

(defun cmmmc (a b)
	(/ (* a b) (cmmdc a b) )
)

(defun cmmmc_lista (lst)
	(cond
		((null lst) nil)
		((null (cdr lst)) (car lst))
		(t (cmmmc (car lst) (cmmmc_lista (cdr lst))))
	)
)

(defun cmmmc_lista_main (lst)
	(cmmmc_lista (extrage_numere lst))
)