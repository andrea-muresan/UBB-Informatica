; a) Sa se scrie o functie care testeaza daca o lista este liniara.

(defun e_liniara (lst)
	(cond 
		((null lst) t)
		((not(atom (car lst))) nil)
		(t (e_liniara (cdr lst)))
	)
)

; b) Definiti o functie care substituie prima aparitie a unui element intr-o 
; lista data.

(defun substituie (lst el nou)
	(cond 
		((null lst) nil)
		((eq (car lst) el) (cons nou (cdr lst)))
		(t (cons (car lst) (substituie (cdr lst) el nou)))
	)
)

; c) Sa se inlocuiasca fiecare sublista a unei liste cu ultimul ei element.
; Prin sublista se intelege element de pe primul nivel, care este lista. 
; Exemplu: (a (b c) (d (e (f)))) ==> (a c (e (f))) ==> (a c (f)) ==> (a c 
; f) (a (b c) (d ((e) f))) ==> (a c ((e) f)) ==> (a c f)

(defun ultim_element (lst)
	(cond 
		((null lst) nil)
		((atom lst) lst)
		((null (cdr lst)) (ultim_element (car lst)))
		(t (ultim_element (cdr lst)))
		
	)
)

(defun inloc_sublista (lst)
	(cond
		((null lst) nil)
		((listp (car lst)) (cons (ultim_element lst) (inloc_sublista (cdr lst))))
		(t (cons (car lst) (inloc_sublista (cdr lst)))) 
	)
)

; d) Definiti o functie care interclaseaza fara pastrarea dublurilor doua liste 
; liniare sortate.

(defun interclasare (lst1 lst2)
	(cond
		((null lst1) lst2)
		((null lst2) lst1)
		((< (car lst1) (car lst2)) (cons (car lst1) (interclasare (cdr lst1) lst2) ))
		((= (car lst1) (car lst2)) (cons (car lst1) (interclasare (cdr lst1) (cdr lst2)) ))
		(t (cons (car lst2) (interclasare lst1 (cdr lst2))))
	)
)

