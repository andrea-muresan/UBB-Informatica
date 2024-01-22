; a) Sa se determine cel mai mic multiplu comun al valorilor numerice dintr-o 
; lista neliniara.

(defun cmmdc (a b)
	(cond 
		((= b 0) a)
		((cmmdc b (mod a b)))
	)
)

(defun cmmmc (a b)
	(/ (* a b) (cmmdc a b))
)

(defun cmmmc_lista (lst result)
	(cond
		((null lst) result)
		((atom lst) (if (numberp lst) (cmmmc lst result) result))
		(t (cmmmc_lista (car lst) (cmmmc_lista (cdr lst) result)))
	)
)

(defun membru (lst el)
	(cond 
		((null lst) nil)
		((atom lst) (if (eq lst el) t nil))
		(t (or (membru (car lst) el) (membru (cdr lst) el)))
	)
)

(defun cmmmc_lista_main (lst)
	(cond 
		((= (cmmmc_lista lst 1) 1) (if (membru lst 1) 1 nil))
		(t (cmmmc_lista lst 1))
	)
)

; c)  Sa se elimine toate aparitiile elementului numeric maxim dintr-o lista 
; neliniara.

(defun extrage_numere (lst)
	(cond
		((null lst) nil)
		((atom lst) (if (numberp lst) (list lst) nil))
		(t (append (extrage_numere (car lst)) (extrage_numere (cdr lst))))
	)
)

(defun maxi (a b)
	(cond
		((> a b) a)
		(t b)
	)
)

(defun maxi_lista (lst)
	(cond
		((null lst) nil)
		((null (cdr lst)) (car lst))
		(t (maxi (car lst) (maxi_lista (cdr lst))))
	)
)

(defun maxi_lista_main (lst)
	(maxi_lista (extrage_numere lst))
)

(defun elimina(l e)
    (cond 
        ( (null l) nil )
        ( (equal (car l) e) (elimina (cdr l) e) )
        ( (atom (car l)) (cons (car l) (elimina (cdr l) e)) )
        ( t (cons (elimina (car l) e) (elimina (cdr l) e)) )
    )
)

