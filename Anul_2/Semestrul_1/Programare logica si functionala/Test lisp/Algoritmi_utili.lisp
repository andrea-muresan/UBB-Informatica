(defun elimina (l e)
    (cond 
        ( (null l) nil )
        ( (equal (car l) e) (elimina (cdr l) e) )
        ( (atom (car l)) (cons (car l) (elimina (cdr l) e)) )
        ( t (cons (elimina (car l) e) (elimina (cdr l) e)) )
    )
)


(defun freq (lst result a)
	(cond 
		((null lst) result)
		((atom lst) (if (eq lst a) (+ 1 result) result))
		(t (freq (car lst) (freq (cdr lst) result a) a))
	)
)


(defun membru (lst at)
	(cond 
		((null lst) nil)
		((atom lst) (if (eq lst at) t nil))
		(t (or (membru (car lst) at) (membru (cdr lst) at)))	
	)
)


(defun interclasare (lst1 lst2)
	(cond
		((null lst1) lst2)
		((null lst2) lst1)
		((< (car lst1) (car lst2)) (cons (car lst1) (interclasare (cdr lst1) lst2) ))
		((= (car lst1) (car lst2)) (cons (car lst1) (interclasare (cdr lst1) (cdr lst2)) ))
		(t (cons (car lst2) (interclasare lst1 (cdr lst2))))
	)
)


(defun extrage_numere (lst)
	(cond
		((null lst) nil)
		((atom lst) (if (numberp lst) (list lst) nil))
		(t (append (extrage_numere (car lst)) (extrage_numere (cdr lst))))
	)
)


(defun plaseaza (lst el)
	(cond
		((null lst) (list el))
		((<= el (car lst)) (cons el lst))
		(t (cons (car lst) (plaseaza (cdr lst) el)))
	)
)

(defun sortare (lst rez)
	(cond
		((null lst) rez)
		(t (sortare (cdr lst) (plaseaza rez (car lst))))
	)
)